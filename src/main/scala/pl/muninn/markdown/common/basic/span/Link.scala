package pl.muninn.markdown.common.basic.span

import pl.muninn.markdown.common.MarkdownNode.Span
import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownContext.AnyMarkdownFragment

case class Link(title: Text, link: Text, alt: Option[Text]) extends Span

object Link:

  object Partial:
    def a(title: String, link: String)(using configuration: Configuration): Link = Link(title = Text(title), link = Text(link), alt = None)

    def a(title: String, link: String, alt: String)(using configuration: Configuration): Link =
      Link(title = Text(title), link = Text(link), alt = Some(Text(alt)))
  end Partial

  def a(title: String, link: String)(using md: AnyMarkdownFragment, configuration: Configuration) = md += Partial.a(title, link)

  def a(title: String, link: String, alt: String)(using md: AnyMarkdownFragment, configuration: Configuration) =
    md += Partial.a(title, link, alt)

  def print(node: Link): String =
    node.alt match {
      case Some(alt) => s"""[${node.title.value}](${node.link.value} "${alt.value}")"""
      case None      => s"[${node.title.value}](${node.link.value})"
    }
