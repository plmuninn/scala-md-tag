package pl.muninn.markdown.basic.block

import pl.muninn.markdown.Configuration
import pl.muninn.markdown.MarkdownContext.{BasicContextFn, createBlockPartialContext}
import pl.muninn.markdown.MarkdownFragment.BlockFragment

class Paragraph extends BlockFragment

object Paragraph:

  object Partial:
    def p(init: BasicContextFn)(using configuration: Configuration): Paragraph = createBlockPartialContext(Paragraph(), init)

  def p(init: BasicContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.p(init)

  def print(body: String): String = s"\n$body\n"
