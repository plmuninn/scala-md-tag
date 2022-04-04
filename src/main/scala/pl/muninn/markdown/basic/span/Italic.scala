package pl.muninn.markdown.basic.span

import pl.muninn.markdown.MarkdownContext.{AnyMarkdownFragment, SpanContextFn, createSpanContext}
import pl.muninn.markdown.MarkdownFragment.SpanFragment

class Italic extends SpanFragment

object Italic:

  def i(init: SpanContextFn)(using md: AnyMarkdownFragment) = createSpanContext(Italic(), init)

  def print(body: String): String = s"_${body}_"
