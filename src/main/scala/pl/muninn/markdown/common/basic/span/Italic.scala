package pl.muninn.markdown.common.basic.span

import pl.muninn.markdown.common.MarkdownFragment.SpanFragment
import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownContext.{AnyMarkdownFragment, SpanContextFn, createSpanPartialContext}

class Italic extends SpanFragment

object Italic:

  object Partial:
    def i(init: SpanContextFn)(using configuration: Configuration): Italic = createSpanPartialContext(Italic(), init)

  def i(init: SpanContextFn)(using md: AnyMarkdownFragment, configuration: Configuration) = md += Partial.i(init)

  def print(body: String): String = s"_${body}_"
