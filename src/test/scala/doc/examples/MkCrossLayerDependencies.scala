package doc.examples

import org.mentha.utils.archimate.model.utils.MkModel

/**
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap12.html#_Toc489946115]]
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/ts_archimate_3.0.1-final-rev_files/image205.png]]
  */
object MkCrossLayerDependencies extends MkModel {

  import org.mentha.utils.archimate.model._
  import org.mentha.utils.archimate.model.nodes.dsl.Motivation._
  import org.mentha.utils.archimate.model.nodes.dsl.Business._
  import org.mentha.utils.archimate.model.nodes.dsl.Application._
  import org.mentha.utils.archimate.model.nodes.dsl.Technology._
  import org.mentha.utils.archimate.model.nodes.dsl._
  import org.mentha.utils.archimate.model.view._
  import org.mentha.utils.archimate.model.view.dsl._

  def main(args: Array[String]): Unit = {

    implicit val space = Size(20, 50)
    implicit val model = new Model withId "ex-cross-layer-dependencies"
    implicit val view = model.add("v-main") { new View(LayeredViewPoint) }


    // customer

    val baCustomer = in(view) node { businessActor withName "Customer" }
    val brInsurant = in(view) node { businessRole withName "Insurant" } place(directions.Left, baCustomer)

    in(view) edge { $(baCustomer) `assigned to` $(brInsurant) }


    // services

    val bsClaimsRegistration = in(view) node { businessService withName "Claims Registration" } place(directions.Down, brInsurant) move(directions.Left)
    val bsClaimsAcceptance = in(view) node { businessService withName "Claims Acceptance" } place(directions.Right, bsClaimsRegistration)
    val bsClaimsPayment = in(view) node { businessService withName "Claims Payment" } place(directions.Right, bsClaimsAcceptance) move(directions.Right)

    in(view) edge { $(bsClaimsRegistration) `serves` $(brInsurant) }
    in(view) edge { $(bsClaimsAcceptance) `serves` $(brInsurant) }
    in(view) edge { $(bsClaimsPayment) `serves` $(brInsurant) }

    // process

    val bpRegister = in(view) node { businessProcess withName "Register" } place(directions.Down, bsClaimsRegistration)
    val bpAccept = in(view) node { businessProcess withName "Accept" } place(directions.Right, bpRegister)
    val bpAdjudicate = in(view) node { businessProcess withName "Adjudicate" } place(directions.Right, bpAccept)
    val bpPay = in(view) node { businessProcess withName "Pay" } place(directions.Right, bpAdjudicate)

    in(view) edge { $(bpRegister) `triggers` $(bpAccept) }
    in(view) edge { $(bpAccept) `triggers` $(bpAdjudicate) }
    in(view) edge { $(bpAdjudicate) `triggers` $(bpPay) }

    in(view) edge { $(bpRegister) `realizes` $(bsClaimsRegistration) }
    in(view) edge { $(bpAccept) `realizes` $(bsClaimsAcceptance) }
    in(view) edge { $(bpPay) `realizes` $(bsClaimsPayment) }

    // insurer

    val bpProcessClaims = in(view) node { businessProcess withName "Process Claims" } wrap (wrapWithComposition, bpRegister, bpAccept, bpAdjudicate, bpPay)
    val brInsurer = in(view) node { businessRole withName "Insurer" } place(directions.Right, bpProcessClaims)
    val baSurance = in(view) node { businessActor withName "OurSuranceCo" } place(directions.Right, brInsurer)
    in(view) edge { $(baSurance) `assigned to` $(brInsurer) }
    in(view) edge { $(brInsurer) `assigned to` $(bpProcessClaims) }

    // customer management

    val asCustomerDataManagement = in(view) node { applicationService withName "Customer Data Management" } place(directions.Down, bpRegister)
    in(view) edge { $(asCustomerDataManagement) `serves` $(bpRegister) }

    val acCRMSystem = in(view) node { applicationComponent withName "CRM System" } place(directions.Down, asCustomerDataManagement)
    in(view) edge { $(acCRMSystem) `realizes` $(asCustomerDataManagement) }

    // payment processing

    val asPaymentProcessing = in(view) node { applicationService withName "Payment Processing" } place(directions.Down, bpPay)
    in(view) edge { $(asPaymentProcessing) `serves` $(bpPay) }

    val acFinancialApplication = in(view) node { applicationComponent withName "Financial Application" } place(directions.Down, asPaymentProcessing)
    in(view) edge { $(acFinancialApplication) `realizes` $(asPaymentProcessing) }

    // database

    val tsDBM = in(view) node { technologyService withName "Database Management" } place(directions.Down, acCRMSystem, acFinancialApplication) move(directions.Down) move(directions.Left, 0.75)
    val ssDBMS = in(view) node { systemSoftware withName "Database Management System" } place(directions.Down, tsDBM)
    in(view) edge { $(ssDBMS) `realizes` $(tsDBM) }

    in(view) edge { $(tsDBM) `serves` $(acCRMSystem) }
    in(view) edge { $(tsDBM) `serves` $(acFinancialApplication) }

    // application hosting

    val tsApplicationHosting = in(view) node { technologyService withName "Application Hosting" } place(directions.Down, acCRMSystem, acFinancialApplication)  move(directions.Down) move(directions.Right, 0.75)
    val ssApplicationServer = in(view) node { systemSoftware withName "Application Server" } place(directions.Down, tsApplicationHosting)
    in(view) edge { $(ssApplicationServer) `realizes` $(tsApplicationHosting) }

    in(view) edge { $(tsApplicationHosting) `serves` $(acCRMSystem) }
    in(view) edge { $(tsApplicationHosting) `serves` $(acFinancialApplication) }

    // archives & providers

//    val arDbaKar = in(view) node { artifact withName "Database access archive" } place(directions.Left, ssDBMS)
//    in(view) edge { $(ssDBMS) `assigned to` $(arDbaKar) }
//    in(view) edge { $(arDbaKar) `serves` $(acCRMSystem) }
//    in(view) edge { $(arDbaKar) `serves` $(acFinancialApplication) }

    val arCrmWar = in(view) node { artifact withName "CRM System\nWebArchive" } place(directions.Down, acCRMSystem)
    in(view) edge { $(ssApplicationServer) `assigned to` $(arCrmWar) }
    in(view) edge { $(arCrmWar) `realizes` $(acCRMSystem) }

    val arPmWar = in(view) node { artifact withName "Financial Application\nWebArchive" } place(directions.Down, acFinancialApplication)
    in(view) edge { $(ssApplicationServer) `assigned to` $(arPmWar) }
    in(view) edge { $(arPmWar) `realizes` $(acFinancialApplication) }

    // blade

    val deBladeSystem = in(view) node { device withName "Blade system" } place(directions.Down, ssDBMS, ssApplicationServer) scaleWidth(3.0)
    in(view) edge { $(deBladeSystem) `assigned to` $(ssDBMS) }
    in(view) edge { $(deBladeSystem) `assigned to` $(ssApplicationServer) }

    // customer data (structure & usage)

    val boCustomerInformation = in(view) node { businessObject withName "Customer Information" } place(directions.Left, bpRegister) move(directions.Left, 0.5)
    val doCustomerData = in(view) node { dataObject withName "Customer Data" } place(boCustomerInformation, acCRMSystem)
    val arCustomerRecord = in(view) node { artifact withName "Customer Database Record" } place(doCustomerData, tsDBM)

    in(view) edge { $(doCustomerData) `realizes` $(boCustomerInformation) }
    in(view) edge { $(arCustomerRecord) `realizes` $(doCustomerData) }

    in(view) edge { $(bpRegister) `writes` $(boCustomerInformation) }
    in(view) edge { $(acCRMSystem) `writes` $(doCustomerData) }
    in(view) edge { $(tsDBM) `writes` $(arCustomerRecord) }




    //
//    val g = in(view) node { goal withName "Goal" }
//    val p = in(view) node { principle withName "Principle" } place(directions.Right, g)
//    val rq = in(view) node { requirement withName "Requirement/\nConstraint" } place(directions.Down, g, p)
//
//    in(view) edge { $(p) `realizes` $(g) } flex(0)
//    in(view) edge { $(rq) `realizes` $(g) } flex(0)
//    in(view) edge { $(rq) `realizes` $(p) } flex(0)
//
//    in(view) edge { $(g) `influences` "+/-" `in` $(g) } routeLoop(directions.Up, 1)
//    in(view) edge { $(g) `influences` "+/-" `in` $(p) } flex(1)
//    in(view) edge { $(g) `influences` "+/-" `in` $(rq) } flex(1)
//
//    in(view) edge { $(p) `influences` "+/-" `in` $(p) } routeLoop(directions.Up, 1)
//    in(view) edge { $(p) `influences` "+/-" `in` $(g) } flex(1)
//    in(view) edge { $(p) `influences` "+/-" `in` $(rq) } flex(1)
//
//    in(view) edge { $(rq) `influences` "+/-" `in` $(rq) } routeLoop(directions.Down, 1)
//    in(view) edge { $(rq) `influences` "+/-" `in` $(g) } flex(1)
//    in(view) edge { $(rq) `influences` "+/-" `in` $(p) } flex(1)


    println(json.toJsonString(model))
    publishModel(model)
  }

}
