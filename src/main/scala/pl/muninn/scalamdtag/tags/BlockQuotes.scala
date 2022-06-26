package pl.muninn.scalamdtag.tags

case class BlockQuotes(value: MarkdownTag) extends MarkdownTag {
  val isMultiline = value.rendered.lines.toArray.length > 1
  val canBeInSameLine = isMultiline
  val shouldEndWithNewLine = isMultiline
}

object BlockQuotes {
  implicit val renderBlockQuotes: Renderer[BlockQuotes] = {
    case BlockQuotes(value) => value.rendered.lines.map(line => s"> $line").toArray.mkString("\n")
  }
}
