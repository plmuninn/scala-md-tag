package pl.muninn

import pl.muninn.scalamdtag.tags.BaseTags.{BreakLine, HorizontalLine}
import pl.muninn.scalamdtag.tags._
import pl.muninn.scalamdtag.tags.github.{Task, TaskList}

import scala.language.implicitConversions

package object scalamdtag {

  val hr: MarkdownTag = HorizontalLine()

  val br: MarkdownTag = BreakLine()

  implicit def text(value: String): TextMarkdownTag = MarkdownText(value)

  def h(value: TextMarkdownTag) = Heading(value)

  def h1(value: TextMarkdownTag): MarkdownTag = h(value)

  def h2(value: TextMarkdownTag) = Heading(value, 2)

  def h3(value: TextMarkdownTag) = Heading(value, 3)

  def h4(value: TextMarkdownTag) = Heading(value, 4)

  def h5(value: TextMarkdownTag) = Heading(value, 5)

  def h6(value: TextMarkdownTag) = Heading(value, 6)

  def i(value: TextMarkdownTag) = Italic(value)

  def b(value: TextMarkdownTag) = Bold(value)

  def code(value: TextMarkdownTag) = Code(value)

  def frag(values: Iterable[MarkdownTag]) = MarkdownFragment(values)

  def frag(values: MarkdownTag*): MarkdownTag = frag(values)

  def p(values: Iterable[MarkdownTag]) = MarkdownParagraph(values)

  def p(values: MarkdownTag*): MarkdownTag = p(values)

  def markdown(values: Iterable[MarkdownTag]) = MarkdownFragment(values)

  def markdown(values: MarkdownTag*): MarkdownTag = markdown(values)

  def img(link: String): MarkdownTag = Image(alt = None, link = link, title = None)

  def img(alt: String, link: String) = Image(alt = Some(alt), link = link, title = None)

  def img(alt: String, link: String, title: String) = Image(alt = Some(alt), link = link, title = Some(title))

  def a(alt: TextMarkdownTag, link: String) = Link(alt = alt, link = link, title = None)

  def a(alt: TextMarkdownTag, link: String, title: String) = Link(alt = alt, link = link, title = Some(title))

  def codeBlock(code: String) = CodeBlock(None, code)

  def codeBlock(language: String, code: String) = CodeBlock(Some(language), code)

  def ul(values: Iterable[MarkdownTag]): MarkdownTag = UnsortedList(values)

  def ul(values: MarkdownTag*): MarkdownTag = ul(values)

  def ol(values: Iterable[MarkdownTag]): MarkdownTag = MarkdownList(values)

  def ol(values: MarkdownTag*): MarkdownTag = ol(values)

  def table(values: Iterable[MarkdownTag], rows: Iterable[Iterable[MarkdownTag]]) =
    Table(values, rows, None)

  def table(values: Iterable[MarkdownTag], rows: Iterable[Iterable[MarkdownTag]], alignment: TableAlignment.Alignment) =
    Table(values, rows, Some(Left(alignment)))

  def table(
    values: Iterable[MarkdownTag],
    rows: Iterable[Iterable[MarkdownTag]],
    alignments: Iterable[TableAlignment.Alignment]
  ) = Table(values, rows, Some(Right(alignments.toList)))

  def table[A <: Product, B <: Product](values: A, rows: Iterable[B]) =
    Table(columns = values.productIterator.toList, rows = rows.map(_.productIterator.toList), alignment = None)

  def table[A <: Product, B <: Product](values: A, rows: Iterable[B], alignment: TableAlignment.Alignment) =
    Table(
      columns = values.productIterator.toList,
      rows = rows.map(_.productIterator.toList),
      alignment = Some(Left(alignment))
    )

  def table[A <: Product, B <: Product](values: A, rows: Iterable[B], alignments: Iterable[TableAlignment.Alignment]) =
    Table(
      columns = values.productIterator.toList,
      rows = rows.map(_.productIterator.toList),
      alignment = Some(Right(alignments.toList))
    )

  def taskList(values: Iterable[Task]) = TaskList(values)

  def taskList(values: Task*): MarkdownTag = taskList(values)

  def task(value: TextMarkdownTag) = Task(selected = false, value)

  def task(isSelected: Boolean, value: TextMarkdownTag) = Task(isSelected, value)

  def task(value: TextMarkdownTag, isSelected: Boolean) = Task(isSelected, value)
}
