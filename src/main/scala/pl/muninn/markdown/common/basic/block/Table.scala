package pl.muninn.markdown.common.basic.block

import pl.muninn.markdown.common.MarkdownContext.{SpanContextFn, createSpanPartialContext}
import pl.muninn.markdown.common.MarkdownFragment.{BlockFragment, SpanFragment}
import pl.muninn.markdown.common.MarkdownNode.Span
import pl.muninn.markdown.common.basic.block.Table.{TableAlignment, TableFragment}
import pl.muninn.markdown.common.{Configuration, MarkdownFragment, MarkdownNode}

import scala.collection.mutable.ArrayBuffer
import scala.util.Try

case class Table(defaultAlignment: Option[TableAlignment], strictPrinting: Boolean) extends TableFragment

object Table:
  extension (table: Table) def withAlignment(alignment: TableAlignment) = table.copy(defaultAlignment = Some(alignment))

  trait TableFragment extends MarkdownFragment[TableElement] with Span

  type TableElement = Headers | Row

  class Headers extends MarkdownFragment[Header] with Span

  case class Header(alignment: Option[TableAlignment]) extends SpanFragment

  class Row extends MarkdownFragment[Column] with Span

  class Column extends SpanFragment

  enum TableAlignment:
    case Left, Right, Center

  type TableContextFn  = TableFragment ?=> TableElement
  type HeaderContextFn = Headers ?=> SpanFragment
  type RowContextFn    = Row ?=> SpanFragment

  def createTablePartialContext(table: Table, init: TableContextFn): Table =
    given fragment: TableFragment = table
    init(using fragment)
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

    def table(defaultAlignment: Option[TableAlignment], init: TableContextFn)(using configuration: Configuration): Table =
      createTablePartialContext(Table(defaultAlignment, strictPrinting = configuration.tableStrictPrinting), init)

    def table(init: TableContextFn)(using configuration: Configuration): Table = table(None, init)

    def headers(init: HeaderContextFn): TableElement = createHeaderPartialContext(Headers(), init)

    def header(alignment: Option[TableAlignment], init: SpanContextFn)(using configuration: Configuration): Header =
      createSpanPartialContext(Header(alignment), init)

    def header(init: SpanContextFn)(using configuration: Configuration): Header = header(None, init)

    def row(init: RowContextFn): TableElement =
      createRowPartialContext(Row(), init)

    def col(init: SpanContextFn)(using configuration: Configuration): Column =
      createSpanPartialContext(Column(), init)

  end Partial

  def tableAligned(defaultAlignment: TableAlignment)(init: TableContextFn)(using md: BlockFragment, configuration: Configuration) =
    md += Partial.table(Some(defaultAlignment), init)

  def table(init: TableContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.table(init)

  def headers(init: HeaderContextFn)(using md: TableFragment, configuration: Configuration) =
    val previousHeaders = md.values.collectFirst({ case headers: Headers => headers })
    previousHeaders match {
      case Some(value) => init(using value)
      case None        => md += Partial.headers(init)
    }

  def row(init: RowContextFn)(using md: TableFragment, configuration: Configuration) = md += Partial.row(init)

  def header(alignment: TableAlignment)(init: SpanContextFn)(using md: Headers, configuration: Configuration) =
    md += Partial.header(Some(alignment), init)

  def col(init: SpanContextFn)(using md: Row | Headers, configuration: Configuration) =
    md match {
      case row: Row         => row += Partial.col(init)
      case headers: Headers => headers += Partial.header(None, init)
    }

  def add(node: Headers)(using md: TableFragment, configuration: Configuration) = md.add(node)

  def add(node: Header)(using md: Headers, configuration: Configuration) = md.add(node)

  def add(nodes: Header*)(using md: Headers, configuration: Configuration) = md.addMany(nodes)

  def add(node: Row)(using md: TableFragment, configuration: Configuration) = md.add(node)

  def add(nodes: Row*)(using md: TableFragment, configuration: Configuration) = md.addMany(nodes)

  def add(node: Column)(using md: Row, configuration: Configuration) = md.add(node)

  def add(nodes: Column*)(using md: Row, configuration: Configuration) = md.addMany(nodes)

  // TODO me
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
    val longestCell: Int                                  = (headersBody.map(_.length) ++ rowsColumnsBody.flatMap(_.map(_.length))).max
    val missingCellValue: String                          = " " * longestCell
    val missingAlignmentValue: String                     = "-" * longestCell

    if headersBody.size < longestRow then (headersBody.size until longestRow).foreach(_ => headersBody.addOne(missingCellValue))

    rowsColumnsBody.map { row =>
      val valuesToAdd: ArrayBuffer[String] =
        if row.size < longestRow then
          (row.size until longestRow).foldLeft(ArrayBuffer.empty) { case (acc, _) =>
            acc.addOne(missingCellValue)
          }
        else ArrayBuffer.empty
      row.addAll(valuesToAdd)
    }

    def resizeBody(value: String): String = if value.length == longestCell then value else value + " " * (longestCell - value.length)

    val resizedHeadersBody     = headersBody.map(resizeBody)
    val resizedRowsColumnsBody = rowsColumnsBody.map(_.map(resizeBody))

    def renderAlignment(alignment: Option[TableAlignment]): String =
      alignment match
        case Some(TableAlignment.Left)   => ":" + "-" * (longestCell - 1)         // :---
        case Some(TableAlignment.Center) => ":" + ("-" * (longestCell - 2)) + ":" // :---:
        case Some(TableAlignment.Right)  => "-" * (longestCell - 1) + ":"         // ---:
        case _                           => missingAlignmentValue                 // ----

    val alignments = headersColumns.map(_.alignment.orElse(node.defaultAlignment)).map(renderAlignment)
    if alignments.size < longestRow then (alignments.size until longestRow).foreach(_ => alignments.addOne(missingAlignmentValue))

    def renderRow(cells: Iterable[String]): String = "| " + cells.mkString(" | ") + " |"

    val renderedHeaders    = renderRow(resizedHeadersBody)
    val renderedAlignments = renderRow(alignments)
    val renderedRows       = resizedRowsColumnsBody.map(renderRow).mkString("\n")

    Array(renderedHeaders, renderedAlignments, renderedRows).mkString("\n")
  end print
