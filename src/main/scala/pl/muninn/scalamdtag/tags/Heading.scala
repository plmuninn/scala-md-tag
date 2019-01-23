package pl.muninn.scalamdtag.tags

case class Heading(value: TextMarkdownTag, level: Int = 1) extends MarkdownTag {
  val isMultiline = false
  val canBeInSameLine = false
  val shouldEndWithNewLine = true
}

object Heading {
  implicit val renderHeading: Renderer[Heading] = {
    case Heading(value, level) => ("#" * level) + s" ${value.rendered}"
  }
}
