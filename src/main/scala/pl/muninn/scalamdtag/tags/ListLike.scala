package pl.muninn.scalamdtag.tags

import Renderer._

trait ListLike extends BlockMarkdownTag {
  def values: Iterable[MarkdownTag]
}

case class UnsortedList(values: Iterable[MarkdownTag]) extends ListLike

case class MarkdownList(values: Iterable[MarkdownTag]) extends ListLike

trait ListLikeOps {
  implicit val renderUnsortedList: Renderer[UnsortedList] = {
    case UnsortedList(values) =>
      values.foldLeft("") {
        case (acc, value: ListLike) => acc + "   " + value.rendered.lines.mkString("\n   ")
        case (acc, value)           => acc + s"* ${value.rendered}\n"
      }
  }

  implicit val renderList: Renderer[MarkdownList] = {
    case MarkdownList(values) =>
      values.zip(Stream.from(1)).foldLeft("") {
        case (acc, (value: ListLike, _)) => acc + "   " + value.rendered.lines.mkString("\n   ")
        case (acc, (value, index))       => acc + s"$index. ${value.rendered}\n"
      }
  }

  implicit def renderListLike[T <: ListLike]: Renderer[T] = {
    case value: UnsortedList => value.render
    case value: MarkdownList => value.render
  }
}

object ListLikeOps extends ListLikeOps
