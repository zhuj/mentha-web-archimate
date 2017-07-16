package org.mentha.utils.archimate.model.view

import org.apache.commons.lang3.StringUtils
import org.mentha.utils.archimate.model.nodes
import org.mentha.utils.archimate.model.nodes.ElementMeta

/**
  * Viewpoints are a means to focus on particular aspects and layers of the architecture. These aspects and layers are determined by the concerns of a stakeholder with whom communication takes place.
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap14.html Stakeholders, Viewpoints, and Views]]
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html Example Viewpoints (Informative)]]
  */
sealed trait ViewPoint {
  val name: String = StringUtils.stripEnd(
    StringUtils.uncapitalize(
      StringUtils.substringBeforeLast(
        this.getClass.getSimpleName,
        "ViewPoint"
      )
    ),
    "$"
  )
  def possibleElements: Seq[ElementMeta[_]]
}

/**
  * The most basic type of viewpoint is a simple selection of a relevant subset of the ArchiMate concepts and the representation of that part of an architecture that is expressed in this selection, geared towards the stakeholders that will use the resulting views.
  *
  * The following are examples of stakeholders and concerns as a basis for the specification of viewpoints:
  * - End user: For example, what are the consequences for his work and workplace?
  * - Architect: What is the consequence for the maintainability of a system, with respect to corrective, preventive, and adaptive maintenance?
  * - Upper-level management: How can we ensure our policies are followed in the development and operation of processes and systems? What is the impact of decisions (on personnel, finance, ICT, etc.)?
  * - Operational manager: Responsible for exploitation or maintenance: For example, what new technologies are there to prepare for? Is there a need to adapt maintenance processes? What is the impact of changes to existing applications? How secure are my systems?
  * - Project manager: Responsible for the development of new applications: What are the relevant domains and their relationships? What is the dependence of business processes on the applications to be built? What is their expected performance?
  * - Developer: What are the modifications with respect to the current situation that need to be done?
  *
  * In each basic viewpoint, concepts from the three layers of Business, Application, and Technology may be used. However, not every combination of these would give meaningful results. In some cases, for example, separate viewpoints for the different layers are advisable. Based on common architectural practice and on experiences with the use of ArchiMate models in practical cases, useful combinations in the form of a set of basic viewpoints have been defined. These are listed in Table 12. The table also shows the perspective for the viewpoint. Some viewpoints have a scope that is limited to a single layer or aspect, when others link multiple layers and/or aspects. The different viewpoints are grouped into categories that indicate which direction and which elements the viewpoint is looking at:
  * - Composition: Viewpoints that defines internal compositions and aggregations of elements.
  * - Support: Viewpoints where you are looking at elements that are supported by other elements. Typically from one layer and upwards to an above layer.
  * - Cooperation: Towards peer elements which cooperate with each other. Typically across aspects.
  * - Realization: Viewpoints where you are looking at elements that realize other elements. Typically from one layer and downwards to a below layer.
  *
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758121 Basic Viewpoints in ArchiMate]]
  */
sealed trait BasicViewPoints extends ViewPoint {

}

/**
  * The organization viewpoint focuses on the (internal) organization of a company, department, network of companies, or of another organizational entity. It is possible to present models in this viewpoint as nested block diagrams, but also in a more traditional way, such as organizational charts. The Organization viewpoint is very useful in identifying competencies, authority, and responsibilities in an organization.
  * - Stakeholders: Enterprise, process and domain architects, managers, employees, shareholders
  * - Concerns: Identification of competencies, authority, and responsibilities
  * - Purpose: Designing, deciding, informing
  * - Scope: Single layer/Single aspect
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758122 Organization Viewpoint]]
  */
case object OrganizationViewPoint extends BasicViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    nodes.businessActiveStructureElements ++
    Seq[ElementMeta[_]](
      nodes.impl.CompositionElements.location
    )
}

/**
  * The business process cooperation viewpoint is used to show the relationships of one or more business processes with each other and/or with their environment. It can both be used to create a high-level design of business processes within their context and to provide an operational manager responsible for one or more such processes with insight into their dependencies.
  * - Stakeholders: Process and domain architects, operational managers
  * - Concerns: Dependencies between business processes, consistency and completeness, responsibilities
  * - Purpose: Designing, deciding
  * - Scope: Multiple layer/Multiple aspect
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758123 Business Process Cooperation Viewpoint]]
  */
case object BusinessProcessCooperationViewPoint extends BasicViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    nodes.impl.ApplicationElements.applicationElements ++
    nodes.businessActiveStructureElements ++
    nodes.businessBehaviorElement ++
    Seq[ElementMeta[_]](
      nodes.impl.BusinessElements.businessObject,
      nodes.impl.BusinessElements.representation,
      nodes.impl.CompositionElements.location
  )
}

/**
  * The product viewpoint depicts the value that these products offer to the customers or other external parties involved and shows the composition of one or more products in terms of the constituting (business, application, or technology) services, and the associated contract(s) or other agreements. It may also be used to show the interfaces (channels) through which this product is offered, and the events associated with the product. A product viewpoint is typically used in product development to design a product by composing existing services or by identifying which new services have to be created for this product, given the value a customer expects from it. It may then serve as input for business process architects and others that need to design the processes and ICT realizing these products.
  * - Stakeholders: Product developers, product managers, process and domain architects
  * - Concerns: Product development, value offered by the products of the enterprise
  * - Purpose: Designing, deciding
  * - Scope: Multiple layer/Multiple aspect
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758124]]
  */
case object ProductViewPoint extends BasicViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    nodes.impl.ApplicationElements.applicationElements ++
    nodes.businessActiveStructureElements ++
    nodes.businessBehaviorElement ++
    Seq[ElementMeta[_]](
      nodes.impl.BusinessElements.businessObject,
      nodes.impl.BusinessElements.contract,
      nodes.impl.BusinessElements.product,
      nodes.impl.PhysicalElements.material,
      nodes.impl.TechnologyElements.technologyService,
      nodes.impl.TechnologyElements.artifact,
      nodes.impl.MotivationElements.value
    )
}

/**
  * The application cooperation viewpoint describes the relationships between applications components in terms of the information flows between them, or in terms of the services they offer and use. This viewpoint is typically used to create an overview of the application landscape of an organization. This viewpoint is also used to express the (internal) cooperation or orchestration of services that together support the execution of a business process.
  * - Stakeholders: Enterprise, process, application, and domain architects
  * - Concerns: Relationships and dependencies between applications, orchestration/choreography of services, consistency and completeness, reduction of complexity
  * - Purpose: Designing
  * - Scope: Multiple layer/Multiple aspect
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758125 Application Cooperation Viewpoint]]
  */
case object ApplicationCooperationViewPoint extends BasicViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    nodes.impl.ApplicationElements.applicationElements ++
    Seq[ElementMeta[_]](
      nodes.impl.CompositionElements.location
    )
}

/**
  * The application usage viewpoint describes how applications are used to support one or more business processes, and how they are used by other applications. It can be used in designing an application by identifying the services needed by business processes and other applications, or in designing business processes by describing the services that are available. Furthermore, since it identifies the dependencies of business processes upon applications, it may be useful to operational managers responsible for these processes.
  * - Stakeholders: Enterprise, process, and application architects, operational managers
  * - Concerns: Consistency and completeness, reduction of complexity
  * - Purpose: Designing, deciding
  * - Scope: Multiple layer/Multiple aspect
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758126 Application Usage Viewpoint]]
  */
case object ApplicationUsageViewPoint extends BasicViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    nodes.impl.ApplicationElements.applicationElements ++
    nodes.businessInternalActiveStructureElements ++
    nodes.businessInternalBehaviorElement ++
    Seq[ElementMeta[_]](
      nodes.impl.BusinessElements.businessObject
    )
}

/**
  * The implementation and deployment viewpoint shows how one or more applications are realized on the infrastructure. This comprises the mapping of applications and components onto artifacts, and the mapping of the information used by these applications and components onto the underlying storage infrastructure.
  * - Stakeholders: Application and domain architects
  * - Concerns: Structure of application platforms and how they relate to supporting technology
  * - Purpose: Designing, deciding
  * - Scope: Multiple layer/Multiple aspect
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758127 Implementation and Deployment Viewpoint]]
  */
case object ImplementationDeploymentViewPoint extends BasicViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    nodes.impl.ApplicationElements.applicationElements ++
    Seq[ElementMeta[_]](
      nodes.impl.TechnologyElements.systemSoftware,
      nodes.impl.TechnologyElements.technologyInterface,
      nodes.impl.TechnologyElements.path,
      nodes.impl.TechnologyElements.technologyProcess,
      nodes.impl.TechnologyElements.technologyFunction,
      nodes.impl.TechnologyElements.technologyInteraction,
      nodes.impl.TechnologyElements.technologyService,
      nodes.impl.TechnologyElements.artifact
    )
}

/**
  * The technology viewpoint contains the software and hardware technology elements supporting the Application Layer, such as physical devices, networks, or system software (e.g., operating systems, databases, and middleware).
  * - Stakeholders: Infrastructure architects, operational managers
  * - Concerns: Stability, security, dependencies, costs of the infrastructure
  * - Purpose: Designing
  * - Scope: Single layer/Multiple aspect
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758128 Technology Viewpoint]]
  */
case object TechnologyViewPoint extends BasicViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    nodes.impl.TechnologyElements.technologyElements ++
    Seq[ElementMeta[_]](
      nodes.impl.CompositionElements.location
    )
}

/**
  * The technology usage viewpoint shows how applications are supported by the software and hardware technology: the technology services are delivered by the devices; system software and networks are provided to the applications. This viewpoint plays an important role in the analysis of performance and scalability, since it relates the physical infrastructure to the logical world of applications. It is very useful in determining the performance and quality requirements on the infrastructure based on the demands of the various applications that use it.
  * - Stakeholders: Application, infrastructure architects, operational managers
  * - Concerns: Dependencies, performance, scalability
  * - Purpose: Designing
  * - Scope: Multiple layer/Multiple aspect
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758129 Technology Usage Viewpoint]]
  */
case object TechnologyUsageViewPoint extends BasicViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    nodes.impl.TechnologyElements.technologyElements ++
    Seq[ElementMeta[_]](
      nodes.impl.ApplicationElements.applicationComponent,
      nodes.impl.ApplicationElements.applicationCollaboration,
      nodes.impl.ApplicationElements.applicationProcess,
      nodes.impl.ApplicationElements.applicationFunction,
      nodes.impl.ApplicationElements.applicationInteraction,
      nodes.impl.ApplicationElements.applicationEvent
    )
}

/**
  * The information structure viewpoint is comparable to the traditional information models created in the development of almost any information system. It shows the structure of the information used in the enterprise or in a specific business process or application, in terms of data types or (object-oriented) class structures. Furthermore, it may show how the information at the business level is represented at the application level in the form of the data structures used there, and how these are then mapped onto the underlying technology infrastructure; e.g., by means of a database schema.
  * - Stakeholders: Domain and information architects
  * - Concerns: Structure and dependencies of the used data and information, consistency and completeness
  * - Purpose: Designing
  * - Scope: Multiple layer/Single aspect
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758130 Information Structure Viewpoint]]
  */
case object InformationStructureViewPoint extends BasicViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    Seq[ElementMeta[_]](
      nodes.impl.BusinessElements.businessObject,
      nodes.impl.BusinessElements.representation,
      nodes.impl.ApplicationElements.dataObject,
      nodes.impl.TechnologyElements.artifact,
      nodes.impl.MotivationElements.meaning
  )
}

/**
  * The service realization viewpoint is used to show how one or more business services are realized by the underlying processes (and sometimes by application components). Thus, it forms the bridge between the business products viewpoint and the business process view. It provides a “view from the outside” on one or more business processes.
  * - Stakeholders: Process and domain architects, product and operational managers
  * - Concerns: Added-value of business processes, consistency and completeness, responsibilities
  * - Purpose: Designing, deciding
  * - Scope: Multiple layer/Multiple aspect
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758131 Service Realization Viewpoint]]
  */
case object ServiceRealizationViewPoint extends BasicViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    nodes.impl.ApplicationElements.applicationElements ++
    nodes.businessActiveStructureElements ++
    nodes.businessBehaviorElement ++
    Seq[ElementMeta[_]](
      nodes.impl.BusinessElements.businessObject,
      nodes.impl.BusinessElements.representation
    )
}

/**
  * The physical viewpoint contains equipment (one or more physical machines, tools, or instruments) that can create, use, store, move, or transform materials, how the equipment is connected via the distribution network, and what other active elements are assigned to the equipment.
  * - Stakeholders: Infrastructure architects, operational managers
  * - Concerns: Relationships and dependencies of the physical environment and how this relates to IT infrastructure
  * - Purpose: Designing
  * - Scope: Multiple layer/Multiple aspect
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758132 Physical Viewpoint]]
  */
case object PhysicalViewPoint extends BasicViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    nodes.impl.PhysicalElements.physicalElements ++
    Seq[ElementMeta[_]](
      nodes.impl.TechnologyElements.node,
      nodes.impl.TechnologyElements.device,
      nodes.impl.TechnologyElements.path,
      nodes.impl.TechnologyElements.communicationNetwork,
      nodes.impl.CompositionElements.location
    )
}

/**
  * The layered viewpoint pictures several layers and aspects of an Enterprise Architecture in one diagram. There are two categories of layers, namely dedicated layers and service layers. The layers are the result of the use of the “grouping” relationship for a natural partitioning of the entire set of objects and relationships that belong to a model. The technology, application, process, and actor/role layers belong to the first category. The structural principle behind a fully layered viewpoint is that each dedicated layer exposes, by means of the “realization” relationship, a layer of services, which are further on “serving” the next dedicated layer. Thus, we can easily separate the internal structure and organization of a dedicated layer from its externally observable behavior expressed as the service layer that the dedicated layer realizes. The order, number, or nature of these layers are not fixed, but in general a (more or less) complete and natural layering of an ArchiMate model should contain the succession of layers depicted in the example given below. However, this example is by no means intended to be prescriptive. The main goal of the layered viewpoint is to provide an overview in one diagram. Furthermore, this viewpoint can be used as support for impact of change analysis and performance analysis or for extending the service portfolio.
  * - Stakeholders: Enterprise, process, application, infrastructure, and domain architects
  * - Concerns: Consistency, reduction of complexity, impact of change, flexibility
  * - Purpose: Designing, deciding, informing
  * - Scope: Multiple layer/Multiple aspect
  * All core elements and all relationships are permitted in this viewpoint.
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758133 Layered Viewpoint]]
  */
case object LayeredViewPoint extends BasicViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    nodes.allElements
}

/**
  * A number of standard viewpoints for modeling motivational aspects have been defined. Each of these viewpoints presents a different perspective on modeling the motivation that underlies some Enterprise Architecture and allows a modeler to focus on certain aspects. Therefore, each viewpoint considers only a selection of the elements and relationships that have been described in the preceding sections.
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758134 Motivation Viewpoints]]
  */
sealed trait MotivationViewPoints extends ViewPoint {

}

/**
  * The stakeholder viewpoint allows the analyst to model the stakeholders, the internal and external drivers for change, and the assessments (in terms of strengths, weaknesses, opportunities, and threats) of these drivers. Also, the links to the initial (high-level) goals that address these concerns and assessments may be described. These goals form the basis for the requirements engineering process, including goal refinement, contribution and conflict analysis, and the derivation of requirements that realize the goals.
  * - Stakeholders: Stakeholders, business managers, enterprise and ICT architects, business analysts, requirements managers
  * - Concerns: Architecture mission and strategy, motivation
  * - Purpose: Designing, deciding, informing
  * - Scope: Motivation
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758135 Stakeholder Viewpoint]]
  */
case object StakeholderViewPoint extends MotivationViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    Seq[ElementMeta[_]](
      nodes.impl.MotivationElements.stakeholder,
      nodes.impl.MotivationElements.driver,
      nodes.impl.MotivationElements.assessment,
      nodes.impl.MotivationElements.goal,
      nodes.impl.MotivationElements.outcome
    )
}

/**
  * The goal realization viewpoint allows a designer to model the refinement of (high-level) goals into more tangible goals, and the refinement of tangible goals into requirements or constraints that describe the properties that are needed to realize the goals. The refinement of goals into sub-goals is modeled using the aggregation relationship. The refinement of goals into requirements is modeled using the realization relationship.
  * In addition, the principles may be modeled that guide the refinement of goals into requirements.
  * - Stakeholders: Stakeholders, business managers, enterprise and ICT architects, business analysts, requirements managers
  * - Concerns: Architecture mission, strategy and tactics, motivation
  * - Purpose: Designing, deciding
  * - Scope: Motivation
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758136 Goal Realization Viewpoint]]
  */
case object GoalRealizationViewPoint extends MotivationViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    Seq[ElementMeta[_]](
      nodes.impl.MotivationElements.goal,
      nodes.impl.MotivationElements.principle,
      nodes.impl.MotivationElements.requirement,
      nodes.impl.MotivationElements.constraint,
      nodes.impl.MotivationElements.outcome
    )
}

/**
  * The requirements realization viewpoint allows the designer to model the realization of requirements by the core elements, such as business actors, business services, business processes, application services, application components, etc. Typically, the requirements result from the goal refinement viewpoint.
  * In addition, this viewpoint can be used to refine requirements into more detailed requirements. The aggregation relationship is used for this purpose.
  * - Stakeholders: Enterprise and ICT architects, business analysts, requirements managers
  * - Concerns: Architecture strategy and tactics, motivation
  * - Purpose: Designing, deciding, informing
  * - Scope: Motivation
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758137 Requirements Realization Viewpoint]]
  */
case object RequirementsRealizationViewPoint extends MotivationViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    nodes.coreElements ++
    Seq[ElementMeta[_]](
      nodes.impl.MotivationElements.goal,
      nodes.impl.MotivationElements.requirement,
      nodes.impl.MotivationElements.constraint,
      nodes.impl.MotivationElements.outcome,
      nodes.impl.MotivationElements.value,
      nodes.impl.MotivationElements.meaning,
      nodes.impl.CompositionElements.location
    )
}

/**
  * he motivation viewpoint allows the designer or analyst to model the motivation aspect, without focusing on certain elements within this aspect. For example, this viewpoint can be used to present a complete or partial overview of the motivation aspect by relating stakeholders, their primary goals, the principles that are applied, and the main requirements on services, processes, applications, and objects.
  * - Stakeholders: Enterprise and ICT architects, business analysts, requirements managers
  * - Concerns: Architecture strategy and tactics, motivation
  * - Purpose: Designing, deciding, informing
  * - Scope: Motivation
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758138 Motivation Viewpoint]]
  */
case object MotivationViewPoint extends MotivationViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    nodes.impl.MotivationElements.motivationElements
}

/**
  * To describe strategic aspects of the enterprise, the viewpoints below have been defined. Each of these viewpoints presents a different perspective on modeling the high-level strategic direction and make-up of the enterprise and allows a modeler to focus on certain aspects. Therefore, each viewpoint considers only a selection of the elements and relationships that have been described in the preceding sections.
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758139 Strategy Viewpoints]]
  */
sealed trait StrategyViewPoints extends ViewPoint {

}

/**
  * The strategy viewpoint allows the business architect to model a high-level, strategic overview of the strategies (courses of action) of the enterprise, the capabilities and resources supporting those, and the envisaged outcomes.
  * - Stakeholders: CxOs, business managers, enterprise and business architects
  * - Concerns: Strategy development
  * - Purpose: Designing, deciding
  * - Scope: Strategy
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758140 Strategy Viewpoint]]
  */
case object StrategyViewPoint extends StrategyViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    nodes.impl.StrategyElements.strategyElements ++
    Seq[ElementMeta[_]](
      nodes.impl.MotivationElements.outcome
    )
}

/**
  * The capability map viewpoint allows the business architect to create a structured overview of the capabilities of the enterprise. A capability map typically shows two or three levels of capabilities across the entire enterprise. It can, for example, be used as a heat map to identify areas of investment. In some cases, a capability map may also show specific outcomes delivered by these capabilities.
  * - Stakeholders: Business managers, enterprise and business architects
  * - Concerns: Architecture strategy and tactics, motivation
  * - Purpose: Designing, deciding
  * - Scope: Strategy
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758141 Capability Map Viewpoint]]
  */
case object CapabilityViewPoint extends StrategyViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    Seq[ElementMeta[_]](
      nodes.impl.MotivationElements.outcome,
      nodes.impl.StrategyElements.capability,
      nodes.impl.StrategyElements.resource
    )
}

/**
  * The outcome realization viewpoint is used to show how the highest-level, business-oriented results are produced by the capabilities and underlying core elements.
  * - Stakeholders: Business managers, enterprise and business architects
  * - Concerns: Business-oriented results
  * - Purpose: Designing, deciding
  * - Scope: Strategy
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758142 Outcome Realization Viewpoint]]
  */
case object OutcomeRealizationViewPoint extends StrategyViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    nodes.coreElements ++
    Seq[ElementMeta[_]](
      nodes.impl.MotivationElements.meaning,
      nodes.impl.MotivationElements.outcome,
      nodes.impl.MotivationElements.value,
      nodes.impl.StrategyElements.capability,
      nodes.impl.StrategyElements.resource,
      nodes.impl.CompositionElements.location
    )
}

/**
  * The resource map viewpoint allows the business architect to create a structured overview of the resources of the enterprise. A resource map typically shows two or three levels of resources across the entire enterprise. It can, for example, be used as a heat map to identify areas of investment. In some cases, a resource map may also show relationships between resources and the capabilities they are assigned to.
  * - Stakeholders: Business managers, enterprise and business architects
  * - Concerns: Architecture strategy and tactics, motivation
  * - Purpose: Designing, deciding
  * - Scope: Strategy
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758143 Resource Map Viewpoint]]
  */
case object ResourceViewPoint extends StrategyViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    Seq[ElementMeta[_]](
      nodes.impl.StrategyElements.resource,
      nodes.impl.StrategyElements.capability,
      nodes.impl.ImplementationElements.workPackage
    )
}

/**
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758144 Implementation and Migration Viewpoints]]
  */
sealed trait ImplementationMigrationViewPoints extends ViewPoint {

}

/**
  * A project viewpoint is primarily used to model the management of architecture change. The “architecture” of the migration process from an old situation (current state Enterprise Architecture) to a new desired situation (target state Enterprise Architecture) has significant consequences on the medium and long-term growth strategy and the subsequent decision-making process.
  * - Stakeholders: (operational) managers, enterprise and ICT architects, employees, shareholders
  * - Concerns: Architecture vision and policies, motivation
  * - Purpose: Deciding, informing
  * - Scope: Implementation and Migration
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758145 Project Viewpoint]]
  */
case object ProjectViewPoint extends ImplementationMigrationViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    Seq[ElementMeta[_]](
      nodes.impl.MotivationElements.goal,
      nodes.impl.ImplementationElements.workPackage,
      nodes.impl.ImplementationElements.implementationEvent,
      nodes.impl.ImplementationElements.deliverable,
      nodes.impl.BusinessElements.businessActor,
      nodes.impl.BusinessElements.businessRole
    )
}

/**
  * The migration viewpoint entails models and concepts that can be used for specifying the transition from an existing architecture to a desired architecture. Since the plateau and gap elements have been quite extensively presented in Section 13.2, here the migration viewpoint is only briefly described and positioned by means of the table below.
  * - Stakeholders: Enterprise architects, process architects, application architects, infrastructure architects and domain architects, employees, shareholders
  * - Concerns: History of models
  * - Purpose: Designing, deciding, informing
  * - Scope: Implementation and Migration
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758146 Migration Viewpoint]]
  */
case object MigrationViewPoint extends ImplementationMigrationViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    Seq[ElementMeta[_]](
      nodes.impl.ImplementationElements.plateau,
      nodes.impl.ImplementationElements.gap
    )
}

/**
  * The implementation and migration viewpoint is used to relate programs and projects to the parts of the architecture that they implement. This view allows modeling of the scope of programs, projects, project activities in terms of the plateaus that are realized or the individual architecture elements that are affected. In addition, the way the elements are affected may be indicated by annotating the relationships.
  * - Stakeholders: (operational) managers, enterprise and ICT architects, employees, shareholders
  * - Concerns: Architecture vision and policies, motivation
  * - Purpose: Deciding, informing
  * - Scope: Multiple layer/Multiple aspect
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758147 Implementation and Migration Viewpoint]]
  */
case object ImplementationMigrationViewPoint extends ImplementationMigrationViewPoints {
  override val possibleElements: Seq[ElementMeta[_]] =
    nodes.coreElements ++
    nodes.impl.ImplementationElements.implementationElements ++
    Seq[ElementMeta[_]](
      nodes.impl.MotivationElements.goal,
      nodes.impl.MotivationElements.requirement,
      nodes.impl.MotivationElements.constraint,
      nodes.impl.CompositionElements.location
    )
}


object ViewPoints {

  val viewPoints: Seq[ViewPoint] = Seq(
    OrganizationViewPoint,
    BusinessProcessCooperationViewPoint,
    ProductViewPoint,
    ApplicationCooperationViewPoint,
    ApplicationUsageViewPoint,
    ImplementationDeploymentViewPoint,
    TechnologyViewPoint,
    TechnologyUsageViewPoint,
    InformationStructureViewPoint,
    ServiceRealizationViewPoint,
    PhysicalViewPoint,
    LayeredViewPoint,
    StakeholderViewPoint,
    GoalRealizationViewPoint,
    RequirementsRealizationViewPoint,
    MotivationViewPoint,
    StrategyViewPoint,
    CapabilityViewPoint,
    OutcomeRealizationViewPoint,
    ResourceViewPoint,
    ProjectViewPoint,
    MigrationViewPoint,
    ImplementationMigrationViewPoint
  )

  val mapViewPoints: Map[String, ViewPoint] = viewPoints.map { v => v.name -> v } .toMap

}