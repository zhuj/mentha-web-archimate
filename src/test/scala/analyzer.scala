import scala.xml.XML

// code for model analysis
object analyzer {

  def main(args: Array[String]): Unit = {

    val xml = XML.load(this.getClass.getClassLoader.getResource("archimate/model.xml"))

    val keys = (xml \ "relations" \ "key")
      .map { el => (el \ "@char").text.charAt(0) -> ( (el \ "@relationship").text, (el \ "@verbs" ).text ) }
      .toMap

    val association: Char = 'o' // <key char="o" relationship="AssociationRelationship" />
    val rels = (xml \ "relations" \ "source")
      .flatMap {
        s => (s \ "target").flatMap {
          t => (t \ "@relations").text.toSeq
            .filterNot { _ == association }
            .map { keys }
            .map { r => (
              r._1,
              (s \ "@concept").text,
              (t \ "@concept").text
            ) }
        }
      }

//    val relsMap = rels
//      .groupBy { case (r, _, _) => r }
//
    val elements = (xml \ "element")
      .map { el => (el \ "@name").text -> ((el \ "@layer").text, (el \ "@parent").text) }
//
//    val layers = elements
//      .map { case (_, (layer, _)) => layer }
//      .toSet


    println(elements.size)

    // aggregates, composes, specializes -> is always allowed self -> self
    {
      {
        val x = rels
          .filterNot { case (r, src, dst) => src == "Junction" || dst == "Junction" }
          .filter { case (r, src, dst) => r == "CompositionRelationship" }
          .groupBy { case (r, src, dst) => dst }
          .mapValues {
            _ map { case (r, src, dst) => src }
          }

        println(x.size)
        x
          .toSeq
          .filter { case (dst, srcs) => !srcs.contains(dst) }
          .foreach { case (dst, _) => println(dst) }

      }

      {
        val x = rels
          .filterNot { case (r, src, dst) => src == "Junction" || dst == "Junction" }
          .filter { case (r, src, dst) => r == "AggregationRelationship" }
          .groupBy { case (r, src, dst) => dst }
          .mapValues {
            _ map { case (r, src, dst) => src }
          }

        println(x.size)
        x
          .toSeq
          .filter { case (dst, srcs) => !srcs.contains(dst) }
          .foreach { case (dst, _) => println(dst) }

      }

      {
        val x = rels
          .filterNot { case (r, src, dst) => src == "Junction" || dst == "Junction" }
          .filter { case (r, src, dst) => r == "SpecializationRelationship" }
          .groupBy { case (r, src, dst) => dst }
          .mapValues {
            _ map { case (r, src, dst) => src }
          }

        println(x.size)
        x
          .toSeq
          .filter { case (dst, srcs) => !srcs.contains(dst) }
          .foreach { case (dst, _) => println(dst) }

      }
    }


    //    x.toSeq
//      // .map { case (dst, srcs) => dst -> srcs.filter { src => src != dst } }
//      .sortBy { case (dst, srcs) => (srcs.size, dst) }
//      .foldLeft(Set[String]()) {
//        case (set, (dst, srcs)) => {
//
//          var result = set
//          var affected = false
//
//          {
//            val tmp = (result ++ srcs) -- result
//            if (tmp.nonEmpty) {
//              affected = true
//              result = result ++ tmp
//              println(dst + " <- (" + result.size + "): + " + tmp.toSeq.sorted.mkString(", "))
//            }
//          }
//
//          {
//            val tmp = result -- srcs
//            if (tmp.nonEmpty) {
//              affected = true
//              result = result -- tmp
//              println(dst + " <- (" + result.size + "): - " + tmp.toSeq.sorted.mkString(", "))
//            }
//          }
//
//          if (!affected) {
//            println(dst + " <- (" + result.size + "): = ")
//          }
//
//          result
//        }
//      }




  }

}
