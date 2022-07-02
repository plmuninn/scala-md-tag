package pl.muninn.markdown.common.basic.block

import pl.muninn.markdown.common.MarkdownFragment
import pl.muninn.markdown.common.MarkdownFragment.{BlockFragment, BlockWithSpanFragment, SpanFragment}
import pl.muninn.markdown.common.MarkdownNode.{Block, Span}
import pl.muninn.markdown.common.basic.block.List.ListFragment
import pl.muninn.markdown.common.MarkdownContext.{SpanContextFn, StringConversion, createSpanPartialContext}
import pl.muninn.markdown.common.{Configuration, MarkdownFragment, MarkdownNode}

import scala.annotation.targetName
import scala.collection.immutable.ListMap
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

//TODO: list item should take Block elements - https://www.markdownguide.org/basic-syntax#paragraphs
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

  @targetName("add_listElement")
  def add(node: ListElement)(using list: ListFragment, configuration: Configuration) = list.add(node)

  @targetName("add_many_ListElements")
  def add(nodes: ListElement*)(using list: ListFragment, configuration: Configuration) = list.addMany(nodes)

  def li(init: SpanContextFn)(using list: ListFragment, level: ListLevel, configuration: Configuration) = list += Partial.li(level)(init)

  def nested(init: ListContextFn)(using list: ListFragment, level: ListLevel, configuration: Configuration) =
    given fragment: ListFragment   = list
    given nextListLevel: ListLevel = level.copy(value = level.value + 1)
    init(using fragment, nextListLevel)

  private val NUMBER_REGEX = raw"^(\d+)(\.)".r

  def print(list: List, printBodyF: MarkdownNode => String): String =
    val listType                                          = list.listType
    val listElements: ArrayBuffer[ListElement]            = list.values.collect({ case element: ListElement => element })
    val bodiesWithLevel: ArrayBuffer[(ListLevel, String)] = listElements.map(element => (element.level, printBodyF(element)))
    val indexLastLevel: mutable.Map[ListLevel, Int]       = mutable.Map(ListLevel(0) -> 1)
    val results = bodiesWithLevel.map { case (level, body) =>
      val index = indexLastLevel.get(level) match
        case Some(value) => value
        case None =>
          indexLastLevel += (level -> 1)
          indexLastLevel(level)

      val prefix = listType match {
        case ListType.Ordered   => "*"
        case ListType.Unordered => s"$index."
      }
      indexLastLevel(level) = index + 1
      val indent   = if level.value == 0 then "" else " " * 3 * level.value
      val safeBody = body.replaceFirst(NUMBER_REGEX.toString(), """$1\\.""")
      s"$indent$prefix $safeBody"
    }

    results.mkString("\n")
  end print
