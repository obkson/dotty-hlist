package hlist

sealed trait HList

case class HCons[+H, +T <: HList](val head: H, val tail: T) extends HList {

  def :*:[H2](h: H2): HCons[H2, this.type] = HCons(h, this)

  override def toString = head match {
    case _: HList => s"($head) :*: $tail"
    case _ => s"$head :*: $tail"
  }

}

case class HNil() extends HList {

  def :*:[H2](h: H2): HCons[H2, HNil] = HCons(h, this)

}

