package pl.muninn.markdown.common.basic.span

import pl.muninn.markdown.common.MarkdownNode.Span
import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownContext.AnyMarkdownFragment

case class Image(alt: Option[Text], link: Text, title: Option[Text]) extends Span

object Image:

  object Partial:
    def img(link: String)(using configuration: Configuration): Image = Image(alt = None, link = Text(link), title = None)

    def img(alt: String, link: String)(using configuration: Configuration): Image =
      Image(alt = Some(Text(alt)), link = Text(link), title = None)

    def img(alt: String, link: String, title: String)(using configuration: Configuration): Image =
      Image(alt = Some(Text(alt)), link = Text(link), title = Some(Text(title)))
  end Partial

  def img(link: String)(using md: AnyMarkdownFragment, configuration: Configuration) = md += Partial.img(link)

  def img(alt: String, link: String)(using md: AnyMarkdownFragment, configuration: Configuration) = md += Partial.img(alt, link)

  def img(alt: String, link: String, title: String)(using md: AnyMarkdownFragment, configuration: Configuration) =
    md += Partial.img(alt, link, title)

  def print(node: Image): String =
    s"![${node.alt.map(_.value).getOrElse("")}](${node.link.value}${node.title.map(title => s""" "${title.value}"""").getOrElse("")})"
