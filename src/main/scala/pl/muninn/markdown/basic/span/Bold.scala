package pl.muninn.markdown.basic.span

import pl.muninn.markdown.MarkdownContext.{AnyMarkdownFragment, SpanContextFn, createSpanContext}
import pl.muninn.markdown.MarkdownFragment.SpanFragment

class Bold extends SpanFragment

object Bold:
  def b(init: SpanContextFn)(using md: AnyMarkdownFragment) = createSpanContext(Bold(), init)

  def print(body: String): String = s"**${body}**"
