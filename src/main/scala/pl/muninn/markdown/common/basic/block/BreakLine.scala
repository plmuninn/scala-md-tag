package pl.muninn.markdown.common.basic.block

import pl.muninn.markdown.common.MarkdownFragment.BlockFragment
import pl.muninn.markdown.common.MarkdownNode.Block
import pl.muninn.markdown.common.Configuration

class BreakLine extends Block

object BreakLine:

  object Partial:
    def br(using configuration: Configuration): BreakLine = BreakLine()

  def br(using md: BlockFragment, configuration: Configuration) = md += Partial.br

  def print: String = "  \n"
