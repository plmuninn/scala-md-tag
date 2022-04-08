package pl.muninn.markdown.basic.block

import pl.muninn.markdown.MarkdownContext.{SpanContextFn, StringConversion, magneticStringToTextConversion}
import pl.muninn.markdown.{Configuration, MarkdownFragment, MarkdownNode}
import pl.muninn.markdown.MarkdownFragment.{BlockFragment, BlockWithSpanFragment, SpanFragment}
import pl.muninn.markdown.MarkdownNode.{Block, Span}
import pl.muninn.markdown.basic.block.List.ListFragment

import scala.collection.mutable.ArrayBuffer

//TODO add levels
case class List(listType: List.ListType) extends ListFragment

object List:

  type ListContextFn = ListFragment ?=> ListElement

  def createListPartialContext(list: List, init: ListContextFn): List =
    given fragment: ListFragment = list
    init(using fragment)
    list

  def createListElementPartialContext(element: ListElement, init: SpanContextFn)(using configuration: Configuration): ListElement =
    given fragment: SpanFragment       = element
    given conversion: StringConversion = magneticStringToTextConversion(using fragment)
    init(using fragment, conversion)
    element

  trait ListFragment extends MarkdownFragment[ListElement] with Block

  class ListElement extends SpanFragment

  enum ListType:
    case Ordered, Unordered

  object Partial:
    def ul(init: ListContextFn)(using configuration: Configuration): List = createListPartialContext(List(ListType.Unordered), init)

    def ol(init: ListContextFn)(using configuration: Configuration): List = createListPartialContext(List(ListType.Ordered), init)

    def li(init: SpanContextFn)(using list: ListFragment, configuration: Configuration) = createListElementPartialContext(ListElement(), init)

  end Partial

  def ul(init: ListContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.ul(init)

  def ol(init: ListContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.ol(init)

  def add(node: ListElement)(using list: ListFragment, configuration: Configuration) = list.add(node)

  def add(nodes: ListElement*)(using list: ListFragment, configuration: Configuration) = list.addMany(nodes)

  def li(init: SpanContextFn)(using list: ListFragment, configuration: Configuration) = list += Partial.li(init)

  def print(list: List, printBodyF: MarkdownNode => String): String =
    val bodies = list.values.map(printBodyF)
    bodies.zipWithIndex
      .map { case (body, index) =>
        val prefix = list.listType match {
          case ListType.Ordered   => "*"
          case ListType.Unordered => s"$index"
        }

        s"$prefix $body"
      }
      .mkString("\n")
