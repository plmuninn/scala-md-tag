package pl.muninn.markdown.basic.span

import pl.muninn.markdown.Configuration
import pl.muninn.markdown.MarkdownContext.{AnyMarkdownFragment, SpanContextFn, createSpanPartialContext}
import pl.muninn.markdown.MarkdownFragment.SpanFragment

class Italic extends SpanFragment

object Italic:

  object Partial:
    def i(init: SpanContextFn)(using configuration: Configuration): Italic = createSpanPartialContext(Italic(), init)

  def i(init: SpanContextFn)(using md: AnyMarkdownFragment, configuration: Configuration) = md += Partial.i(init)

  def print(body: String): String = s"_${body}_"
