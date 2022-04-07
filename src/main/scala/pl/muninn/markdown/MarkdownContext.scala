package pl.muninn.markdown

import pl.muninn.markdown.MarkdownFragment.{BlockFragment, BlockWithSpanFragment, SpanFragment}
import pl.muninn.markdown.MarkdownNode.{Block, Span}
import pl.muninn.markdown.basic.span.Text

object MarkdownContext:

  type AnyMarkdownFragment = BlockFragment | SpanFragment | BlockWithSpanFragment

  type StringConversion = Conversion[String, Text]

  type BasicContextFn = (BlockFragment, StringConversion) ?=> Span | Block

  type SpanContextFn = (SpanFragment | BlockWithSpanFragment, StringConversion) ?=> Span

  def createBlockContext(value: BlockFragment, init: BasicContextFn)(using md: BlockFragment, configuration: Configuration) =
    given fragment: BlockFragment      = value
    given conversion: StringConversion = magneticStringToTextConversion(using fragment)
    init(using fragment, conversion)
    md.add(value)

  def createSpanContext(value: SpanFragment, init: SpanContextFn)(using md: AnyMarkdownFragment, configuration: Configuration) =
    given fragment: SpanFragment       = value
    given conversion: StringConversion = magneticStringToTextConversion(using fragment)
    init(using fragment, conversion)
    md.add(value)

  def createSpanContextForBlock(value: BlockWithSpanFragment, init: SpanContextFn)(using md: BlockFragment, configuration: Configuration) =
    given fragment: BlockWithSpanFragment = value
    given conversion: StringConversion    = magneticStringToTextConversion(using fragment)
    init(using fragment, conversion)
    md.add(value)

  def magneticStringToTextConversion(using md: AnyMarkdownFragment, configuration: Configuration) = new Conversion[String, Text]():
    override def apply(value: String): Text = md.add(Text.escaped(value, configuration))

end MarkdownContext
