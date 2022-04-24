package pl.muninn.markdown.common.basic.span

import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownContext.{AnyMarkdownFragment, SpanContextFn, createSpanPartialContext}
import pl.muninn.markdown.common.MarkdownFragment.SpanFragment

// Important - TextFragment is SpanFragment but during rendering is not dividing nodes with " "
class TextFragment extends SpanFragment

object TextFragment:

  object Partial:
    def div(init: SpanContextFn)(using configuration: Configuration) = createSpanPartialContext(TextFragment(), init)

  def div(init: SpanContextFn)(using md: AnyMarkdownFragment, configuration: Configuration) = md += Partial.div(init)
