import hlist._, Field._, Selector._

object Main {

  def main(args: Array[String]): Unit = {

    val l = "foo"->>42 :*: "bar" ->> "baz" :*: HNil

    println(l)

    val s = implicitly[Selector["bar",HCons[Field["foo",Int], HCons[Field["bar", String],HNil]]]]
    val v = s.get(l)
    println(v)

    val v1: Int = l.get["foo"] + 1
    val v2: String = l.get["bar"]
    println(v1)
    println(v2)
  }
}
