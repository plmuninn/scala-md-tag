package pl.muninn.markdown.basic.span

import pl.muninn.markdown.MarkdownContext.AnyMarkdownFragment
import pl.muninn.markdown.MarkdownNode.Span

case class Image(alt: Option[Text], link: Text, title: Option[Text]) extends Span

object Image:
  def img(link: String)(using md: AnyMarkdownFragment) = md += Image(alt = None, link = Text(link), title = None)

  def img(alt: String, link: String)(using md: AnyMarkdownFragment) = md += Image(alt = Some(Text(alt)), link = Text(link), title = None)

  def img(alt: String, link: String, title: String)(using md: AnyMarkdownFragment) =
    md += Image(alt = Some(Text(alt)), link = Text(link), title = Some(Text(title)))

  def print(node: Image): String =
    s"![${node.alt.map(_.value).getOrElse("")}](${node.link.value}${node.title.map(title => s""" "${title.value}"""").getOrElse("")})"
