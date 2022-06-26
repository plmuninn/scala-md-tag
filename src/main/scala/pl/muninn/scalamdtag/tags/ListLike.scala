package pl.muninn.scalamdtag.tags

import scala.annotation.nowarn

trait ListLike extends BlockMarkdownTag {
  def values: Iterable[MarkdownTag]
}

case class UnsortedList(values: Iterable[MarkdownTag]) extends ListLike

object UnsortedList {
  implicit val renderUnsortedList: Renderer[UnsortedList] = {
    case UnsortedList(values) =>
      values.foldLeft("") {
        case (acc, value: ListLike) => acc + "   " + value.rendered.lines.toArray.mkString("\n   ")
        case (acc, value)           => acc + s"* ${value.rendered}\n"
      }
  }
}

case class MarkdownList(values: Iterable[MarkdownTag]) extends ListLike

object MarkdownList {

  @nowarn
  implicit val renderList: Renderer[MarkdownList] = {
    case MarkdownList(values) =>
      values.zip(Stream.from(1)).foldLeft("") {
        case (acc, (value: ListLike, _)) => acc + "   " + value.rendered.lines.toArray.mkString("\n   ")
        case (acc, (value, index))       => acc + s"$index. ${value.rendered}\n"
      }
  }
}
