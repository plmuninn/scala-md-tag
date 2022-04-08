package pl.muninn.markdown.common.basic.block

import pl.muninn.markdown.common.MarkdownFragment.BlockFragment
import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownContext.{BasicContextFn, createBlockPartialContext}

class Paragraph extends BlockFragment

object Paragraph:

  object Partial:
    def p(init: BasicContextFn)(using configuration: Configuration): Paragraph = createBlockPartialContext(Paragraph(), init)

  def p(init: BasicContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.p(init)

  def print(body: String): String = s"\n$body\n"
