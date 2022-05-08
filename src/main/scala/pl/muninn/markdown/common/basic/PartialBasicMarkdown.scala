package pl.muninn.markdown.common.basic

import pl.muninn.markdown.common.basic.PartialBasicMarkdown
import pl.muninn.markdown.common.basic.block.*
import pl.muninn.markdown.common.basic.span.*

trait PartialBasicMarkdown:

  export Bold.Partial.*
  export Code.Partial.*
  export Emphasis.Partial.*
  export Image.Partial.*
  export Italic.Partial.*
  export Link.Partial.*
  export Strikethrough.Partial.*
  export Text.Partial.*
  export TextFragment.Partial.*
  export Blockquotes.Partial.*
  export BreakLine.Partial.*
  export CodeBlock.Partial.*
  export Heading.Partial.*
  export List.Partial.*
  export Paragraph.Partial.*
  export Table.Partial.*
  export TaskList.Partial.*

end PartialBasicMarkdown

object PartialBasicMarkdown extends PartialBasicMarkdown
