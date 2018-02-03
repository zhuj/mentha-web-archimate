package doc.examples.softdev

import doc.examples.school.globalenglish.MkGlobalEnglish.model
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


object MkSoftwareDevelopment extends MkModel {

  implicit val model: Model = new Model withId "real-software-development"
  implicit val space: Size = Size(40, 50)

  private def layout(view: View) = {
    in(view) layoutLayered()
  }

  val base = new {

    implicit val commonView: View = model.add(MkModel.generateViewId("v-common")) { new View() withName "Common" }

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


  // https://www.scrum.org/resources/scrum-guide

  // scrum values:
  // * respect - respect themselves, all team members, PO & SM and final users
  // * courage - personal (each and every) courage to always tell the truth
  // * commitment - whole the team is responsible for the whole sprint (personal responsibility for the own tasks is not enough)
  // * focus - focus on the spring goals, on the project result, proactive (preemptive) thinking
  // * openness - to be ready to help, to be ready to share knowledge ...

  // scrum principles:
  // * transparent - don't hide the problems (even from PO & SM), don't hide the problems from themselves
  // * inspection - review results of work (previous iterations)
  // * adaption - change the behavior according to the inspection


  val roles = new {

    implicit val view = model.add(MkModel.generateViewId("v-scrum-roles")) { new View() withName "Scrum: Roles" }

    // main roles & actors

    // The King, responsible for the product
    val productOwner = << { businessRole withName "Product owner" }

    // Couch, responsible for the communication, to make Scrum work for you (who knows how to cook the Scrum)
    val scrumMaster = << { businessRole withName "Scrum master" }

    // Developers, responsible for technologies (who knows how to implement)
    val team = << { businessCollaboration withName "Team" }

    // scrum itself
    val scrum = << { businessProcess withName "Scrum" }
    << { productOwner `assigned to` scrum }
    << { scrumMaster `assigned to` scrum }
    << { team `assigned to` scrum }

    // scrum master roles:

    // build *shared understanding* (make people communicate each other effectively)
    val facilitator = << { businessRole withName "Facilitator" }
    << { scrumMaster `composes` facilitator }

    // teaches, answers the questions, share the knowledge/techniques
    val mentor = << { businessRole withName "Mentor" }
    << { scrumMaster `composes` mentor }

    // trains, asks questions, helps explore, help people/team to find a solution by themselves (don't answer questions, force them think)
    val coach = << { businessRole withName "Coach" }
    << { scrumMaster `composes` coach }

    // protects the team, challenges environment
    val changeAgent = << { businessRole withName "Change agent" }
    << { scrumMaster `composes` changeAgent }

    // actively does nothing: inspect/collect the whole process for the following adaption
    val observer = << { businessRole withName "Observer" }
    << { scrumMaster `composes` observer }

    // team roles
    // https://flowchainsensei.wordpress.com/2011/02/25/the-many-roles-in-software-projects/
    // https://www.infoq.com/articles/development-manager-role
    // http://zimmer.csufresno.edu/~sasanr/Teaching-Material/SAD/breaking%20down%20software%20development%20roles.pdf
    // https://medium.com/@SherrieRose/software-project-team-roles-and-responsibilities-152a7d575759
    // https://www.atlascode.com/blog/software-development-project-roles-and-responsibilities/

    val analyst = << { businessRole withName "Analyst" }
    << { team `aggregates` analyst }

    val architect = << { businessRole withName "Architect" }
    << { team `aggregates` architect }

    val developer = << { businessRole withName "Developer" }
    << { team `aggregates` developer }

    val qa = << { businessRole withName "QA" }
    << { team `aggregates` qa }

    // layout
    layout(view)

  }

  val artifacts = new {

    implicit val view = model.add(MkModel.generateViewId("v-scrum-artifacts")) { new View() withName "Scrum: Artifacts" }

    // something valuable, which could be done and delivered separately
    val feature = << { businessObject withName "Feature" }

    // Project scope, the complete (at the moment) list to be done for the project
    val productBacklog = << { businessObject withName "Product backlog" }

    // backlog consists of features
    << { productBacklog `aggregates` feature }

    // product increment: should increment functionality (quantity aspect)
    // potentially shippable: possible to use (quality aspect)
    val productIncrement = << { businessObject withName "Potentially shippable product increment" }
    << { productIncrement `associated with` feature withPredicate "for" }

    // an agreement (accepted by the whole Team) defines what we calls «done» (when the feature is good enough to count it as done)
    // global acceptance criteria for the whole features
    // kind of trade-of (between time and quality) to mark the feature «done» as soon as it possible
    // i.e. the feature hasn't done yet even if the developer says «it works locally»
    val definitionOfDone = << { businessObject withName "Definition of done" }

    // Visualizes where we are, i.e. burndown chart
    val sprintProgress = << { businessObject withName "Sprint progress" }

    // what the team both decides and is accepted to do
    val teamCommitments = << { businessObject withName "Team commitments" }

    // non-formal results to be done
    val sprintGoal = << { businessObject withName "Sprint goal" }

    // The complete (at the moment) list to be done in the sprint
    val sprintBacklog = << { businessObject withName "Sprint backlog" }

    // is a subset of the project backlog
    << { sprintBacklog `associated with` productBacklog withPredicate "is subset of" }

    // Sprint backlog and goal approved by PO and the Team
    val sprintPlan = << { businessObject withName "Sprint plan" }

    // composes sprintBacklog and sprintGoal
    << { sprintPlan `composes` sprintBacklog }
    << { sprintPlan `composes` sprintGoal }

    // Sprint backlog and goal approved by PO and the Team
    val dailyPlan = << { businessObject withName "Daily plan" }

    // task
    val task = << { businessObject withName "Task" }
    << { feature `aggregates` task }

    // acceptance criteria
    val acceptanceCriteria = << { businessObject withName "Acceptance criteria" }
    << { task `composes` acceptanceCriteria }

    // layout
    layout(view)

  }

  // ==========================================================
  // Out of meeting: Product owner maintains the backlog actual

  val productBacklogMaintenance = new {

    implicit val view = model.add(MkModel.generateViewId("v-scrum-product-backlog-maintenance")) { new View() withName "Scrum: Process: Product Backlog Maintenance" }
    val process = << { businessProcess withName "Product backlog maintenance" }

    << { process `writes` artifacts.productBacklog }

    // Assignment
    << { roles.productOwner `assigned to` process }

    // layout
    layout(view)

  }

  // ========================================================================
  // (1) Iteration planing: to synchronize the vision between PO and the Team
  // * PO prioritize backlog,
  // * Team estimates and takes issues to sprint (sprint backlog)
  // * PO matches features worth with the team costs

  val iterationPlaning = new {

    implicit val view = model.add(MkModel.generateViewId("v-scrum-sprint-planing")) { new View() withName "Scrum: Process: Sprint Planing" }
    val process = << { businessProcess withName "Iteration planing (meeting)" }

    // Inspection: product backlog, commitments (i.e., from retro), «Definition of Done»
    << { process `reads` artifacts.productBacklog }
    << { process `reads` artifacts.definitionOfDone }
    << { process `reads` artifacts.teamCommitments }

    // Adaption: determine sprint goal (predict/forecast sprint results), create and approve sprint backlog
    << { process `writes` artifacts.sprintGoal }
    << { process `writes` artifacts.sprintBacklog }

    // Assignment
    << { roles.productOwner `assigned to` process }
    << { roles.scrumMaster `assigned to` process }
    << { roles.team `assigned to` process }

    // layout
    in(view) borrowEdges { artifacts.view }
    layout(view)

  }

  // ========================================================================================================
  // (2) Daily stand-up: to synchronize the status in the team (inside the team), make *shared understanding*
  // All the team together verifies that nobody blocks others, there is no blockers (blocker - smth which blocks *the Team*)

  val dailyStandUp = new {

    implicit val view = model.add(MkModel.generateViewId("v-scrum-daily-stand-up")) { new View() withName "Scrum: Process: Daily Standup" }
    val process = << { businessProcess withName "Daily standup" }

    // Inspection: progress toward sprint goal
    << { process `reads` artifacts.sprintPlan }
    << { process `reads` artifacts.sprintProgress }
    << { process `reads` artifacts.teamCommitments }

    // Adaption: change sprint backlog, modify daily plan
    << { process `writes` artifacts.sprintBacklog }
    << { process `writes` artifacts.dailyPlan }

    // Assignment
    // << { roles.productOwner `assigned to` process } // optional?
    // << { roles.scrumMaster `assigned to` process } // optional?
    << { roles.team `assigned to` process }

    // layout
    in(view) borrowEdges { artifacts.view }
    layout(view)

  }

  // ============================================================================
  // (3) Backlog grooming: to resolve possible blockers in the backlog in advance
  // analyze backlog so that the following iteration planing process won't have any blockers

  val backlogGrooming = new  {

    implicit val view = model.add(MkModel.generateViewId("v-scrum-backlog-grooming")) { new View() withName "Scrum: Process: Backlog Grooming" }
    val process = << { businessProcess withName "Backlog grooming" }

    // Inspection: product backlog (proactive actions, think in advance)
    << { process `reads` artifacts.productBacklog }

    // Adaption: highlight/resolve possible blockers, change backlog (accordingly)
    << { process `writes` artifacts.productBacklog }

    // Assignment
    << { roles.productOwner `assigned to` process }
    << { roles.team `assigned to` process }

    // layout
    in(view) borrowEdges { artifacts.view }
    layout(view)

  }

  // ==================================================================================================
  // (4) Scrum of Scrums: to synchronize the vision between teams (if there are some external blockers)

  // ...

  // ===========================================================
  // (5) Demo / Sprint review: to collect feedback (the product)
  // it modifies PO behavior

  val sprintReview = new {

    implicit val view = model.add(MkModel.generateViewId("v-scrum-sprint-review")) { new View() withName "Scrum: Process: Sprint Review (Demo)" }
    val process = << { businessProcess withName "Sprint review" }

    // Inspection: product increment (what has been done), product backlog (how it moves us to the release), market-business conditions (inspect all the world)
    << { process `reads` artifacts.sprintPlan }
    << { process `reads` artifacts.productBacklog }

    // Adaption: change product backlog (accordingly)
    << { process `writes` artifacts.productBacklog }

    // Assignment
    << { roles.productOwner `assigned to` process }
    << { roles.scrumMaster `assigned to` process }
    << { roles.team `assigned to` process }

    // layout
    in(view) borrowEdges { artifacts.view }
    layout(view)

  }

  // ===================================================================
  // (6) Retro / Sprint retrospective: to collect feedback (the process)
  // it modifies Scrum master behavior

  val sprintRetrospective = new  {

    implicit val view = model.add(MkModel.generateViewId("v-scrum-sprint-retrospective")) { new View() withName "Scrum: Process: Sprint Retrospective" }
    val process = << { businessProcess withName "Sprint retrospective" }

    // Inspection: team and collaboration, technologies and engineering, «Definition of Done»
    << { process `reads` artifacts.sprintPlan }
    << { process `reads` artifacts.sprintProgress }
    << { process `reads` artifacts.definitionOfDone }

    // Adaption: define commitments (list of actionable improvements to be done), modify «Definition of Done»
    << { process `writes` artifacts.teamCommitments }
    << { process `writes` artifacts.definitionOfDone }

    // Assignment
    << { roles.productOwner `assigned to` process }
    << { roles.scrumMaster `assigned to` process }
    << { roles.team `assigned to` process }

    // layout
    in(view) borrowEdges { artifacts.view }
    layout(view)
  }

  // ===================================================================

  val featureDecomposition = new  {

    implicit val view = model.add(MkModel.generateViewId("v-feature-decomposition")) { new View() withName "Team: Process: Feature Decomposition" }
    val process = << { businessProcess withName "Feature analysis" }

    << { process `reads` artifacts.feature }
    << { process `reads` artifacts.teamCommitments }

    << { process `writes` artifacts.task }

    // Assignment
    << { roles.analyst `assigned to` process }
    << { roles.architect `assigned to` process }

    // layout
    in(view) borrowEdges { artifacts.view }
    layout(view)

  }

  // ===================================================================

  val taskWriting = new  {

    implicit val view = model.add(MkModel.generateViewId("v-task-writing")) { new View() withName "Team: Process: Task Writing" }
    val process = << { businessProcess withName "Task writing" }

    << { process `reads` artifacts.feature }
    << { process `reads` artifacts.definitionOfDone }
    << { process `reads` artifacts.teamCommitments }


    << { process `writes` artifacts.task }
    << { process `writes` artifacts.acceptanceCriteria }

    // Assignment
    << { roles.analyst `assigned to` process }

    // layout
    in(view) borrowEdges { artifacts.view }
    layout(view)

  }

  // ===================================================================

  val featureDevelopment = new {

    implicit val view = model.add(MkModel.generateViewId("v-feature-development")) { new View() withName "Team: Process: Feature Development" }
    val process = << { businessProcess withName "Feature development" }

    << { process `reads` artifacts.definitionOfDone }
    << { process `reads` artifacts.teamCommitments }
    << { process `reads` artifacts.dailyPlan }
    << { process `reads` artifacts.feature }
    << { process `reads` artifacts.task }
    << { process `reads` artifacts.acceptanceCriteria }

    << { process `writes` artifacts.productIncrement }

    // Assignment
    << { roles.developer `assigned to` process }

    // layout
    in(view) borrowEdges { artifacts.view }
    layout(view)

  }

  // ===================================================================

  val qualityAssurance = new  {

    implicit val view = model.add(MkModel.generateViewId("v-quality-assurance")) { new View() withName "Team: Process: Quality Assurance" }
    val process = << { businessProcess withName "Quality assurance" }

    << { process `reads` artifacts.definitionOfDone }
    << { process `reads` artifacts.teamCommitments }
    << { process `reads` artifacts.feature }
    << { process `reads` artifacts.task }
    << { process `reads` artifacts.acceptanceCriteria }
    << { process `reads` artifacts.productIncrement }

    << { process `writes` artifacts.task }
    << { process `writes` artifacts.acceptanceCriteria }
    << { process `writes` artifacts.sprintProgress }

    // Assignment
    << { roles.qa `assigned to` process }

    // layout
    in(view) borrowEdges { artifacts.view }
    layout(view)

  }

  // ===================================================================


  // product owner aims:

  // minimize building, maximize learning:
  // build it -> measure it -> learn from it -> adaption
  // products metrics should motivate the team to achieve results


  // team: self organized teams (organized by the working agreements)
  // there should not be micromanagement and chaos
  // alignment - alignment/moved to the result, to architecture, to ...
  // autonomy - self management

  val overallView: View = model.add(MkModel.generateViewId("v-overall")) { new View() withName "Overall" }

  in(overallView)
    .add { roles.view }
    .add { artifacts.view }
    .add { productBacklogMaintenance.view }
    .add { iterationPlaning.view }
    .add { dailyStandUp.view }
    .add { backlogGrooming.view }
    .add { sprintReview.view }
    .add { sprintRetrospective.view }
    .add { featureDecomposition.view }
    .add { taskWriting.view }
    .add { featureDevelopment.view }
    .add { qualityAssurance.view }
    .resizeNodesToTitle()
    .layoutSimple()

  def main(args: Array[String]): Unit = {
    publishModel(model)
  }

}
