package pl.muninn.markdown.basic.block

import pl.muninn.markdown.Configuration
import pl.muninn.markdown.MarkdownContext.{BasicContextFn, createBlockContext}
import pl.muninn.markdown.MarkdownFragment.BlockFragment

class Paragraph extends BlockFragment

object Paragraph:

  def p(init: BasicContextFn)(using md: BlockFragment, configuration: Configuration) = createBlockContext(Paragraph(), init)

  def print(body: String): String = s"\n$body\n"
