package pl.muninn.markdown.simple

import pl.muninn.markdown.common.basic.ContextBasicMarkdown
import pl.muninn.markdown.common.basic.block.Heading.*
import pl.muninn.markdown.common.basic.block.List.*
import pl.muninn.markdown.common.basic.block.Table.*
import pl.muninn.markdown.common.basic.block.{Blockquotes, BreakLine, CodeBlock, Heading, HorizontalLine, List, Paragraph, Table}
import pl.muninn.markdown.common.basic.span.*

trait ContextSimpleMarkdown:
  export ContextBasicMarkdown.add
  export Text.text
  export TextFragment.div
  export Blockquotes.blockquotes
  export BreakLine.br
  export HorizontalLine.hr
  export Heading.{h, h1, h2, h3, h4, h5, h6}
  export Paragraph.p
  export Bold.b
  export Code.code
  export Emphasis.em
  export CodeBlock.codeBlock
  export Image.img
  export Italic.i
  export Link.a
  export List.{ul, ol, li, nested, add}
