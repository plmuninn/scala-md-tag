package pl.muninn.markdown.common.basic.block

import pl.muninn.markdown.common.MarkdownFragment.BlockFragment
import pl.muninn.markdown.common.MarkdownNode.Block
import pl.muninn.markdown.common.Configuration

class HorizontalLine extends Block

object HorizontalLine:

  object Partial:
    def hr(using configuration: Configuration): HorizontalLine = HorizontalLine()

  def hr(using md: BlockFragment, configuration: Configuration) = md += Partial.hr

  def print: String = "---\n"
