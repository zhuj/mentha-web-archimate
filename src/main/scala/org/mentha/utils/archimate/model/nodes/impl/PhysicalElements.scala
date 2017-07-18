package org.mentha.utils.archimate.model.nodes.impl

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes._
import org.mentha.utils.archimate.model.edges._

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
sealed trait PhysicalElement extends PhysicalLayer {}

/**
 * Equipment represents one or more physical machines, tools, or instruments that can create, use, store, move, or transform materials.
 * @note Equipment comprises all active processing elements that carry out physical processes in which materials (which are a special kind of technology object) are used or transformed. Equipment is a specialization of the node element from the Technology Layer. Therefore, it is possible to model nodes that are formed by a combination of IT infrastructure (devices, system software) and physical infrastructure (equipment); e.g., an MRI scanner at a hospital, a production plant with its control systems, etc.
 * @note Material can be accessed (e.g., created, used, stored, moved, or transformed) by equipment. Equipment can serve other equipment, and also other active structure elements such as business roles and actors, and facilities can be assigned to equipment. A piece of equipment can be composed of other pieces of equipment. Equipment can be assigned to (i.e., installed and used in or on) a facility and can be aggregated in a location.
 * @note The name of a piece of equipment should preferably be a noun.
 * @note A useful specialization of equipment is vehicle, for describing, for example, trucks, cars, trains, ships, and airplanes.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap11.html#_Toc451758070 Equipment ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Equipment extends InternalActiveStructureElement with PhysicalElement {
  @inline override def meta: ElementMeta[Equipment] = PhysicalElements.equipment
}

/**
 * A facility represents a physical structure or environment.
 * @note A facility is a specialization of a node. It represents a physical resource that has the capability of facilitating (e.g., housing or locating) the use of equipment. It is typically used to model factories, buildings, or outdoor constructions that have an important role in production or distribution processes. Examples of facilities include a factory, a laboratory, a warehouse, a shopping mall, a cave, or a spaceship. Facilities may be composite; i.e., consist of sub-facilities.
 * @note Facilities can be interconnected by distribution networks. Material can be accessed (e.g., created, used, stored, moved, or transformed) by equipment. A facility can serve other facilities, and also other active structure elements such as business roles and actors, and locations can be assigned to facilities. A facility can be composed of other facilities and can be aggregated in a location.
 * @note The name of a facility should preferably be a noun referring to the type of facility; e.g., “Rotterdam harbor oil refinery”.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap11.html#_Toc451758071 Facility ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Facility extends ExternalActiveStructureElement with PhysicalElement {
  @inline override def meta: ElementMeta[Facility] = PhysicalElements.facility
}

/**
 * A distribution network represents a physical network used to transport materials or energy.
 * @note A distribution network represents the physical distribution or transportation infrastructure. It embodies the physical realization of the logical paths between nodes.
 * @note A distribution network connects two or more nodes. A distribution network may realize one or more paths. A distribution network can consist of sub-networks and can aggregate facilities and equipment, for example, to model railway stations and trains that are part of a rail network.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap11.html#_Toc451758072 DistributionNetwork ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class DistributionNetwork extends InternalActiveStructureElement with PhysicalElement {
  @inline override def meta: ElementMeta[DistributionNetwork] = PhysicalElements.distributionNetwork
}

/**
 * Material represents tangible physical matter or physical elements.
 * @note Material represents tangible physical matter, with attributes such as size and weight. It is typically used to model raw materials and physical products, and also energy sources such as fuel. Material can be accessed by physical processes.
 * @note The name of material should be a noun. Pieces of material may be composed of other pieces of material.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap11.html#_Toc451758075 Material ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Material extends PassiveStructureElement with PhysicalElement {
  @inline override def meta: ElementMeta[Material] = PhysicalElements.material
}

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
object PhysicalElements {

  case object equipment extends ElementMeta[Equipment] {
    override def newInstance(): Equipment = new Equipment
    override def layerObject: LayerObject = PhysicalLayer
  }
  case object facility extends ElementMeta[Facility] {
    override def newInstance(): Facility = new Facility
    override def layerObject: LayerObject = PhysicalLayer
  }
  case object distributionNetwork extends ElementMeta[DistributionNetwork] {
    override def newInstance(): DistributionNetwork = new DistributionNetwork
    override def layerObject: LayerObject = PhysicalLayer
  }
  case object material extends ElementMeta[Material] {
    override def newInstance(): Material = new Material
    override def layerObject: LayerObject = PhysicalLayer
  }

  val physicalElements: Seq[ElementMeta[_]] = Seq(equipment, facility, distributionNetwork, material)

}
