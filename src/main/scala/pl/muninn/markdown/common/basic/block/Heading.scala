package pl.muninn.markdown.common.basic.block

import pl.muninn.markdown.common.MarkdownFragment.{BlockFragment, BlockWithSpanFragment}
import pl.muninn.markdown.common.MarkdownNode.Block
import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownContext.{SpanWithParentContextFn, createSpanContextForBlock}

case class Heading(var level: Int, var id: Option[String] = None) extends BlockWithSpanFragment

object Heading:

  type HeadingContextFn = SpanWithParentContextFn[Heading]

  object Partial:
    def h(init: HeadingContextFn)(using configuration: Configuration): Heading = createSpanContextForBlock(Heading(1), init)

    def h1(init: HeadingContextFn)(using configuration: Configuration): Heading = createSpanContextForBlock(Heading(1), init)

    def h2(init: HeadingContextFn)(using configuration: Configuration): Heading = createSpanContextForBlock(Heading(2), init)

    def h3(init: HeadingContextFn)(using configuration: Configuration): Heading = createSpanContextForBlock(Heading(3), init)

    def h4(init: HeadingContextFn)(using configuration: Configuration): Heading = createSpanContextForBlock(Heading(4), init)

    def h5(init: HeadingContextFn)(using configuration: Configuration): Heading = createSpanContextForBlock(Heading(5), init)

    def h6(init: HeadingContextFn)(using configuration: Configuration): Heading = createSpanContextForBlock(Heading(6), init)
  end Partial

  def h(init: HeadingContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h(init)

  def h1(init: HeadingContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h1(init)

  def h2(init: HeadingContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h2(init)

  def h3(init: HeadingContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h3(init)

  def h4(init: HeadingContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h4(init)

  def h5(init: HeadingContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h5(init)

  def h6(init: HeadingContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h6(init)

  def setId(id: String)(using heading: Heading) =
    heading.id = Some(id)

  def setLevel(level: Int)(using heading: Heading) =
    heading.level = level

  def print(heading: Heading, body: String): String =
    ("#" * heading.level) + " " + body + heading.id.map(id => s" {# $id}").getOrElse("") + "\n"
