package pl.muninn.scalamdtag.tags

trait BlockMarkdownTag extends MarkdownTag {
  val isMultiline = true
  val canBeInSameLine = false
  val shouldEndWithNewLine = true
}
