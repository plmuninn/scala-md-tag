package pl.muninn.scalamdtag.tags

import pl.muninn.scalamdtag.tags.BaseTags.{BreakLine, HorizontalLine}

object BaseTags {

  case class BreakLine() extends MarkdownTag {
    val isMultiline = false
    val canBeInSameLine = false
    val shouldEndWithNewLine = true
  }

  case class HorizontalLine() extends MarkdownTag {
    val isMultiline = false
    val canBeInSameLine = false
    val shouldEndWithNewLine = true
  }

}

trait BaseTagsOps {
  implicit val renderBreakLine: Renderer[BreakLine] = _ => "  "

  implicit val renderHorizontalLine: Renderer[HorizontalLine] = _ => "---"
}

object BaseTagsOps extends BaseTagsOps
