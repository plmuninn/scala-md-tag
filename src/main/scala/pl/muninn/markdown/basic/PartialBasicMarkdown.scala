package pl.muninn.markdown.basic

import pl.muninn.markdown.basic.block.*
import pl.muninn.markdown.basic.span.*

trait PartialBasicMarkdown:

  export Bold.Partial.*
  export Code.Partial.*
  export Image.Partial.*
  export Italic.Partial.*
  export Link.Partial.*
  export Strikethrough.Partial.*
  export Text.Partial.*
  export Blockquotes.Partial.*
  export BreakLine.Partial.*
  export CodeBlock.Partial.*
  export Heading.Partial.*
  export List.Partial.*
  export Paragraph.Partial.*
//  export Table.Partial.*

end PartialBasicMarkdown

object PartialBasicMarkdown extends PartialBasicMarkdown
