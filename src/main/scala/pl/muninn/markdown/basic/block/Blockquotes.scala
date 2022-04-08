package pl.muninn.markdown.basic.block

import pl.muninn.markdown.Configuration
import pl.muninn.markdown.MarkdownContext.{BasicContextFn, createBlockPartialContext}
import pl.muninn.markdown.MarkdownFragment.BlockFragment

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
