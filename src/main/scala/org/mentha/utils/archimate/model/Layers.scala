package org.mentha.utils.archimate.model

import org.apache.commons.lang3.StringUtils

/**
  * http://pubs.opengroup.org/architecture/archimate3-doc/chap03.html
  * An abstraction of the ArchiMate framework at which an enterprise can be modeled.
  * The ArchiMate core language defines a structure of generic elements and their relationships, which can be specialized in different layers.
  */
sealed trait Layer extends ArchimateObject {
  def layerObject: LayerObject

}

/**
  * The ArchiMate core language defines a structure of generic elements and their relationships, which can be specialized in different layers.
  */
sealed trait CoreLayer extends Layer {

}

/**
  * The Business Layer depicts business services offered to customers, which are realized in the organization by business processes performed by business actors.
  */
trait BusinessLayer extends CoreLayer {
  final override def layerObject = BusinessLayer
}

/**
  * The Application Layer depicts application services that support the business, and the applications that realize them.
  */
trait ApplicationLayer extends CoreLayer {
  final override def layerObject = ApplicationLayer
}

/**
  * The Technology Layer depicts technology services such as processing, storage, and communication services needed to run the applications, and the computer and communication hardware and system software that realize those services. Physical elements are added for modeling physical equipment, materials, and distribution networks to this layer.
  */
trait TechnologyLayer extends CoreLayer {
  final override def layerObject = TechnologyLayer
}

/**
  * TODO
  */
trait MotivationLayer extends Layer {
  final override def layerObject = MotivationLayer
}

/**
  * TODO
  */
trait StrategyLayer extends Layer {
  final override def layerObject = StrategyLayer
}

/**
  * TODO
  */
trait PhysicalLayer extends Layer {
  final override def layerObject = PhysicalLayer
}

/**
  * TODO
  */
trait ImplementationLayer extends Layer {
  final override def layerObject = ImplementationLayer
}

/**
  * TODO
  */
trait CompositionLayer extends Layer {
  final override def layerObject = CompositionLayer
}

sealed trait LayerObject {
  self: Layer =>

  /**
    * In ArchiMate models, there are no formal semantics assigned to colors and the use of color is left to the modeler.
    * However, they can be used freely to stress certain aspects in models.
    * Colors are grabbed from http://pubs.opengroup.org/architecture/archimate3-doc/ts_archimate_3.0_files/image004.png
    * @return color hex string
    */
  def color: String

  /**
    * In addition to the colors, other notational cues can be used to distinguish between the layers of the framework. A letter ‘M’, ‘S’, ‘B’, ‘A’, ‘T’, ‘P’, or ‘I’ in the top-left corner of an element can be used to denote a Motivation, Strategy, Business, Application, Technology, Physical, or Implementation & Migration element, respectively.
    * @return
    */
  def letter: Char

  /**
    * @return
    */
  def name: String = StringUtils.uncapitalize(
    StringUtils.substringBeforeLast(this.getClass.getSimpleName, "$")
  )

}


/**
  * The Business Layer depicts business services offered to customers, which are realized in the organization by business processes performed by business actors.
  */
case object BusinessLayer extends LayerObject with BusinessLayer {
  override val color = "#ffffcc" // Cream, http://www.htmlcsscolor.com/hex/ffffcc
  override val letter = 'B'
}

/**
  * The Application Layer depicts application services that support the business, and the applications that realize them.
  */
case object ApplicationLayer extends LayerObject with ApplicationLayer {
  override val color = "#c2f0ff" // Onahau, http://www.htmlcsscolor.com/hex/c2f0ff
  override val letter = 'A'
}

/**
  * The Technology Layer depicts technology services such as processing, storage, and communication services needed to run the applications, and the computer and communication hardware and system software that realize those services. Physical elements are added for modeling physical equipment, materials, and distribution networks to this layer.
  */
case object TechnologyLayer extends LayerObject with TechnologyLayer {
  override val color = "#c9ffc9" // Blue Romance, http://www.htmlcsscolor.com/hex/c9ffc9
  override val letter = 'T'
}

/**
  * Motivation
  * TODO
  */
case object MotivationLayer extends LayerObject with MotivationLayer {
  override val color = "#e5e5ff" // Lavender, http://www.htmlcsscolor.com/hex/e5e5ff
  override val letter = 'M'
}

/**
  * Strategy
  * TODO
  */
case object StrategyLayer extends LayerObject with StrategyLayer {
  override val color = "#ffffa7" // Shalimar, http://www.htmlcsscolor.com/hex/ffffa7
  override val letter = 'S'
}

/**
  * Physical
  * TODO
  */
case object PhysicalLayer extends LayerObject with PhysicalLayer {
  override val color = "#97ff97" // Mint Green, http://www.htmlcsscolor.com/hex/97ff97
  override val letter = 'P'
}

/**
  * Implementation & Migration
  * TODO
  */
case object ImplementationLayer extends LayerObject with ImplementationLayer {
  override val color = "#ffe0e0" // Misty Rose, http://www.htmlcsscolor.com/hex/ffe0e0
  override val letter = 'I'
}

/**
  * Artificial layer: composite element
  */
case object CompositionLayer extends LayerObject with CompositionLayer {
  override val color = "#ffffff" // White
  override val letter = 'C'
}