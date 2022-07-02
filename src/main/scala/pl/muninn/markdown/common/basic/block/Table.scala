package pl.muninn.markdown.common.basic.block

import pl.muninn.markdown.common.MarkdownContext.{SpanContextFn, createSpanPartialContext}
import pl.muninn.markdown.common.MarkdownFragment.{BlockFragment, SpanFragment}
import pl.muninn.markdown.common.MarkdownNode.Span
import pl.muninn.markdown.common.basic.block.Table.{ColumnAlignment, TableFragment}
import pl.muninn.markdown.common.{Configuration, MarkdownFragment, MarkdownNode}

import scala.annotation.targetName
import scala.collection.mutable.ArrayBuffer
import scala.util.Try

case class Table(var defaultAlignment: Option[ColumnAlignment], strictPrinting: Boolean) extends TableFragment

object Table:
  extension (table: Table) def withAlignment(alignment: ColumnAlignment) = table.copy(defaultAlignment = Some(alignment))

  trait TableFragment extends MarkdownFragment[TableElement] with Span

  type TableElement = Headers | Row

  class Headers extends MarkdownFragment[Header] with Span

  case class Header(var alignment: Option[ColumnAlignment]) extends SpanFragment

  class Row extends MarkdownFragment[Column] with Span

  class Column extends SpanFragment

  enum ColumnAlignment:
    case Left, Right, Center

  type TableContextFn  = (TableFragment, Table) ?=> TableElement
  type HeaderContextFn = Headers ?=> SpanFragment
  type RowContextFn    = Row ?=> SpanFragment

  def createTablePartialContext(table: Table, init: TableContextFn): Table =
    given fragment: TableFragment = table
    init(using fragment, table)
    table

  def createHeaderPartialContext(element: Headers, init: HeaderContextFn): TableElement =
    given fragment: Headers = element
    init(using fragment)
    element

  def createRowPartialContext(element: Row, init: RowContextFn): TableElement =
    given fragment: Row = element
    init(using fragment)
    element

  object Partial:

    def table(defaultAlignment: Option[ColumnAlignment], init: TableContextFn)(using configuration: Configuration): Table =
      createTablePartialContext(Table(defaultAlignment, strictPrinting = configuration.tableStrictPrinting), init)

    def table(init: TableContextFn)(using configuration: Configuration): Table = table(None, init)

    def headers(init: HeaderContextFn): TableElement = createHeaderPartialContext(Headers(), init)

    def header(alignment: Option[ColumnAlignment], init: SpanContextFn)(using configuration: Configuration): Header =
      createSpanPartialContext(Header(alignment), init)

    def header(init: SpanContextFn)(using configuration: Configuration): Header = header(None, init)

    def row(init: RowContextFn): TableElement =
      createRowPartialContext(Row(), init)

    def col(init: SpanContextFn)(using configuration: Configuration): Column =
      createSpanPartialContext(Column(), init)

  end Partial

  def tableAligned(defaultAlignment: ColumnAlignment)(init: TableContextFn)(using md: BlockFragment, configuration: Configuration) =
    md += Partial.table(Some(defaultAlignment), init)

  def table(init: TableContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.table(init)

  def setDefaultAlignment(alignment: ColumnAlignment)(using table: Table) =
    table.defaultAlignment = Some(alignment)

  def headers(init: HeaderContextFn)(using md: TableFragment, configuration: Configuration) =
    val previousHeaders = md.values.collectFirst({ case headers: Headers => headers })
    previousHeaders match {
      case Some(value) => init(using value)
      case None        => md += Partial.headers(init)
    }

  def setAlignment(alignment: ColumnAlignment)(using header: Header) = header.alignment = Some(alignment)

  def row(init: RowContextFn)(using md: TableFragment, configuration: Configuration) = md += Partial.row(init)

  def header(alignment: ColumnAlignment)(init: SpanContextFn)(using md: Headers, configuration: Configuration) =
    md += Partial.header(Some(alignment), init)

  def col(init: SpanContextFn)(using md: Row | Headers, configuration: Configuration) =
    md match {
      case row: Row         => row += Partial.col(init)
      case headers: Headers => headers += Partial.header(None, init)
    }

  @targetName("add_headers")
  def add(node: Headers)(using md: TableFragment, configuration: Configuration) = md.add(node)

  @targetName("add_header")
  def add(node: Header)(using md: Headers, configuration: Configuration) = md.add(node)

  @targetName("add_many_headers")
  def add(nodes: Header*)(using md: Headers, configuration: Configuration) = md.addMany(nodes)

  @targetName("add_row")
  def add(node: Row)(using md: TableFragment, configuration: Configuration) = md.add(node)

  @targetName("add_many_rows")
  def add(nodes: Row*)(using md: TableFragment, configuration: Configuration) = md.addMany(nodes)

  @targetName("add_tableElement")
  def add(node: TableElement)(using md: TableFragment, configuration: Configuration) = md.add(node)

  @targetName("add_many_tableElement")
  def add(node: TableElement*)(using md: TableFragment, configuration: Configuration) = md.addMany(node)

  @targetName("add_column")
  def add(node: Column)(using md: Row, configuration: Configuration) = md.add(node)

  @targetName("add_many_columns")
  def add(nodes: Column*)(using md: Row, configuration: Configuration) = md.addMany(nodes)

  def print(node: Table, printBodyF: MarkdownNode => String): String =
    val headers: Option[Headers]                      = node.values.collectFirst { case headers: Headers => headers }
    val headersColumns: ArrayBuffer[Header]           = headers.map(_.values.collect({ case header: Header => header })).getOrElse(ArrayBuffer.empty)
    val rows                                          = node.values.collect({ case row: Row => row })
    val rowsColumns: ArrayBuffer[ArrayBuffer[Column]] = rows.map(_.values.collect({ case column: Column => column }))
    val longestColumnRow: Int                         = Try(rowsColumns.map(_.size).max).getOrElse(0)
    val longestRow: Int                               = Array(longestColumnRow, headersColumns.size).max
    val rowSizesEqual: Boolean                        = rowsColumns.map(_.size).distinct.size == 1

    if node.strictPrinting &&
      (headersColumns.isEmpty ||
        headersColumns.size != longestColumnRow ||
        rowsColumns.isEmpty ||
        !rowSizesEqual)
    then throw new RuntimeException("Table is not meeting criteria of strict printing")

    val headersBody: ArrayBuffer[String]                  = headersColumns.map(printBodyF)
    val rowsColumnsBody: ArrayBuffer[ArrayBuffer[String]] = rowsColumns.map(columns => columns.map(column => printBodyF(column)))
    // Alignments need's to have minimal values - otherwise some renderings are complaining and not rendering table
    val minSizeOfAlignments: Map[Int, Int] =
      headersColumns
        .map(_.alignment)
        .map {
          case Some(_) => 4
          case None    => 0
        }
        .zipWithIndex
        .toMap
    val rowLongestRowCells: Map[Int, Int] =
      rowsColumnsBody
        .map(_.zipWithIndex)              // Add each column id
        .addOne(headersBody.zipWithIndex) // Merge rows with headers
        .flatten
        .groupMap({ case (_, key) => key })({ case (values, _) => values.length })
        .map({ case (index, values) => index -> values.addOne(minSizeOfAlignments.getOrElse(index, 0)) }) // Merge with min alignment sizes
        .view
        .mapValues(_.max)
        .toMap
    def getColumnLongestCell(column: Int): Int = rowLongestRowCells(column)
    def missingCellValue(column: Int): String  = " " * getColumnLongestCell(column)

    if headersBody.size < longestRow then (headersBody.size until longestRow).foreach(index => headersBody.addOne(missingCellValue(index)))

    rowsColumnsBody.map { row =>
      val valuesToAdd: ArrayBuffer[String] =
        if row.size < longestRow then
          (row.size until longestRow).foldLeft(ArrayBuffer.empty) { case (acc, index) =>
            acc.addOne(missingCellValue(index))
          }
        else ArrayBuffer.empty
      row.addAll(valuesToAdd)
    }

    // Alignments are rendered without spaces that means they are originally missing 2 of length
    def renderAlignment(alignment: Option[ColumnAlignment], index: Int): String =
      val longestCell = rowLongestRowCells(index) + 2
      alignment match
        case Some(ColumnAlignment.Left)   => ":" + "-" * (longestCell - 1)         // :---
        case Some(ColumnAlignment.Center) => ":" + ("-" * (longestCell - 2)) + ":" // :---:
        case Some(ColumnAlignment.Right)  => "-" * (longestCell - 1) + ":"         // ---:
        case _                            => "-" * longestCell                     // ----

    val alignments = headersColumns.map(_.alignment.orElse(node.defaultAlignment))
    if alignments.size < longestRow then (alignments.size until longestRow).foreach(_ => alignments.addOne(node.defaultAlignment))
    val columnAlignments: Map[Int, Option[ColumnAlignment]] = alignments.zipWithIndex.map(_.swap).toMap
    val alignmentsBodies                                    = alignments.zipWithIndex.map(renderAlignment)

    def resizeAndAlignBody(value: String, index: Int): String =
      val alignment = columnAlignments.get(index).flatten
      if value.length == rowLongestRowCells(index) then value
      else
        val missingLength = rowLongestRowCells(index) - value.length
        alignment match
          case Some(ColumnAlignment.Right) => (" " * missingLength) + value
          case Some(ColumnAlignment.Center) =>
            val leftSpace  = Math.floor(missingLength / 2).toInt
            val rightSpace = missingLength - leftSpace
            (" " * leftSpace) + value + (" " * rightSpace)
          case Some(ColumnAlignment.Left) => value + (" " * missingLength)
          case None                       => value + (" " * missingLength)

    val resizedHeadersBody: ArrayBuffer[String]                  = headersBody.zipWithIndex.map(resizeAndAlignBody)
    val resizedRowsColumnsBody: ArrayBuffer[ArrayBuffer[String]] = rowsColumnsBody.map(_.zipWithIndex.map(resizeAndAlignBody))

    def renderRow(cells: Iterable[String]): String        = "| " + cells.mkString(" | ") + " |"
    def renderAlignments(cells: Iterable[String]): String = "|" + cells.mkString("|") + "|"

    val renderedHeaders    = renderRow(resizedHeadersBody)
    val renderedAlignments = renderAlignments(alignmentsBodies)
    val renderedRows       = resizedRowsColumnsBody.map(renderRow).mkString("\n")

    Array(renderedHeaders, renderedAlignments, renderedRows).mkString("\n")
  end print
