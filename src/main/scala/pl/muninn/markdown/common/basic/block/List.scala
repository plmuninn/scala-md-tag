package pl.muninn.markdown.common.basic.block

import pl.muninn.markdown.common.MarkdownFragment
import pl.muninn.markdown.common.MarkdownFragment.{BlockFragment, BlockWithSpanFragment, SpanFragment}
import pl.muninn.markdown.common.MarkdownNode.{Block, Span}
import pl.muninn.markdown.common.basic.block.List.ListFragment
import pl.muninn.markdown.common.MarkdownContext.{SpanContextFn, StringConversion, createSpanPartialContext}
import pl.muninn.markdown.common.{Configuration, MarkdownFragment, MarkdownNode}

import scala.collection.mutable.ArrayBuffer

//TODO add levels
//TODO handle generating string with number on start of list
case class List(listType: List.ListType) extends ListFragment

object List:

  type ListContextFn = ListFragment ?=> ListElement

  def createListPartialContext(list: List, init: ListContextFn): List =
    given fragment: ListFragment = list
    init(using fragment)
    list

  trait ListFragment extends MarkdownFragment[ListElement] with Block

  class ListElement extends SpanFragment

  enum ListType:
    case Ordered, Unordered

  object Partial:
    def ul(init: ListContextFn)(using configuration: Configuration): List = createListPartialContext(List(ListType.Unordered), init)

    def ol(init: ListContextFn)(using configuration: Configuration): List = createListPartialContext(List(ListType.Ordered), init)

    def li(init: SpanContextFn)(using list: ListFragment, configuration: Configuration): ListElement = createSpanPartialContext(ListElement(), init)

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
