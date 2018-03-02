package hlist

sealed trait HList
final case class HCons[+H, +T <: HList](val head: H, val tail: T) extends HList {
  def :*:[H2](h: H2): HCons[H2, this.type] = HCons(h, this)
  override def toString = head match {
    case _: HList => s"($head) :*: $tail"
    case _ => s"$head :*: $tail"
  }
}
sealed trait HNil extends HList // make HNil a proper type
final case object HNil extends HNil {
  def :*:[H2](h: H2): HCons[H2, HNil] = HCons(h, this)
}

class Field[L <: String, V](val label: L, val value: V) {
  override def equals(that : Any) = that match {
    case Field(l, v) => label == l && value == v
    case _ => false
  }
  override def toString = s"($label ->> $value)"
}

object Field {
  def apply[L <: String, V](label: L, value: V) = new Field[label.type, V](label, value)
  def unapply(f: Field[String, Any]): Option[(String, Any)] = Some((f.label, f.value))

  implicit def partiallyApplyField[L <: String](l: L): PartField[l.type] = new PartField[l.type](l)
  class PartField[L <: String](val l: L) {
    def ->>[V](v: V): Field[l.type, V] = new Field[l.type, V](l, v)
  }
}


trait Selector[F <: String, L <: HList] {
  type V <: Any
  def get(l: L): V
}

object Selector {

  type SelectorAux[F <: String, L <: HList, VOut] = Selector[F, L] {
    type V = VOut
  }

  implicit def headSelector[F <: String, T <: HList, VOut]: SelectorAux[F, HCons[Field[F, VOut], T], VOut]
  = new Selector[F, HCons[Field[F, VOut], T]] {
      type V = VOut
      def get(l: HCons[Field[F, VOut], T]): VOut = l.head.value
  }

  implicit def iterSelector[F <: String, H, T <: HList, VOut](
    implicit ts: SelectorAux[F, T, VOut])
    : SelectorAux[F, HCons[H, T], VOut]
  = new Selector[F, HCons[H, T]] {
      type V = VOut
      def get(l: HCons[H, T]): VOut = ts.get(l.tail)
  }

  implicit class HListOps[L <: HList](val l: L) extends AnyVal {
    def get[F <: String](implicit sel: Selector[F, L]) = sel.get(l)
  }
}
