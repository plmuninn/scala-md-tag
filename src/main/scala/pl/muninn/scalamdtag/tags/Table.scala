package pl.muninn.scalamdtag.tags

import pl.muninn.scalamdtag.tags.TableAlignment._

case class Table(
  columns: Iterable[Any],
  rows: Iterable[Iterable[Any]],
  alignment: Option[Either[Alignment, List[Alignment]]]
) extends BlockMarkdownTag

object Table {

  implicit private class IterableImplicit[A](iterable: Iterable[A]) {

    def get(index: Int): Option[A] =
      iterable.zipWithIndex
        .find {
          case (_, i) => i == index
        }
        .collect {
          case (value, _) => value
        }
  }

  implicit val renderTable: Renderer[Table] = {
    case Table(columns, rows, alignment) =>
      def escape(value: String): String = value.replace("|", """\|""")

      def getAlignment(index: Int): Option[Alignment] = alignment.flatMap {
        result: Either[Alignment, List[Alignment]] =>
          result.fold(Option.apply, alignments => alignments.get(index))
      }

      def renderValue: Any => String = {
        case value: MarkdownTag => value.rendered
        case value: Option[_]   => value.map(renderValue).getOrElse("")
        case value              => value.toString
      }

      val minimumLengthForAlignment: Alignment => Int = {
        case TableAlignment.Left | TableAlignment.Right => 4
        case TableAlignment.Center                      => 5
      }

      val alignmentMinimum = alignment.fold(3)(_.fold(minimumLengthForAlignment, _.map(minimumLengthForAlignment).max))

      val preRenderedColumns: Iterable[String] = columns.map(renderValue).map(escape)
      val preRenderedRows: Iterable[Iterable[String]] = rows.map(_.map(renderValue).map(escape))

      val minimumLengths: Map[Int, Int] =
        preRenderedColumns.zipWithIndex.map {
          case (columnValue, index) =>
            val columnLength = columnValue.length
            val alignmentLength = getAlignment(index).fold(3)(minimumLengthForAlignment)
            val rowsMaxLength = preRenderedRows
              .foldLeft(Iterable.empty[Int]) {
                case (acc, values: Iterable[String]) => acc ++ values.get(index).map(_.length).toIterable
              }
              .max

            index -> Set(columnLength, alignmentLength, rowsMaxLength).max
        }.toMap

      def getMinimumLength(index: Int): Int = minimumLengths.get(index).filter(_ > 0).getOrElse(alignmentMinimum)

      def resize(value: String, index: Int): String =
        if (value.length == minimumLengths(index)) value
        else {
          value.concat(" " * (minimumLengths(index) - value.length))
        }

      def render: Iterable[String] => String = "| " + _.mkString(" | ") + " |"

      val resizedColumns: Iterable[String] = preRenderedColumns.zipWithIndex.map {
        case (value, index) => resize(value, index)
      }

      val resizedRows: Iterable[Iterable[String]] = preRenderedRows.map(
        _.zipWithIndex.map {
          case (value, index) => resize(value, index)
        }
      )

      val resizedAlignments: Iterable[String] =
        resizedColumns.zipWithIndex
          .collect {
            case (_, index) => index
          }
          .map { index =>
            getAlignment(index) match {
              case Some(TableAlignment.Left)   => ":" + "-" * (getMinimumLength(index) - 1) // :---
              case Some(TableAlignment.Center) => ":" + ("-" * (getMinimumLength(index) - 2)) + ":" // :---:
              case Some(TableAlignment.Right)  => "-" * (getMinimumLength(index) - 1) + ":" // ---:
              case _                           => "-" * getMinimumLength(index) // ----
            }
          }

      val renderedColumns: String = render(resizedColumns)
      val renderedAlignments: String = render(resizedAlignments)
      val renderedRows: String = resizedRows.map(render).mkString("\n")

      Iterable(renderedColumns, renderedAlignments, renderedRows).mkString("\n")
  }

}
