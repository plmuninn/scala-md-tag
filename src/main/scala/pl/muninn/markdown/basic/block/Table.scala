package pl.muninn.markdown.basic.block

import com.sun.tools.javac.api.DiagnosticFormatter.Configuration
import pl.muninn.markdown.{MarkdownFragment, MarkdownNode}
import pl.muninn.markdown.MarkdownFragment.SpanFragment
import pl.muninn.markdown.MarkdownNode.Block
import pl.muninn.markdown.basic.block.Table.{TableAlignment, TableFragment}

//TODO finish me
case class Table(defaultAlignment: Option[TableAlignment]) extends TableFragment

object Table:

  trait TableFragment extends MarkdownFragment[Header | Row] with Block

  class Header extends ColumnFragment

  class Row extends ColumnFragment

  class Column(alignment: Option[TableAlignment]) extends SpanFragment

  trait ColumnFragment extends SpanFragment

//  def table(defaultAlignment:TableAlignment)
//
//  def table()
//
//  def header()
//
//  def row()
//
//  def col()
//
//  def add()

  enum TableAlignment:
    case Left, Right, Center

  def print(node: Table, printBodyF: MarkdownNode => String): String = ???
