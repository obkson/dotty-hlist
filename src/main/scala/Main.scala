import dotty.records._, hlist._, hlist.ops._

object Main {

  def main(args: Array[String]): Unit = {

    val l = ("name"->>"Morty") :*: ("age"->>14) :*: HNil()

    println(l)

    val s = implicitly[Selector["age", HCons[Field["name",String], HCons[Field["age", Int],HNil]]]]
    val v = s.get(l)
    println(v)

    val a: Int = l.get["age"]
    val n: String = l.get["name"]

    println(a)
    println(n)

    val r = l.toRecord

    println(r)
    println(r.name)
    println(r.age)

  }
}
