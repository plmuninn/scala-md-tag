package pl.muninn.markdown.basic.block

import pl.muninn.markdown.MarkdownContext.{SpanContextFn, createSpanContextForBlock}
import pl.muninn.markdown.MarkdownFragment.{BlockFragment, BlockWithSpanFragment}
import pl.muninn.markdown.MarkdownNode.Block

case class Heading(level: Int) extends BlockWithSpanFragment

object Heading:

  def h(init: SpanContextFn)(using md: BlockFragment) = createSpanContextForBlock(Heading(1), init)

  def h1(init: SpanContextFn)(using md: BlockFragment) = createSpanContextForBlock(Heading(1), init)

  def h2(init: SpanContextFn)(using md: BlockFragment) = createSpanContextForBlock(Heading(2), init)

  def h3(init: SpanContextFn)(using md: BlockFragment) = createSpanContextForBlock(Heading(3), init)

  def h4(init: SpanContextFn)(using md: BlockFragment) = createSpanContextForBlock(Heading(4), init)

  def h5(init: SpanContextFn)(using md: BlockFragment) = createSpanContextForBlock(Heading(5), init)

  def h6(init: SpanContextFn)(using md: BlockFragment) = createSpanContextForBlock(Heading(6), init)

  def print(heading: Heading, body: String): String = ("#" * heading.level) + " " + body + "\n"
