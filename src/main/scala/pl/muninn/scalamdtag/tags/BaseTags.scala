package pl.muninn.scalamdtag.tags

object BaseTags {

  case class BreakLine() extends MarkdownTag {
    val isMultiline = false
    val canBeInSameLine = true
    val shouldEndWithNewLine = true
  }

  object BreakLine {
    implicit val renderBreakLine: Renderer[BreakLine] = _ => "  "
  }

  case class HorizontalLine() extends MarkdownTag {
    val isMultiline = false
    val canBeInSameLine = false
    val shouldEndWithNewLine = true
  }

  object HorizontalLine {
    implicit val renderHorizontalLine: Renderer[HorizontalLine] = _ => "---"
  }

}
