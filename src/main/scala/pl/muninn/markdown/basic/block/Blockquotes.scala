package pl.muninn.markdown.basic.block

import pl.muninn.markdown.Configuration
import pl.muninn.markdown.MarkdownContext.{BasicContextFn, createBlockContext}
import pl.muninn.markdown.MarkdownFragment.BlockFragment

import java.util.Arrays
import scala.io.Source
import scala.jdk.CollectionConverters.*

class Blockquotes extends BlockFragment

object Blockquotes:

  def blockquotes(init: BasicContextFn)(using md: BlockFragment, configuration: Configuration) = createBlockContext(Blockquotes(), init)

  def print(body: String): String =
    Source
      .fromString(body)
      .getLines()
      .map(line => s"s> $line")
      .mkString("\n")
