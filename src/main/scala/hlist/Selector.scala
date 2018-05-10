package hlist

import dotty.records.{Field}

trait Selector[F <: String, L <: HList] {
  type V <: Any
  def get(l: L): V
}

object Selector {

  type Aux[F <: String, L <: HList, VOut] = Selector[F, L] {
    type V = VOut
  }

  implicit def headSelector[F <: String, T <: HList, VOut]: Selector.Aux[F, HCons[Field[F, VOut], T], VOut]
  = new Selector[F, HCons[Field[F, VOut], T]] {
    type V = VOut
    def get(l: HCons[Field[F, VOut], T]): VOut = l.head.value
  }

  implicit def iterSelector[F <: String, H, T <: HList, VOut]
  (implicit ts: Selector.Aux[F, T, VOut])
  : Selector.Aux[F, HCons[H, T], VOut]
  = new Selector[F, HCons[H, T]] {
    type V = VOut
    def get(l: HCons[H, T]): VOut = ts.get(l.tail)
  }

}
