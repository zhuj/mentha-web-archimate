package org.mentha.tools.archimate.model

sealed trait AccessType {
  def read: Boolean = false
  def write: Boolean = false
}

sealed trait ReadAccessType extends AccessType {
  override def read: Boolean = true
}

sealed trait WriteAccessType extends AccessType {
  override def write: Boolean = true
}

case object ReadAccess extends ReadAccessType {

}

case object WriteAccess extends WriteAccessType {

}

case object ReadWriteAccess extends ReadAccessType with WriteAccessType {

}