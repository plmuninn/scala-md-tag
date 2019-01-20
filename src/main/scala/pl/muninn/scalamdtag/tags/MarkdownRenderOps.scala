package pl.muninn.scalamdtag.tags

import Renderer._
import pl.muninn.scalamdtag.tags.BaseTags.{BreakLine, HorizontalLine}

trait MarkdownRenderOps {

  import BaseTagsOps._
  import BlockMarkdownTagOps._
  import HeadingOps._
  import ListLikeOps._
  import TextMdTagOps._
  import TableOps._
  import MarkdownFragmentOps._
  import MarkdownParagraphOps._

  implicit def renderMdTag[T <: MarkdownTag]: Renderer[T] = {
    case value: TextMarkdownTag => value.render
    case value: ListLike => value.render
    case value: Heading => value.render
    case value: BreakLine => value.render
    case value: HorizontalLine => value.render
    case value: BlockQuotes => value.render
    case value: CodeBlock => value.render
    case value: Table => value.render
    case value: MarkdownParagraph => value.render
    case value: MarkdownFragment => value.render
  }
}

object MarkdownRenderOps extends MarkdownRenderOps
