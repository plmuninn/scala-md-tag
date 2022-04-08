package pl.muninn.markdown.basic.span

import pl.muninn.markdown.Configuration
import pl.muninn.markdown.MarkdownContext.AnyMarkdownFragment
import pl.muninn.markdown.MarkdownNode.Span

case class Code(text: Text) extends Span

object Code:
  object Partial:
    def code(value: String)(using configuration: Configuration): Code = Code(Text(value))

  def code(value: String)(using md: AnyMarkdownFragment, configuration: Configuration) = md += Partial.code(value)

  def print(node: Code): String = s"`${node.text.value}`"
