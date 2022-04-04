package pl.muninn.markdown.basic.span

import pl.muninn.markdown.MarkdownContext.{AnyMarkdownFragment, SpanContextFn, createSpanContext}
import pl.muninn.markdown.MarkdownFragment.SpanFragment

class Strikethrough extends SpanFragment

object Strikethrough:
  def s(init: SpanContextFn)(using md: AnyMarkdownFragment) = createSpanContext(Strikethrough(), init)

  def print(body: String): String = s"~~$body~~"