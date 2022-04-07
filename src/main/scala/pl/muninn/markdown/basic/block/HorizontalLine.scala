package pl.muninn.markdown.basic.block

import pl.muninn.markdown.Configuration
import pl.muninn.markdown.MarkdownFragment.BlockFragment
import pl.muninn.markdown.MarkdownNode.Block

class HorizontalLine extends Block

object HorizontalLine:
  def hr(using md: BlockFragment, configuration: Configuration) = md += HorizontalLine()

  def print: String = "---\n"
