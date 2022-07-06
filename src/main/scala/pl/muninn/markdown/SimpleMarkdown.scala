package pl.muninn.markdown

import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.simple.{ContextSimpleMarkdown, PartialSimpleMarkdown}

// Implementation of https://daringfireball.net/projects/markdown/syntax
object SimpleMarkdown extends Markdown with ContextSimpleMarkdown:
  type MarkdownConfig = Configuration
  val partial: PartialSimpleMarkdown = PartialSimpleMarkdown
