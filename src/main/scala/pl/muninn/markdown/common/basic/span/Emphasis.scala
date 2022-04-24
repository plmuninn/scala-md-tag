package pl.muninn.markdown.common.basic.span

import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownContext.{AnyMarkdownFragment, SpanContextFn, createSpanPartialContext}
import pl.muninn.markdown.common.MarkdownFragment.SpanFragment

class Emphasis extends SpanFragment

object Emphasis:
  object Partial:
    def em(init: SpanContextFn)(using configuration: Configuration): Emphasis = createSpanPartialContext(Emphasis(), init)

  def em(init: SpanContextFn)(using md: AnyMarkdownFragment, configuration: Configuration) = md += Partial.em(init)

  def print(body: String): String = s"*${body}*"
