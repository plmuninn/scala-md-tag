package pl.muninn.markdown.common.basic.block

import pl.muninn.markdown.common.MarkdownFragment.{BlockFragment, BlockWithSpanFragment}
import pl.muninn.markdown.common.MarkdownNode.Block
import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownContext.{SpanContextFn, createSpanPartialContextForBlock}

case class Heading(level: Int, id: Option[String] = None) extends BlockWithSpanFragment

object Heading:

  object Partial:
    def h(init: SpanContextFn)(using configuration: Configuration): Heading = createSpanPartialContextForBlock(Heading(1), init)
    def hId(id: String)(init: SpanContextFn)(using configuration: Configuration): Heading =
      createSpanPartialContextForBlock(Heading(1, Some(id)), init)

    def h1(init: SpanContextFn)(using configuration: Configuration): Heading = createSpanPartialContextForBlock(Heading(1), init)
    def h1Id(id: String)(init: SpanContextFn)(using configuration: Configuration): Heading =
      createSpanPartialContextForBlock(Heading(1, Some(id)), init)

    def h2(init: SpanContextFn)(using configuration: Configuration): Heading = createSpanPartialContextForBlock(Heading(2), init)
    def h2Id(id: String)(init: SpanContextFn)(using configuration: Configuration): Heading =
      createSpanPartialContextForBlock(Heading(2, Some(id)), init)

    def h3(init: SpanContextFn)(using configuration: Configuration): Heading = createSpanPartialContextForBlock(Heading(3), init)
    def h3Id(id: String)(init: SpanContextFn)(using configuration: Configuration): Heading =
      createSpanPartialContextForBlock(Heading(3, Some(id)), init)

    def h4(init: SpanContextFn)(using configuration: Configuration): Heading = createSpanPartialContextForBlock(Heading(4), init)
    def h4Id(id: String)(init: SpanContextFn)(using configuration: Configuration): Heading =
      createSpanPartialContextForBlock(Heading(4, Some(id)), init)

    def h5(init: SpanContextFn)(using configuration: Configuration): Heading = createSpanPartialContextForBlock(Heading(5), init)
    def h5Id(id: String)(init: SpanContextFn)(using configuration: Configuration): Heading =
      createSpanPartialContextForBlock(Heading(5, Some(id)), init)

    def h6(init: SpanContextFn)(using configuration: Configuration): Heading = createSpanPartialContextForBlock(Heading(6), init)
    def h6Id(id: String)(init: SpanContextFn)(using configuration: Configuration): Heading =
      createSpanPartialContextForBlock(Heading(6, Some(id)), init)
  end Partial

  def h(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration)               = md += Partial.h(init)
  def hId(id: String)(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.hId(id)(init)

  def h1(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration)               = md += Partial.h1(init)
  def h1Id(id: String)(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h1Id(id)(init)

  def h2(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration)               = md += Partial.h2(init)
  def h2Id(id: String)(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h2Id(id)(init)

  def h3(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration)               = md += Partial.h3(init)
  def h3Id(id: String)(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h3Id(id)(init)

  def h4(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration)               = md += Partial.h4(init)
  def h4Id(id: String)(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h4Id(id)(init)

  def h5(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration)               = md += Partial.h5(init)
  def h5Id(id: String)(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h5Id(id)(init)

  def h6(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration)               = md += Partial.h6(init)
  def h6Id(id: String)(init: SpanContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.h6Id(id)(init)

  def print(heading: Heading, body: String): String =
    ("#" * heading.level) + " " + body + heading.id.map(id => s" {# $id}").getOrElse("") + "\n"
