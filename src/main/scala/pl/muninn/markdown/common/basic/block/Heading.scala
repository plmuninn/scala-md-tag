package pl.muninn.markdown.common.basic.block

import pl.muninn.markdown.common.MarkdownFragment.{BlockFragment, BlockWithSpanFragment}
import pl.muninn.markdown.common.MarkdownNode.Block
import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownContext.{SpanContextFn, createSpanPartialContextForBlock}

case class Heading(level: Int) extends BlockWithSpanFragment

object Heading:

  object Partial:
    def h(init: SpanContextFn)(using configuration: Configuration): Heading = createSpanPartialContextForBlock(Heading(1), init)

    def h1(init: SpanContextFn)(using configuration: Configuration): Heading = createSpanPartialContextForBlock(Heading(1), init)

    def h2(init: SpanContextFn)(using configuration: Configuration): Heading = createSpanPartialContextForBlock(Heading(2), init)

    def h3(init: SpanContextFn)(using configuration: Configuration): Heading = createSpanPartialContextForBlock(Heading(3), init)

    def h4(init: SpanContextFn)(using configuration: Configuration): Heading = createSpanPartialContextForBlock(Heading(4), init)

    def h5(init: SpanContextFn)(using configuration: Configuration): Heading = createSpanPartialContextForBlock(Heading(5), init)

    def h6(init: SpanContextFn)(using configuration: Configuration): Heading = createSpanPartialContextForBlock(Heading(6), init)
  end Partial

  def h(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h(init)

  def h1(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h1(init)

  def h2(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h2(init)

  def h3(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h3(init)

  def h4(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h4(init)

  def h5(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h5(init)

  def h6(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h6(init)

  def print(heading: Heading, body: String): String = ("#" * heading.level) + " " + body + "\n"
