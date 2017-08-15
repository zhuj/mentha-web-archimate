package org.mentha.utils.archimate.model.nodes.impl

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes._
import org.mentha.utils.archimate.model.edges._

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
sealed trait MotivationElement extends MotivationLayer {}

/**
 * A stakeholder is the role of an individual, team, or organization (or classes thereof) that represents their interests in the outcome of the architecture.
 * @note This definition is based on the definition in the TOGAF framework. A stakeholder has one or more interests in, or concerns about, the organization and its Enterprise Architecture. In order to direct efforts to these interests and concerns, stakeholders change, set, and emphasize goals. Stakeholders may also influence each other. Examples of stakeholders are the CEO, the board of directors, shareholders, customers, business and application architects, but also legislative authorities. The name of a stakeholder should preferably be a noun.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap06.html#_Toc451757975 Stakeholder ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Stakeholder extends ExternalActiveStructureElement with MotivationElement {
  @inline override def meta: ElementMeta[Stakeholder] = MotivationElements.stakeholder
}

/**
 * A driver represents an external or internal condition that motivates an organization to define its goals and implement the changes necessary to achieve them.
 * @note Drivers may be internal, in which case they are usually associated with a stakeholder, and are often called “concerns”. Stakeholder concerns are defined in the TOGAF framework as ”the key interests that are crucially important to the stakeholders in a system, and determine the acceptability of the system. Concerns may pertain to any aspect of the function, development, or operation of the system, including considerations such as performance, reliability, security, distribution, and evolvability.” Examples of internal drivers are Customer satisfaction and Profitability. Drivers of change may also be external; e.g., economic changes or changing legislation. The name of a driver should preferably be a noun.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap06.html#_Toc451757976 Driver ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Driver extends GenericElement with MotivationElement {
  @inline override def meta: ElementMeta[Driver] = MotivationElements.driver
}

/**
 * An assessment represents the result of an analysis of the state of affairs of the enterprise with respect to some driver.
 * @note An assessment may reveal strengths, weaknesses, opportunities, or threats for some area of interest. These need to be addressed by adjusting existing goals or setting new ones, which may trigger changes to the Enterprise Architecture.
 * @note Strengths and weaknesses are internal to the organization. Opportunities and threats are external to the organization. Weaknesses and threats can be considered as problems that need to be addressed by goals that “negate” the weaknesses and threats. Strengths and opportunities may be translated directly into goals. For example, the weakness “Customers complain about the helpdesk” can be addressed by defining the goal “Improve helpdesk”. Or, the opportunity “Customers favor insurances that can be managed online” can be addressed by the goal “Introduce online portfolio management”. The name of an assessment should preferably be a noun or a (very) short sentence.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap06.html#_Toc451757977 Assessment ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Assessment extends GenericElement with MotivationElement {
  @inline override def meta: ElementMeta[Assessment] = MotivationElements.assessment
}

/**
 * A goal represents a high-level statement of intent, direction, or desired end state for an organization and its stakeholders.
 * @note In principle, a goal can represent anything a stakeholder may desire, such as a state of affairs, or a produced value. Examples of goals are: to increase profit, to reduce waiting times at the helpdesk, or to introduce online portfolio management. Goals are typically used to measure success of an organization.
 * @note Goals are generally expressed using qualitative words; e.g., “increase”, “improve”, or “easier”. Goals can also be decomposed; e.g., Increase profit can be decomposed into the goals Reduce cost and Increase sales. However, it is also very common to associate concrete outcomes with goals, which can be used to describe both the quantitative and time-related results that are essential to describe the desired state, and when it should be achieved.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap06.html#_Toc451757980 Goal ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Goal extends GenericElement with MotivationElement {
  @inline override def meta: ElementMeta[Goal] = MotivationElements.goal
}

/**
 * An outcome represents an end result that has been achieved.
 * @note Outcomes are high-level, business-oriented results produced by capabilities of an organization, and by inference by the core elements of its architecture that realize these capabilities. Outcomes are tangible, possibly quantitative, and time-related, and can be associated with assessments. An outcome may have a different value for different stakeholders.
 * @note The notion of outcome is important in business outcome-driven approaches to Enterprise Architecture and in capability-based planning. Outcomes are closely related to requirements, goals, and other intentions. Outcomes are the end results, and goals or requirements are often formulated in terms of outcomes that should be realized. Capabilities are designed to achieve such outcomes.
 * @note Outcome names should unambiguously identify end results that have been achieved in order to avoid confusion with actions or goals. At a minimum, outcome names should consist of a noun identifying the end result followed by a past-tense verb or adjective indicating that the result has been achieved. Examples include “First-place ranking achieved” and “Key supplier partnerships in place”. Outcome names can also be more specific; e.g., “2015 quarterly profits rose 10% year over year beginning in Q3”.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap06.html#_Toc451757981 Outcome ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Outcome extends GenericElement with MotivationElement {
  @inline override def meta: ElementMeta[Outcome] = MotivationElements.outcome
}

/**
 * A principle represents a qualitative statement of intent that should be met by the architecture.
 * @note Principles are strongly related to goals and requirements. Similar to requirements, principles define intended properties of systems. However, in contrast to requirements, principles are broader in scope and more abstract than requirements. A principle defines a general property that applies to any system in a certain context. A requirement defines a property that applies to a specific system as described by an architecture.
 * @note A principle needs to be made specific for a given system by means of one or more requirements, in order to enforce that the system conforms to the principle. For example, the principle “Information management processes comply with all relevant laws, policies, and regulations” is realized by the requirements that are imposed by the actual laws, policies, and regulations that apply to the specific system under design.
 * @note A principle is motivated by some goal or driver. For example, the aforementioned principle may be motivated by the goal to maintain a good reputation and/or the goal to avoid penalties. The principle provides a means to realize its motivating goal, which is generally formulated as a guideline. This guideline constrains the design of all systems in a given context by stating the general properties that are required from any system in this context to realize the goal. Principles are intended to be more stable than requirements in the sense that they do not change as quickly as requirements may do. Organizational values, best practices, and design knowledge may be reflected and made applicable in terms of principles.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap06.html#_Toc451757982 Principle ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Principle extends GenericElement with MotivationElement {
  @inline override def meta: ElementMeta[Principle] = MotivationElements.principle
}

/**
 * A requirement represents a statement of need that must be met by the architecture.
 * @note In the end, a business goal must be realized by a plan or concrete change goal, which may or may not require a new system or changes to an existing system.
 * @note The term “system” is used in its general meaning; i.e., as a group of (functionally) related elements, where each element may be considered as a system again. Therefore, a system may refer to any active structural element, behavior element, or passive structural element of some organization, such as a business actor, application component, business process, application service, business object, or data object.
 * @note Requirements model the properties of these elements that are needed to achieve the “ends” that are modeled by the goals. In this respect, requirements represent the “means” to realize goals.
 * @note During the design process, goals may be decomposed until the resulting sub-goals are sufficiently detailed to enable their realization by properties that can be exhibited by systems. At this point, goals can be realized by requirements that demand these properties from the systems.
 * @note For example, one may identify two alternative requirements to realize the goal to improve portfolio management: (1) By assigning a personal assistant to each customer, or (2) By introducing online portfolio management. The former requirement can be realized by a human actor and the latter by a software application. These requirements can be decomposed further to define the requirements on the human actor and the software application in more detail.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap06.html#_Toc451757983 Requirement ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Requirement extends GenericElement with MotivationElement {
  @inline override def meta: ElementMeta[Requirement] = MotivationElements.requirement
}

/**
 * A constraint represents a factor that prevents or obstructs the realization of goals.
 * @note In contrast to a requirement, a constraint does not prescribe some intended functionality of the system to be realized, but imposes a restriction on the way it operates or may be realized. This may be a restriction on the implementation of the system (e.g., specific technology that is to be used), a restriction on the implementation process (e.g., time or budget constraints), or a restriction on the functioning of the system (e.g., legal constraints).
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap06.html#_Toc451757984 Constraint ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Constraint extends GenericElement with MotivationElement {
  @inline override def meta: ElementMeta[Constraint] = MotivationElements.constraint
}

/**
 * Meaning represents the knowledge or expertise present in, or the interpretation given to, a core element in a particular context.
 * @note A meaning represents the interpretation of an element of the architecture. In particular, this is used to describe the meaning of passive structure elements (for example, a document, message). It is a description that expresses the intent of that element; i.e., how it informs the external user.
 * @note It is possible that different users view the informative functionality of an element differently. For example, what may be a “registration confirmation” for a client could be a “client mutation” for a CRM department (assuming for the sake of argument that it is modeled as an external user). Also, various different representations may carry essentially the same meaning. For example, various different documents (a web document, a filled-in paper form, a “client contact” report from the call center) may essentially carry the same meaning.
 * @note A meaning can be associated with any core element. To denote that a meaning is specific to a particular stakeholder, this stakeholder can also be associated to the meaning. The name of a meaning should preferably be a noun or noun phrase.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap06.html#_Toc451757987 Meaning ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Meaning extends GenericElement with MotivationElement {
  @inline override def meta: ElementMeta[Meaning] = MotivationElements.meaning
}

/**
 * Value represents the relative worth, utility, or importance of a core element or an outcome.
 * @note Value may apply to what a party gets by selling or making available some product or service, or it may apply to what a party gets by buying or obtaining access to it. Value is often expressed in terms of money, but it has long since been recognized that non-monetary value is also essential to business; for example, practical/functional value (including the right to use a service), and the value of information or knowledge. Though value can hold internally for some system or organizational unit, it is most typically applied to external appreciation of goods, services, information, knowledge, or money, normally as part of some sort of customer-provider relationship.
 * @note A value can be associated with all core elements of an architecture as well as with outcomes. To model the stakeholder for whom this value applies, this stakeholder can also be associated with that value. Although the name of a value can be expressed in many different ways (including amounts, objects), where the “functional” value of an architecture element is concerned it is recommended to try and express it as an action or state that can be performed or reached as a result of the corresponding element being available.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap06.html#_Toc451757988 Value ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Value extends GenericElement with MotivationElement {
  @inline override def meta: ElementMeta[Value] = MotivationElements.value
}

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
object MotivationElements {

  case object stakeholder extends ElementMeta[Stakeholder] {
    override def newInstance(): Stakeholder = new Stakeholder
    override def layerObject: LayerObject = MotivationLayer
    override def key: String = "msh"
    override def name: String = "stakeholder"
  }
  case object driver extends ElementMeta[Driver] {
    override def newInstance(): Driver = new Driver
    override def layerObject: LayerObject = MotivationLayer
    override def key: String = "mdr"
    override def name: String = "driver"
  }
  case object assessment extends ElementMeta[Assessment] {
    override def newInstance(): Assessment = new Assessment
    override def layerObject: LayerObject = MotivationLayer
    override def key: String = "mas"
    override def name: String = "assessment"
  }
  case object goal extends ElementMeta[Goal] {
    override def newInstance(): Goal = new Goal
    override def layerObject: LayerObject = MotivationLayer
    override def key: String = "mgo"
    override def name: String = "goal"
  }
  case object outcome extends ElementMeta[Outcome] {
    override def newInstance(): Outcome = new Outcome
    override def layerObject: LayerObject = MotivationLayer
    override def key: String = "moc"
    override def name: String = "outcome"
  }
  case object principle extends ElementMeta[Principle] {
    override def newInstance(): Principle = new Principle
    override def layerObject: LayerObject = MotivationLayer
    override def key: String = "mpr"
    override def name: String = "principle"
  }
  case object requirement extends ElementMeta[Requirement] {
    override def newInstance(): Requirement = new Requirement
    override def layerObject: LayerObject = MotivationLayer
    override def key: String = "mrq"
    override def name: String = "requirement"
  }
  case object constraint extends ElementMeta[Constraint] {
    override def newInstance(): Constraint = new Constraint
    override def layerObject: LayerObject = MotivationLayer
    override def key: String = "mcs"
    override def name: String = "constraint"
  }
  case object meaning extends ElementMeta[Meaning] {
    override def newInstance(): Meaning = new Meaning
    override def layerObject: LayerObject = MotivationLayer
    override def key: String = "mmn"
    override def name: String = "meaning"
  }
  case object value extends ElementMeta[Value] {
    override def newInstance(): Value = new Value
    override def layerObject: LayerObject = MotivationLayer
    override def key: String = "mvl"
    override def name: String = "value"
  }

  val motivationElements: Seq[ElementMeta[Element]] = Seq(stakeholder, driver, assessment, goal, outcome, principle, requirement, constraint, meaning, value)

}
