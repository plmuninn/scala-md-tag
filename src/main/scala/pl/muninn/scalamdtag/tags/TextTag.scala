package pl.muninn.scalamdtag.tags

import Renderer._

trait TextMarkdownTag extends MarkdownTag {
  val isMultiline = false
  val canBeInSameLine = true
  val shouldEndWithNewLine = false
}

case class MarkdownText(value: String) extends TextMarkdownTag

case class Italic(value: TextMarkdownTag) extends TextMarkdownTag

case class Bold(value: TextMarkdownTag) extends TextMarkdownTag

case class Strikethrough(value: TextMarkdownTag) extends TextMarkdownTag

case class Code(value: TextMarkdownTag) extends TextMarkdownTag

case class Link(alt: TextMarkdownTag, link: String, title: Option[String]) extends TextMarkdownTag

case class Image(alt: Option[String], link: String, title: Option[String]) extends TextMarkdownTag

trait TextMdTagOps {

  implicit val renderText: Renderer[MarkdownText] = text => text.value

  implicit val renderItalic: Renderer[Italic] = {
    case Italic(value) => s"_${value.rendered}_"
  }

  implicit val renderBold: Renderer[Bold] = {
    case Bold(value) => s"**${value.rendered}**"
  }

  implicit val renderStrikethrough: Renderer[Strikethrough] = strikethrough => s"~~${strikethrough.value.rendered}~~"

  implicit val renderCode: Renderer[Code] = code => s"`${code.value.rendered}`"

  implicit val renderLink: Renderer[Link] = {
    case Link(alt, link, title) => s"[${alt.rendered}]($link${title.map(value => s""" "$value"""").getOrElse("")})"
  }

  implicit val renderImage: Renderer[Image] = {
    case Image(alt, link, title) =>
      s"![${alt.getOrElse("")}]($link${title.map(value => s""" "$value"""").getOrElse("")})"
  }

  implicit def renderTextMdTag[T <: TextMarkdownTag]: Renderer[T] = {
    case value: MarkdownText  => value.render
    case value: Italic        => value.render
    case value: Bold          => value.render
    case value: Strikethrough => value.render
    case value: Code          => value.render
    case value: Link          => value.render
    case value: Image         => value.render
  }
}

object TextMdTagOps extends TextMdTagOps
