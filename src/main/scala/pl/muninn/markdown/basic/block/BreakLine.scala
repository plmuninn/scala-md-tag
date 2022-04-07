package pl.muninn.markdown.basic.block

import pl.muninn.markdown.Configuration
import pl.muninn.markdown.MarkdownFragment.BlockFragment
import pl.muninn.markdown.MarkdownNode.Block

class BreakLine extends Block

object BreakLine:

  def br(using md: BlockFragment, configuration: Configuration) = md += BreakLine()

  def print: String = "  \n"
