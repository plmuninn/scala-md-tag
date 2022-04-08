package pl.muninn.markdown.common.basic.span

import pl.muninn.markdown.common.MarkdownNode.Span
import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownContext.AnyMarkdownFragment

case class Link(alt: Text, link: Text, title: Option[Text]) extends Span

object Link:

  object Partial:
    def a(alt: String, link: String)(using configuration: Configuration): Link = Link(alt = Text(alt), link = Text(link), title = None)

    def a(alt: String, link: String, title: String)(using configuration: Configuration): Link =
      Link(alt = Text(alt), link = Text(link), title = Some(Text(title)))
  end Partial

  def a(alt: String, link: String)(using md: AnyMarkdownFragment, configuration: Configuration) = md += Partial.a(alt, link)

  def a(alt: String, link: String, title: String)(using md: AnyMarkdownFragment, configuration: Configuration) = md += Partial.a(alt, link, title)

  def print(node: Link): String =
    node.title match {
      case Some(titleResult) => s"""[${node.alt.value}](${node.link.value} "${titleResult.value}")"""
      case None              => s"[${node.alt.value}](${node.link.value})"
    }
