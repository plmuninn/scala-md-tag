package pl.muninn.markdown

import pl.muninn.markdown.simple.{ContextSimpleMarkdown, PartialSimpleMarkdown}

// Implementation of https://daringfireball.net/projects/markdown/syntax
object SimpleMarkdown extends Markdown with ContextSimpleMarkdown:
  val partial: PartialSimpleMarkdown = PartialSimpleMarkdown
