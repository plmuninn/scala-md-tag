package pl.muninn.markdown.common.basic.block

import pl.muninn.markdown.common.MarkdownFragment.BlockFragment
import pl.muninn.markdown.common.MarkdownNode.Block
import pl.muninn.markdown.common.Configuration

case class BreakLine(hard: Boolean) extends Block

object BreakLine:

  object Partial:
    def br(hard: Boolean = false)(using configuration: Configuration): BreakLine = BreakLine(hard)

  def br(hard: Boolean)(using md: BlockFragment, configuration: Configuration) = md += Partial.br(hard)
  def br(using md: BlockFragment, configuration: Configuration)                = md += Partial.br(false)

  def print(node: BreakLine): String =
    if node.hard then "  \n" else "\n"
