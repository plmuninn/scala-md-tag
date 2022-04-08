package pl.muninn.markdown.basic.span

import pl.muninn.markdown.Configuration
import pl.muninn.markdown.MarkdownContext.{AnyMarkdownFragment, SpanContextFn, createSpanPartialContext}
import pl.muninn.markdown.MarkdownFragment.SpanFragment

class Bold extends SpanFragment

object Bold:

  object Partial:
    def b(init: SpanContextFn)(using configuration: Configuration): Bold = createSpanPartialContext(Bold(), init)

  def b(init: SpanContextFn)(using md: AnyMarkdownFragment, configuration: Configuration) = md += Partial.b(init)

  def print(body: String): String = s"**${body}**"
