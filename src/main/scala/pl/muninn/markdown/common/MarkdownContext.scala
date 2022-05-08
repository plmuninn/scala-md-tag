package pl.muninn.markdown.common

import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownFragment.{BlockFragment, BlockWithSpanFragment, SpanFragment}
import pl.muninn.markdown.common.MarkdownNode.{Block, Span}
import pl.muninn.markdown.common.basic.span.Text

object MarkdownContext:

  type AnyMarkdownFragment = BlockFragment | SpanFragment | BlockWithSpanFragment

  type StringConversion = Conversion[String, Text]

  type BasicContextFn = (BlockFragment, StringConversion) ?=> Span | Block

  type SpanContextFn = (SpanFragment | BlockWithSpanFragment, StringConversion) ?=> Span

  type SpanWithParentContextFn[T] = (SpanFragment | BlockWithSpanFragment, T, StringConversion) ?=> Span

  def createBlockPartialContext[T <: BlockFragment](value: T, init: BasicContextFn)(using Configuration): T =
    given fragment: BlockFragment      = value
    given conversion: StringConversion = magneticStringToTextConversion(using fragment)
    init(using fragment, conversion)
    value
  end createBlockPartialContext

  def createSpanPartialContext[T <: SpanFragment](value: T, init: SpanContextFn)(using Configuration): T =
    given fragment: SpanFragment       = value
    given conversion: StringConversion = magneticStringToTextConversion(using fragment)
    init(using fragment, conversion)
    value
  end createSpanPartialContext

  def createSpanPartialContextForBlock[T <: BlockWithSpanFragment](value: T, init: SpanContextFn)(using Configuration): T =
    given fragment: BlockWithSpanFragment = value
    given conversion: StringConversion    = magneticStringToTextConversion(using fragment)
    init(using fragment, conversion)
    value
  end createSpanPartialContextForBlock

  def createSpanContext[T <: SpanFragment](value: T, init: SpanWithParentContextFn[T])(using Configuration): T =
    given fragment: SpanFragment       = value
    given conversion: StringConversion = magneticStringToTextConversion(using fragment)
    init(using fragment, value, conversion)
    value
  end createSpanContext

  def createSpanContextForBlock[T <: BlockWithSpanFragment](value: T, init: SpanWithParentContextFn[T])(using Configuration): T =
    given fragment: BlockWithSpanFragment = value
    given conversion: StringConversion    = magneticStringToTextConversion(using fragment)
    init(using fragment, value, conversion)
    value
  end createSpanContextForBlock

  def magneticStringToTextConversion(using md: AnyMarkdownFragment, configuration: Configuration): Conversion[String, Text] =
    new Conversion[String, Text]():
      override def apply(value: String): Text = md.add(Text.escaped(value, configuration))

end MarkdownContext
