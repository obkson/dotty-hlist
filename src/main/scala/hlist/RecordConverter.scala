package hlist

import dotty.records._, records._, records.ops._

trait RecordConverter[L <: HList] {
  type Out <: Record
  def toRecord(l: L): Out
}

object RecordConverter {

  type Aux[L <: HList, Out0 <: Record] = RecordConverter[L] {
    type Out = Out0
  }

  // This type lambda is needed to prevent the intersection types from messing up Dotty's implicit search algorithm
  type Merge = [S <: Selectable, T <: Selectable] => S & T

  implicit object HNilConverter extends RecordConverter[HNil] {
    type Out = Record
    def toRecord(l: HNil) = Record()
  }

  implicit def hconsConverter[L <: String, V, T <: HList, HS <: Selectable, TS <: Record]
  (implicit ft: FieldTyper.Aux[L, V, HS], hc: RecordConverter.Aux[T, TS], ev: Extensible[TS, L, V])
  : RecordConverter[HCons[Field[L, V], T]] { type Out = Merge[HS, TS] }
  = new RecordConverter[HCons[Field[L, V], T]] {
    type Out = Merge[HS, TS]
    def toRecord(l: HCons[Field[L, V], T]) = hc.toRecord(l.tail) + (l.head)
  }
}

