package pl.muninn.scalamdtag.tags

trait TextMarkdownTag extends MarkdownTag {
  val isMultiline = false
  val canBeInSameLine = true
  val shouldEndWithNewLine = false
}

case class MarkdownText(value: String) extends TextMarkdownTag

object MarkdownText {
  implicit val renderText: Renderer[MarkdownText] = text => text.value
}

case class Italic(value: TextMarkdownTag) extends TextMarkdownTag

object Italic {
  implicit val renderItalic: Renderer[Italic] = {
    case Italic(value) => s"_${value.rendered}_"
  }
}

case class Bold(value: TextMarkdownTag) extends TextMarkdownTag

object Bold {
  implicit val renderBold: Renderer[Bold] = {
    case Bold(value) => s"**${value.rendered}**"
  }
}

case class Strikethrough(value: TextMarkdownTag) extends TextMarkdownTag

object Strikethrough {
  implicit val renderStrikethrough: Renderer[Strikethrough] = strikethrough => s"~~${strikethrough.value.rendered}~~"
}

case class Code(value: TextMarkdownTag) extends TextMarkdownTag

object Code {
  implicit val renderCode: Renderer[Code] = code => s"`${code.value.rendered}`"
}

case class Link(alt: TextMarkdownTag, link: String, title: Option[String]) extends TextMarkdownTag

object Link {
  implicit val renderLink: Renderer[Link] = {
    case Link(alt, link, Some(title)) => s"""[${alt.rendered}]($link "$title")"""
    case Link(alt, link, None)        => s"[${alt.rendered}]($link)"
  }
}

case class Image(alt: Option[String], link: String, title: Option[String]) extends TextMarkdownTag

object Image {
  implicit val renderImage: Renderer[Image] = {
    case Image(alt, link, title) =>
      s"![${alt.getOrElse("")}]($link${title.map(value => s""" "$value"""").getOrElse("")})"
  }
}
