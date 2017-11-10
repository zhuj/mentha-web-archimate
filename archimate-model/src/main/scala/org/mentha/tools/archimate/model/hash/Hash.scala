package org.mentha.tools.archimate.model.hash

trait Hash {

  @inline def _bool(b: Boolean): Int = b.hashCode
  @inline def _str(s: String): Int = s.hashCode
  @inline def _num(n: Number): Int = (n.doubleValue()*1024).toInt

  @inline def _add(h: Int, v: Int): Int = ((h<<5)-h) + v

  @inline def _arr(a: Seq[Any]): Int = a.foldLeft(0) { case (h, v) => _add(h, hash(v)) }
  @inline def _map(m: Map[_, Any]): Int = m.toStream.foldLeft(0) { case (h, (k, v)) => _add(h, hash(k) ^ hash(v)) }
  @inline def _obj(o: (String, Any)*): Int = o.sortBy(_._1).foldLeft(0) { case (h, (k, v)) => _add(h, k.hashCode ^ hash(v)) }

  def hash(v: Any): Int = v match {
    case m: Map[_, _] => _map(m)
    case a: Seq[_] => _arr(a)
    case s: String => _str(s)
    case n: Number => _num(n)
    case b: Boolean => _bool(b)
    case _ if v == null => 0
    case _ => throw new IllegalStateException(s"Unsupported type: ${v}")
  }

}
