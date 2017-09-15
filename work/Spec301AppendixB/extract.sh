#!/bin/bash -e

# make sure you have numpy, cv2, tessract(+dev) and tesserocr installed

# it extracts images from http://pubs.opengroup.org/architecture/archimate3-doc/apdxb.html#_Toc489946155 and parses them

# load images (unfortunately it's impossible to have it downloaded due to the license reasons)
for ((n=226;n<238;n++)); do
  [ -f "image${n}.png" ] || wget -c http://pubs.opengroup.org/architecture/archimate3-doc/ts_archimate_3.0.1-final-rev_files/image${n}.png -O image${n}.png
done

# parse them
extract.py > base.txt
