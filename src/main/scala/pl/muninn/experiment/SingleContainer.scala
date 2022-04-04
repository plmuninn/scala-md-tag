package pl.muninn.experiment

trait Container[T] {
  def add(value: T) = println(value)
}

trait SingleContainer extends Container[String]

trait ComposedContainer extends Container[List[String]]

object InsideContainer {

  def start(fn: (ComposedContainer) ?=> Unit) =
    val cont = new ComposedContainer {}
    fn(using cont)
    cont
  end start

  def single(using cont: SingleContainer | ComposedContainer)(value: String) =
    cont match
      case sc: SingleContainer   => sc.add(value)
      case cc: ComposedContainer => cc.add(List(value))

  def composed(using cont: ComposedContainer)(value: String) = cont.add(List(value))

}

import InsideContainer._

@main
def Main(args: String*): Unit =
  start {
    single("test")
    composed("test2")
  }
end Main
