package hlist

import dotty.records.Field
import records.Record

package object ops {

  import Selector._
  import RecordConverter._

  implicit class HListOps[L <: HList](val l: L) extends AnyVal {

    def get[F <: String](implicit sel: Selector[F, L]) = sel.get(l)

    def toRecord[Out <: Record](implicit hc: RecordConverter.Aux[L, Out]): Out = hc.toRecord(l)

  }
}
