package pl.muninn.markdown.common.basic.block

import pl.muninn.markdown.common.MarkdownFragment.BlockFragment
import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownContext.{BasicContextFn, createBlockPartialContext}

import java.util.Arrays
import scala.io.Source
import scala.jdk.CollectionConverters.*

class Blockquotes extends BlockFragment

object Blockquotes:

  object Partial:
    def blockquotes(init: BasicContextFn)(using configuration: Configuration): Blockquotes = createBlockPartialContext(Blockquotes(), init)

  def blockquotes(init: BasicContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.blockquotes(init)

  def print(body: String): String =
    Source
      .fromString(body)
      .getLines()
      .map(line => s"s> $line")
      .mkString("\n")
