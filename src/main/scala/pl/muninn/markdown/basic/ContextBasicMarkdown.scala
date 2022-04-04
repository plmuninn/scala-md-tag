package pl.muninn.markdown.basic

import pl.muninn.markdown.MarkdownContext.AnyMarkdownFragment
import pl.muninn.markdown.MarkdownFragment.{BlockFragment, SpanFragment}
import pl.muninn.markdown.MarkdownNode.{Block, Span}
import pl.muninn.markdown.basic.block.*
import pl.muninn.markdown.basic.span.*

//TODO finish quote
//TODO finish code block
//TODO finish table
trait ContextBasicMarkdown:

  export ContextBasicMarkdown.add
  export Text.text
  export BreakLine.br
  export HorizontalLine.hr
  export Heading.{h, h1, h2, h3, h4, h5, h6}
  export Paragraph.p
  export Bold.b
  export Code.code
  export Image.img
  export Italic.i
  export Link.a
  export Strikethrough.s
  export List.{ul, ol, li}

end ContextBasicMarkdown

object ContextBasicMarkdown:

  def add[T <: Span](node: T)(using md: AnyMarkdownFragment) = md += node

  def add[T <: Span](nodes: T*)(using md: AnyMarkdownFragment) = md.addMany(nodes)

  def add[T <: Block](node: T)(using md: BlockFragment) = md += node

  def add[T <: Block](nodes: T*)(using md: BlockFragment) = md.addMany(nodes)
