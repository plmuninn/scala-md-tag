package pl.muninn.markdown.common.basic.span

import pl.muninn.markdown.common.MarkdownNode.Span
import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownContext.AnyMarkdownFragment

case class Code(text: Text) extends Span

object Code:
  object Partial:
    def code(value: String)(using configuration: Configuration): Code = Code(Text(value))

  def code(value: String)(using md: AnyMarkdownFragment, configuration: Configuration) = md += Partial.code(value)

  def print(node: Code): String = s"`${node.text.value}`"
