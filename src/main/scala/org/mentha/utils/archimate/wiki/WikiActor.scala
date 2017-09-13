package org.mentha.utils.archimate.wiki

import akka.actor._
import org.apache.commons.lang3.StringUtils
import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.view._


object WikiActor {
  case class Sync(modelJson: String)

}

private[wiki] class ModelSync(val wiki: Wiki, val model: Model) {
  private val space = wiki
    .getSpace(s"archimate-model-${model.id}", orCreate = true)
    .withTitle { s"Archimate Model: ${model.name}" }
    .commit

  private def sync[T<:IdentifiedArchimateObject](
    name: String,
    objects: Iterable[T],
    pageTitle: T=>String,
    pageHeader: T=>String,
    pageHeaderBody: T=>String
  ): WikiPage = {

    val page = space
      .getPage(s"archimate-sync-page-${name}", orCreate = true)
      .withTitle { StringUtils.capitalize(name) }
      .commit

    // refresh element pages
    val pageIds = objects.map {
      el => space
        .getPage(el.id, orCreate = true)
        .moveTo(page.id)
        .withTitle { _ => pageTitle(el) }
        .withText {
          _.withHeader {
            wiki
              .textSection { pageHeader(el) }
              .withBody { wiki.textSection { pageHeaderBody(el) } :: Nil }
          }
        }
        .withMeta("json", json.toJsonPair(el).toString())
        .commit
        .id
    }.toSet

    // remove the rest pages
    page
      .children.direct
      .filter { p => !pageIds.contains(p.id) }
      .foreach { _.markAsDeleted.commit }

    // return the container page
    page
  }

  def sync(): WikiSpace = {

    // elements
    sync[Element](
      name = "elements",
      objects = model.concepts[Element],
      pageTitle = e => s"${StringUtils.capitalize(e.meta.name)}: ${e.name}",
      pageHeader = e => s"${StringUtils.capitalize(e.meta.name)}: ${e.name}",
      pageHeaderBody = e => StringUtils.trimToEmpty(e.description)
    )

    // views
    sync[View](
      name = "views",
      objects = model.views,
      pageTitle = v => s"View: ${v.name}",
      pageHeader = v => s"${StringUtils.capitalize(v.viewpoint.name)}: ${v.name}",
      pageHeaderBody = v => ""
    )

    // return the space
    space
  }

}


class WikiActor(val wiki: Wiki) extends Actor with ActorLogging {

  private def sync(modelJson: String): Unit = {
    val model = json.fromJsonString(modelJson)
    new ModelSync(wiki, model).sync()
  }

  override def receive: Receive = {
    case WikiActor.Sync(modelJson) => sync(modelJson)
    case _ =>
  }

}
