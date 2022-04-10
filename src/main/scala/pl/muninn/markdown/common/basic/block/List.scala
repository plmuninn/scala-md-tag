package pl.muninn.markdown.common.basic.block

import pl.muninn.markdown.common.MarkdownFragment
import pl.muninn.markdown.common.MarkdownFragment.{BlockFragment, BlockWithSpanFragment, SpanFragment}
import pl.muninn.markdown.common.MarkdownNode.{Block, Span}
import pl.muninn.markdown.common.basic.block.List.ListFragment
import pl.muninn.markdown.common.MarkdownContext.{SpanContextFn, StringConversion, createSpanPartialContext}
import pl.muninn.markdown.common.{Configuration, MarkdownFragment, MarkdownNode}

import scala.collection.immutable.ListMap
import scala.collection.mutable.ArrayBuffer

case class List(listType: List.ListType) extends ListFragment

object List:

  trait ListFragment extends MarkdownFragment[ListElement] with Block

  case class ListElement(level: ListLevel) extends SpanFragment

  case class ListLevel(value: Int) extends AnyVal

  enum ListType:
    case Ordered, Unordered

  type ListContextFn = (ListFragment, ListLevel) ?=> ListElement

  def createListPartialContext(list: List, init: ListContextFn): List =
    given fragment: ListFragment = list
    given level: ListLevel       = ListLevel(0)
    init(using fragment, level)
    list

  object Partial:
    def ul(init: ListContextFn)(using configuration: Configuration): List = createListPartialContext(List(ListType.Unordered), init)

    def ol(init: ListContextFn)(using configuration: Configuration): List = createListPartialContext(List(ListType.Ordered), init)

    def li(level: ListLevel)(init: SpanContextFn)(using list: ListFragment, configuration: Configuration): ListElement =
      createSpanPartialContext(ListElement(level), init)

  end Partial

  def ul(init: ListContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.ul(init)

  def ol(init: ListContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.ol(init)

  def add(node: ListElement)(using list: ListFragment, configuration: Configuration) = list.add(node)

  def add(nodes: ListElement*)(using list: ListFragment, configuration: Configuration) = list.addMany(nodes)

  def li(init: SpanContextFn)(using list: ListFragment, level: ListLevel, configuration: Configuration) = list += Partial.li(level)(init)

  def nested(init: ListContextFn)(using list: ListFragment, level: ListLevel, configuration: Configuration) =
    given fragment: ListFragment   = list
    given nextListLevel: ListLevel = level.copy(value = level.value + 1)
    init(using fragment, nextListLevel)

  // TODO handle generating string with number on start of list
  // (\d+)(\.)
  def print(list: List, printBodyF: MarkdownNode => String): String =
    val listType                                          = list.listType
    val listElements: ArrayBuffer[ListElement]            = list.values.collect({ case element: ListElement => element })
    val bodiesWithLevel: ArrayBuffer[(ListLevel, String)] = listElements.map(element => (element.level, printBodyF(element)))
    var index: Int                                        = 1
    var previousLevel: ListLevel                          = ListLevel(0)
    val results = bodiesWithLevel.map { case (level, body) =>
      if level.value != previousLevel.value then index = 1
      previousLevel = level

      val prefix = listType match {
        case ListType.Ordered   => "*"
        case ListType.Unordered => s"$index."
      }
      index += 1
      val indent = if level.value == 0 then "" else " " * 3 * level.value
      s"$indent$prefix $body"
    }

    results.mkString("\n")
  end print
