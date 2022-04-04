package pl.muninn.markdown.basic.block

import pl.muninn.markdown.MarkdownFragment
import pl.muninn.markdown.MarkdownFragment.SpanFragment
import pl.muninn.markdown.MarkdownNode.Block
import pl.muninn.markdown.basic.block.Table.{TableAlignment, TableFragment}

case class Table(defaultAlignment: Option[TableAlignment]) extends TableFragment

object Table:

  trait TableFragment extends MarkdownFragment[Header | Row] with Block

  class Header extends ColumnFragment

  class Row extends ColumnFragment

  class Column(alignment: Option[TableAlignment]) extends SpanFragment

  trait ColumnFragment extends SpanFragment

  enum TableAlignment:
    case Left, Right, Center
