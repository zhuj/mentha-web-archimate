#!/usr/bin/env python

# pip install tesserocr
# pip install editdistance
# apt-get install libleptonica-dev libtesseract-dev
# https://pypi.python.org/pypi/tesserocr
# https://stackoverflow.com/questions/27969091/processing-an-image-of-a-table-to-get-data-from-it

import os
import re
import numpy as np
import Image
import cv2

lexicon = [
  'ApplicationCollaboration',
  'ApplicationComponent',
  'ApplicationEvent',
  'ApplicationFunction',
  'ApplicationInteraction',
  'ApplicationInterface',
  'ApplicationProcess',
  'ApplicationService',
  'Artifact',
  'Assessment',
  'BusinessActor',
  'BusinessCollaboration',
  'BusinessEvent',
  'BusinessFunction',
  'BusinessInteraction',
  'BusinessInterface',
  'BusinessObject',
  'BusinessProcess',
  'BusinessRole',
  'BusinessService',
  'Capability',
  'CommunicationNetwork',
  'Constraint',
  'Contract',
  'CourseOfAction',
  'DataObject',
  'Deliverable',
  'Device',
  'DistributionNetwork',
  'Driver',
  'Equipment',
  'Facility',
  'Gap',
  'Goal',
  'Grouping',
  'ImplementationEvent',
  'Junction',
  'Location',
  'Material',
  'Meaning',
  'Node',
  'Outcome',
  'Path',
  'Plateau',
  'Principle',
  'Product',
  'Relationship',
  'Representation',
  'Requirement',
  'Resource',
  'Stakeholder',
  'SystemSoftware',
  'TechnologyCollaboration',
  'TechnologyEvent',
  'TechnologyFunction',
  'TechnologyInteraction',
  'TechnologyInterface',
  'TechnologyProcess',
  'TechnologyService',
  'Value',
  'WorkPackage',
]

import editdistance
cached = {}
def look(s):
    c = cached.get(s, None)
    if c is not None: return c

    m, z = len(lexicon), ""
    for l in lexicon:
        i = editdistance.eval(l, s)
        if (i < m): 
            m = i
            z = l

    cached[s] = z
    return z


# the list of images (tables)
images = [ 'image%s.png' % i for i in range(226, 236) ]
#images = [ 'image234.png' ]

# remove duplicate lines (lines within 10 pixels of eachother)
def remove_duplicates(lines, threshold=0):
    for i1, (x1, y1, x2, y2) in enumerate(lines):
        for i2, (x3, y3, x4, y4) in enumerate(lines):
            if i1 >= i2: continue
            if y1 == y2 and y3 == y4: diff = abs(y1-y3)
            elif x1 == x2 and x3 == x4: diff = abs(x1-x3)
            else: continue
            if diff < threshold: del lines[i2]
    return lines

# sort lines into horizontal and vertical
def sort_line_list(lines):
    vertical = []
    horizontal = []
    for line in lines:
        if line[0] == line[2]: vertical.append(line)
        elif line[1] == line[3]: horizontal.append(line)
    vertical.sort(key=lambda line: line[0])
    horizontal.sort(key=lambda line: line[1])
    return remove_duplicates(horizontal), remove_duplicates(vertical)


def read_transparent_png(filename):
    image_4channel = cv2.imread(filename, cv2.IMREAD_UNCHANGED)
    alpha_channel = image_4channel[:,:,3]
    rgb_channels = image_4channel[:,:,:3]

    # White Background Image
    white_background_image = np.ones_like(rgb_channels, dtype=np.uint8) * 255

    # Alpha factor
    alpha_factor = alpha_channel[:,:,np.newaxis].astype(np.float32) / 255.0
    alpha_factor = np.concatenate((alpha_factor,alpha_factor,alpha_factor), axis=2)

    # Transparent Image Rendered on White Background
    base = rgb_channels.astype(np.float32) * alpha_factor
    white = white_background_image.astype(np.float32) * (1 - alpha_factor)
    final_image = base + white
    return final_image.astype(np.uint8)


def hough_transform_p(fn):

    # cv2
    #img = cv2.imread(fn)
    img = read_transparent_png(fn)
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

    blur = gray #cv2.GaussianBlur(gray, (1, 1), 1000)
    #cv2.imwrite(fn+'-01-blur.png', blur)

    # http://docs.opencv.org/2.4/modules/imgproc/doc/miscellaneous_transformations.html
    thresh = cv2.adaptiveThreshold(blur, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY, 11, 2)
    #cv2.imwrite(fn+'-02-thresh.png', thresh)

    exp = blur
    _, exp = cv2.threshold(exp, 100, 255, cv2.THRESH_BINARY)
    cv2.imwrite(fn+'-02-export.png', exp)

    edges = cv2.Canny(thresh, threshold1=50, threshold2=150, apertureSize=3)
    #cv2.imwrite(fn+'-03-edges.png', edges)

    lines = cv2.HoughLinesP(edges, rho=1, theta=np.pi/180, threshold=400, minLineLength=400, maxLineGap=10)[0].tolist()
    lines = remove_duplicates(lines)

    # table
    horizontal, vertical = sort_line_list(lines)

    # draw
    lines = []
    lines += horizontal
    lines += vertical

    # filter
    vertical = vertical[2:]
    horizontal = horizontal[2:-4]

    # draw2
    lines = horizontal + vertical
    for x1, y1, x2, y2 in lines:
        cv2.line(img, (x1,y1), (x2,y2), (255,0,0), 2)

    TH = 10
    for i in range(0, len(horizontal)-1): # first row is table header - skip it
        for j in range(0, len(vertical)-1):
            x1,y1 = vertical[0+j][0], horizontal[0+i][1]
            x2,y2 = vertical[1+j][0], horizontal[1+i][1]
            if (x2 - x1 < TH or y2 - y1 < TH): continue

            cv2.line(img, (x1,y1), (x2,y2), (0,255,0), 2)

    cv2.imwrite(fn+'-04-lines.png', img)

    # go through each horizontal line (aka row)

    def trim(s): return re.sub(r'\s+', '', s)

    import tesserocr
    from tesserocr import PyTessBaseAPI, RIL, PSM, OEM, iterate_level

    import string
    SYMBOLS=" " + string.ascii_uppercase + string.ascii_lowercase

    columns = {}
    if 1:
      with PyTessBaseAPI(psm=PSM.AUTO_OSD, lang='eng') as api: # psm=PSM.SINGLE_BLOCK
        i = 0
        for j in range(1, len(vertical)-1):
            x1,y1 = vertical[0+j][0]+4, horizontal[0+i][1]+4
            x2,y2 = vertical[1+j][0]+1, horizontal[1+i][1]+1
            if (x2 - x1 < TH or y2 - y1 < TH): continue

            # region of interest
            roi = exp[ y1:y2, x1:x2 ]
            name = 'test/%s-%04d-%04d.png' % (fn, i, j)
            #cv2.imwrite(name, roi)

            api.SetImage(Image.fromarray(roi))
            api.SetVariable("tessedit_char_whitelist", SYMBOLS)
            columns[j] = look(trim(api.GetUTF8Text()))

    #print fn, columns

    rows = {}
    if 1:
      with PyTessBaseAPI(psm=PSM.AUTO_OSD, lang='eng') as api:
        j = 0
        for i in range(1, len(horizontal)-1):
            x1,y1 = vertical[0+j][0]+4, horizontal[0+i][1]+4
            x2,y2 = vertical[1+j][0]+1, horizontal[1+i][1]+1
            if (x2 - x1 < TH or y2 - y1 < TH): continue

            # region of interest
            roi = exp[ y1:y2, x1:x2 ]
            name = 'test/%s-%04d-%04d.png' % (fn, i, j)
            #cv2.imwrite(name, roi)

            api.SetImage(Image.fromarray(roi))
            api.SetVariable("tessedit_char_whitelist", SYMBOLS)
            rows[i] = look(trim(api.GetUTF8Text()))

    #print fn, rows

    result = {}
    if 1:
      with PyTessBaseAPI(psm=PSM.RAW_LINE) as api:
        for j in range(1, len(vertical)-1):
            for i in range(1, len(horizontal)-1):
                x1,y1 = vertical[0+j][0]+4, horizontal[0+i][1]+4
                x2,y2 = vertical[1+j][0]+1, horizontal[1+i][1]+1
                if (x2 - x1 < TH or y2 - y1 < TH): continue

                # region of interest
                roi = exp[ y1:y2, x1:x2 ]
                name = 'test/%s-%04d-%04d.png' % (fn, i, j)
                #cv2.imwrite(name, roi)

                api.SetImage(Image.fromarray(roi))
                api.SetVariable("tessedit_char_whitelist", "acfginorstv ")
                relations = trim(api.GetUTF8Text())
                result[(columns[j], rows[i])] = relations
                print columns[j], rows[i], relations

    return result

def process():
    result = {}
    for img in images:
        result.update(hough_transform_p(img))
    #print result

if __name__ == '__main__':
    process()
