package pl.muninn.markdown

import pl.muninn.markdown.MarkdownContext.{BasicContextFn, StringConversion, magneticStringToTextConversion}
import pl.muninn.markdown.MarkdownFragment.BlockFragment
import pl.muninn.markdown.MarkdownFragment.MarkdownDocument
import pl.muninn.markdown.MarkdownNode.Span
import pl.muninn.markdown.basic.ContextBasicMarkdown
import pl.muninn.markdown.basic.block.Paragraph
import pl.muninn.markdown.basic.span.Text

//TODO design partial context
// To do partial I need to create function that does not return fragment but itself

trait Markdown:
  export MarkdownStringContext.TextOps

  given Configuration = Configuration.DefaultConfiguration

  def md(init: BasicContextFn): MarkdownDocument =
    given fragment: MarkdownDocument   = MarkdownDocument()
    given conversion: StringConversion = magneticStringToTextConversion(using fragment)
    init(using fragment)
    fragment

end Markdown

object Markdown extends Markdown with ContextBasicMarkdown with MarkdownGraphGeneration with MarkdownBasicPrinter
