import org.mentha.utils.uuid.FastTimeBasedIdGenerator

object hash {

  // 64 digits = 6 bits
  private val digits = Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '$', '#')

  def h(a: Int, b: Int, S: Int): Int = {
    digits(a).toInt * S + digits(b).toInt
  }

  def main(args: Array[String]): Unit = {

    var best = (0, 0, Seq[(Int, (Int, Int))]())

    for {
      s <- 13 until 4096
    } {
      val hashes = for {
        a <- 0 until 64
        b <- 0 until 64
      } yield {
        (h(a, b, s) & 0xfff) -> (a, b)
      }

      val uniq = hashes.toMap.toSeq.sorted
      val l = uniq.size
      if (l > best._2) {
        best = (s, l, uniq)
      }
    }

    println(best._1)
    println(best._2)

    println(
      best._3
        .map { case (i, (a, b)) => (i, s"${digits(a)}${digits(b)}") }

    )



  }

}
