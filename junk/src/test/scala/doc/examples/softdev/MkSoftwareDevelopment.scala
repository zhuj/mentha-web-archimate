package doc.examples.softdev

import org.mentha.tools.archimate.model._
import org.mentha.tools.archimate.model.nodes._
import org.mentha.tools.archimate.model.nodes.impl._
import org.mentha.tools.archimate.model.nodes.dsl.Motivation._
import org.mentha.tools.archimate.model.nodes.dsl.Business._
import org.mentha.tools.archimate.model.nodes.dsl.Application._
import org.mentha.tools.archimate.model.nodes.dsl.Technology._
import org.mentha.tools.archimate.model.nodes.dsl.Physical._
import org.mentha.tools.archimate.model.nodes.dsl.Implementation._
import org.mentha.tools.archimate.model.nodes.dsl.Strategy._
import org.mentha.tools.archimate.model.nodes.dsl.Junctions._
import org.mentha.tools.archimate.model.nodes.dsl.Composition._
import org.mentha.tools.archimate.model.nodes.dsl._
import org.mentha.tools.archimate.model.edges._
import org.mentha.tools.archimate.model.edges.impl._
import org.mentha.tools.archimate.model.view._
import org.mentha.tools.archimate.model.view.dsl._
import org.mentha.tools.archimate.model.utils.MkModel

trait Base {
  implicit val model: Model = new Model withId "real-software-development"
  implicit val space: Size = Size(40, 50)
}


trait Common {
  this: Base =>

  val seniorManagement: Stakeholder = stakeholder withName "Senior Management"
  val ceo: Stakeholder = stakeholder withName "CEO"
  val cto: Stakeholder = stakeholder withName "CTO"
  val cxo: Stakeholder = stakeholder withName "C?O"

  seniorManagement `aggregates` ceo
  seniorManagement `aggregates` cto
  seniorManagement `aggregates` cxo

  val `customer`: Stakeholder = stakeholder withName "Customer"

  val customerSatisfaction: Driver = driver withName "Customer Satisfaction"

  customerSatisfaction `associated with` ceo
  customerSatisfaction `associated with` customer

}

trait MkSoftwareDevelopmentTrait {
  this: Common =>

}

object MkSoftwareDevelopment extends MkModel
  with MkSoftwareDevelopmentTrait
  with Common
  with Base {

  def main(args: Array[String]): Unit = {
    publishModel(model)
  }

}
