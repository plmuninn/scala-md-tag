package pl.muninn.markdown

import pl.muninn.markdown.common.MarkdownContext.{BasicContextFn, StringConversion, magneticStringToTextConversion}
import pl.muninn.markdown.common.MarkdownFragment.BlockFragment
import pl.muninn.markdown.common.MarkdownFragment.MarkdownDocument
import pl.muninn.markdown.common.MarkdownNode.Span
import pl.muninn.markdown.common.basic.PartialBasicMarkdown
import pl.muninn.markdown.common.{Configuration, MarkdownNode}
import pl.muninn.markdown.common.basic.block.Paragraph
import pl.muninn.markdown.common.basic.span.Text
import pl.muninn.markdown.common.basic.{ContextBasicMarkdown, PartialBasicMarkdown}
import pl.muninn.markdown.common.print.{BasicPrinter, GenerateGraph, GenerateMarkdown}

trait Markdown extends GenerateGraph with GenerateMarkdown:

  export pl.muninn.markdown.common.MarkdownStringContext.TextOps
  export Configuration.*

  given Configuration = Configuration.DefaultConfiguration()

  override def generate[T <: MarkdownNode](markdown: T): Either[Throwable, String] = BasicPrinter.generate(markdown)

  final def md(init: BasicContextFn): MarkdownDocument =
    given fragment: MarkdownDocument   = MarkdownDocument()
    given conversion: StringConversion = magneticStringToTextConversion(using fragment)
    init(using fragment)
    fragment

end Markdown

// Implementation of https://www.markdownguide.org/extended-syntax/#overview
// TODO: https://www.markdownguide.org/extended-syntax/#task-lists
// Without:
// * https://www.markdownguide.org/extended-syntax/#definition-lists
// * https://www.markdownguide.org/extended-syntax/#footnotes
// * https://www.markdownguide.org/extended-syntax/#subscript
// * https://www.markdownguide.org/extended-syntax/#superscript
// Those components are not supported because they are not so popular
object Markdown extends Markdown with ContextBasicMarkdown:
  val partial: PartialBasicMarkdown = PartialBasicMarkdown
