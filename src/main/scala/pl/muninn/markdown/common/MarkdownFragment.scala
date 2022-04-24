package pl.muninn.markdown.common

import pl.muninn.markdown.common.MarkdownNode.{Block, Span}
import pl.muninn.markdown.common.{MarkdownFragment, MarkdownNode}

import scala.annotation.targetName
import scala.collection.mutable.ArrayBuffer

trait MarkdownFragment[T <: MarkdownNode]:

  lazy val values: ArrayBuffer[MarkdownNode] = new ArrayBuffer[MarkdownNode]

  @targetName("add_infix")
  infix def +=[A <: T](element: A)(using configuration: Configuration): A = add(element)

  @targetName("addMany_infix")
  infix def ++[A <: T](elements: A*)(using configuration: Configuration): Unit = addMany(elements)

  @targetName("combine_infix")
  infix def ++=(fragment: MarkdownFragment[T])(using configuration: Configuration): Unit = combine(fragment)

  def add[A <: T](element: A)(using configuration: Configuration): A =
    if configuration.safeInserting then MarkdownSafeInserting.safeInsert(element, values) else values += element
    element

  def addMany(elements: Iterable[T])(using configuration: Configuration): Unit =
    if configuration.safeInserting then MarkdownSafeInserting.safeInsertMany(elements, values) else values ++ elements

  def combine(fragment: MarkdownFragment[T])(using configuration: Configuration): MarkdownFragment[T] =
    if configuration.safeInserting then MarkdownSafeInserting.safeInsertMany(fragment.values, values) else values ++ fragment.values
    this

  def remove[A <: T](element: A): Unit =
    val index = values.indexOf(element, 0)
    if index != -1 then values.remove(index)

  def modify[A <: T](element: A)(f: A => A): Unit =
    val index = values.indexOf(element, 0)
    if index != -1 then values.update(index, f(element))

end MarkdownFragment

object MarkdownFragment:

  trait SpanFragment extends MarkdownFragment[Span] with Span

  trait BlockFragment extends MarkdownFragment[Span | Block] with Block

  trait BlockWithSpanFragment extends MarkdownFragment[Span] with Block

  class MarkdownDocument extends BlockFragment:
    def nodes: Iterable[MarkdownNode] = values

end MarkdownFragment
