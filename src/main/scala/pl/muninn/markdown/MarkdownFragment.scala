package pl.muninn.markdown

import pl.muninn.markdown.MarkdownNode.{Block, Span}

import scala.collection.mutable.ArrayBuffer

//TODO handle configuration
trait MarkdownFragment[T <: MarkdownNode]:

  lazy val values: ArrayBuffer[MarkdownNode] = new ArrayBuffer[MarkdownNode]

  infix def +=[A <: T](element: A): A = add(element)

  infix def ++[A <: T](elements: A*): Unit = addMany(elements)

  infix def ++=(fragment: MarkdownFragment[T]): Unit = combine(fragment)

  def add[A <: T](element: A): A =
    values += element
    element

  def addMany(elements: Iterable[T]): Unit =
    values ++ elements

  def combine(fragment: MarkdownFragment[T]): MarkdownFragment[T] =
    values ++ fragment.values
    this

  def remove[A <: T](element: A): Unit =
    val index = values.indexOf(element)
    if index != -1 then values.remove(index)

end MarkdownFragment

object MarkdownFragment:

  trait SpanFragment extends MarkdownFragment[Span] with Span

  trait BlockFragment extends MarkdownFragment[Span | Block] with Block

  trait BlockWithSpanFragment extends MarkdownFragment[Span] with Block

  class MarkdownDocument extends BlockFragment:
    def nodes: Iterable[MarkdownNode] = values

end MarkdownFragment
