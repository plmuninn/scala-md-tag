package pl.muninn.markdown.basic.span

import pl.muninn.markdown.Configuration
import pl.muninn.markdown.MarkdownContext.{AnyMarkdownFragment, SpanContextFn, createSpanPartialContext}
import pl.muninn.markdown.MarkdownFragment.SpanFragment

class Strikethrough extends SpanFragment

object Strikethrough:

  object Partial:
    def s(init: SpanContextFn)(using configuration: Configuration): Strikethrough = createSpanPartialContext(Strikethrough(), init)

  def s(init: SpanContextFn)(using md: AnyMarkdownFragment, configuration: Configuration) = md += Partial.s(init)

  def print(body: String): String = s"~~$body~~"
