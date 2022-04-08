package pl.muninn.markdown

import pl.muninn.markdown.common.MarkdownContext.{BasicContextFn, StringConversion, magneticStringToTextConversion}
import pl.muninn.markdown.common.MarkdownFragment.BlockFragment
import pl.muninn.markdown.common.MarkdownFragment.MarkdownDocument
import pl.muninn.markdown.common.MarkdownNode.Span
import pl.muninn.markdown.common.basic.PartialBasicMarkdown
import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.basic.block.Paragraph
import pl.muninn.markdown.common.basic.span.Text
import pl.muninn.markdown.common.basic.{ContextBasicMarkdown, PartialBasicMarkdown}

trait Markdown:
  export pl.muninn.markdown.common.MarkdownStringContext.TextOps

  given Configuration = Configuration.DefaultConfiguration

  val partial: PartialBasicMarkdown = PartialBasicMarkdown

  def md(init: BasicContextFn): MarkdownDocument =
    given fragment: MarkdownDocument   = MarkdownDocument()
    given conversion: StringConversion = magneticStringToTextConversion(using fragment)
    init(using fragment)
    fragment

end Markdown

object Markdown extends Markdown with ContextBasicMarkdown with MarkdownGraphGeneration with MarkdownBasicPrinter
