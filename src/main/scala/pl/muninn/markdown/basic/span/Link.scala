package pl.muninn.markdown.basic.span

import pl.muninn.markdown.MarkdownContext.AnyMarkdownFragment
import pl.muninn.markdown.MarkdownNode.Span

case class Link(alt: Text, link: Text, title: Option[Text]) extends Span

object Link:
  def a(alt: String, link: String)(using md: AnyMarkdownFragment) = md += Link(alt = Text(alt), link = Text(link), title = None)

  def a(alt: String, link: String, title: String)(using md: AnyMarkdownFragment) =
    md += Link(alt = Text(alt), link = Text(link), title = Some(Text(title)))

  def print(node: Link): String =
    node.title match {
      case Some(titleResult) => s"""[${node.alt.value}](${node.link.value} "${titleResult.value}")"""
      case None              => s"[${node.alt.value}](${node.link.value})"
    }
