package pl.muninn.markdown.basic.block

import pl.muninn.markdown.MarkdownFragment.BlockFragment
import pl.muninn.markdown.MarkdownNode.Block

class HorizontalLine extends Block

object HorizontalLine:
  def hr(using md: BlockFragment) = md += HorizontalLine()

  def print: String = "---\n"
