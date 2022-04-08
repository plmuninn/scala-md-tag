package pl.muninn.markdown.common.basic.span

import pl.muninn.markdown.common.MarkdownFragment.SpanFragment
import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownContext.{AnyMarkdownFragment, SpanContextFn, createSpanPartialContext}

class Strikethrough extends SpanFragment

object Strikethrough:

  object Partial:
    def s(init: SpanContextFn)(using configuration: Configuration): Strikethrough = createSpanPartialContext(Strikethrough(), init)

  def s(init: SpanContextFn)(using md: AnyMarkdownFragment, configuration: Configuration) = md += Partial.s(init)

  def print(body: String): String = s"~~$body~~"
