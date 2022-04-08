package pl.muninn.markdown.common.basic.span

import pl.muninn.markdown.common.MarkdownFragment.SpanFragment
import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownContext.{AnyMarkdownFragment, SpanContextFn, createSpanPartialContext}

class Bold extends SpanFragment

object Bold:

  object Partial:
    def b(init: SpanContextFn)(using configuration: Configuration): Bold = createSpanPartialContext(Bold(), init)

  def b(init: SpanContextFn)(using md: AnyMarkdownFragment, configuration: Configuration) = md += Partial.b(init)

  def print(body: String): String = s"**${body}**"
