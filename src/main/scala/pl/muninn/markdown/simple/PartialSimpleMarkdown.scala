package pl.muninn.markdown.simple

import pl.muninn.markdown.common.basic.block.{Blockquotes, BreakLine, CodeBlock, Heading, HorizontalLine, List, Paragraph, Table}
import pl.muninn.markdown.common.basic.span.*

trait PartialSimpleMarkdown:
  export Bold.Partial.*
  export Code.Partial.*
  export Emphasis.Partial.*
  export Image.Partial.*
  export Italic.Partial.*
  export Link.Partial.*
  export Text.Partial.*
  export TextFragment.Partial.*
  export Blockquotes.Partial.*
  export BreakLine.Partial.*
  export CodeBlock.Partial.*
  export Heading.Partial.*
  export List.Partial.*
  export Paragraph.Partial.*
  export HorizontalLine.Partial.*

object PartialSimpleMarkdown extends PartialSimpleMarkdown
