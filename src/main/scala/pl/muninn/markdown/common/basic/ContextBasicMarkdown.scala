package pl.muninn.markdown.common.basic

import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownFragment.{BlockFragment, SpanFragment}
import pl.muninn.markdown.common.MarkdownNode.{Block, Span}
import pl.muninn.markdown.common.basic.ContextBasicMarkdown
import pl.muninn.markdown.common.basic.block.*
import pl.muninn.markdown.common.basic.span.*
import pl.muninn.markdown.common.MarkdownContext.AnyMarkdownFragment

trait ContextBasicMarkdown:

  export ContextBasicMarkdown.add
  export Text.text
  export Blockquotes.blockquotes
  export BreakLine.br
  export HorizontalLine.hr
  export Heading.{h, h1, h2, h3, h4, h5, h6}
  export Paragraph.p
  export Bold.b
  export Code.code
  export CodeBlock.codeBlock
  export Image.img
  export Italic.i
  export Link.a
  export Strikethrough.s
  export List.{ul, ol, li, add}
  export Table.{table, tableAligned, headers, header, row, col, add}

end ContextBasicMarkdown

object ContextBasicMarkdown:

  def add[T <: Span](node: T)(using md: AnyMarkdownFragment, configuration: Configuration) = md += node

  def add[T <: Span](nodes: T*)(using md: AnyMarkdownFragment, configuration: Configuration) = md.addMany(nodes)

  def add[T <: Block](node: T)(using md: BlockFragment, configuration: Configuration) = md += node

  def add[T <: Block](nodes: T*)(using md: BlockFragment, configuration: Configuration) = md.addMany(nodes)
