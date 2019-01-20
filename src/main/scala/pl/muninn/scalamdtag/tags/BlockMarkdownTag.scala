package pl.muninn.scalamdtag.tags

trait BlockMarkdownTag extends MarkdownTag {
  val isMultiline = true
  val canBeInSameLine = false
  val shouldEndWithNewLine = true
}


case class BlockQuotes(value: MarkdownTag) extends MarkdownTag {
  val isMultiline = value.rendered.lines.length > 1
  val canBeInSameLine = isMultiline
  val shouldEndWithNewLine = isMultiline
}

case class CodeBlock(language: Option[String], code: String) extends BlockMarkdownTag

trait BlockMarkdownTagOps {

  implicit val renderCodeBlock: Renderer[CodeBlock] = {
    case CodeBlock(language, code) => s"""```${language.map(_ + "\n").getOrElse("\n")}\n$code\n\n```"""
  }

  implicit val renderBlockQuotes: Renderer[BlockQuotes] = {
    case BlockQuotes(value) => value.rendered.lines.map(line => s"> $line").mkString("\n")
  }
}

object BlockMarkdownTagOps extends BlockMarkdownTagOps