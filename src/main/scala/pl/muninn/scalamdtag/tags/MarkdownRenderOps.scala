package pl.muninn.scalamdtag.tags

import Renderer._
import pl.muninn.scalamdtag.tags.BaseTags.{BreakLine, HorizontalLine}

trait MarkdownRenderOps {
  implicit def renderMdTag[T <: MarkdownTag]: Renderer[T] = {
    case value: MarkdownText      => value.render(MarkdownText.renderText)
    case value: Italic            => value.render(Italic.renderItalic)
    case value: Bold              => value.render(Bold.renderBold)
    case value: Strikethrough     => value.render(Strikethrough.renderStrikethrough)
    case value: Code              => value.render(Code.renderCode)
    case value: Link              => value.render(Link.renderLink)
    case value: Image             => value.render(Image.renderImage)
    case value: UnsortedList      => value.render(UnsortedList.renderUnsortedList)
    case value: MarkdownList      => value.render(MarkdownList.renderList)
    case value: Heading           => value.render(Heading.renderHeading)
    case value: BreakLine         => value.render(BreakLine.renderBreakLine)
    case value: HorizontalLine    => value.render(HorizontalLine.renderHorizontalLine)
    case value: BlockQuotes       => value.render(BlockQuotes.renderBlockQuotes)
    case value: CodeBlock         => value.render(CodeBlock.renderCodeBlock)
    case value: Table             => value.render(Table.renderTable)
    case value: MarkdownParagraph => value.render(MarkdownParagraph.renderMarkdownParagraph)
    case value: MarkdownFragment  => value.render(MarkdownFragment.renderMarkdownFragment)
  }
}

object MarkdownRenderOps extends MarkdownRenderOps
