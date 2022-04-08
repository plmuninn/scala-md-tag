package pl.muninn.markdown.basic.block

import pl.muninn.markdown.Configuration
import pl.muninn.markdown.MarkdownFragment.BlockFragment
import pl.muninn.markdown.MarkdownNode.Block
import pl.muninn.markdown.basic.span.Text

case class CodeBlock(language: Option[String], code: Text) extends Block

object CodeBlock:

  object Partial:
    def codeBlock(language: String, code: String)(using configuration: Configuration): CodeBlock =
      CodeBlock(language = Some(language), code = Text.escaped(code, configuration))

    def codeBlock(code: String)(using configuration: Configuration): CodeBlock =
      CodeBlock(language = None, code = Text.escaped(code, configuration))
  end Partial

  def codeBlock(language: String, code: String)(using md: BlockFragment, configuration: Configuration) = md += Partial.codeBlock(language, code)

  def codeBlock(code: String)(using md: BlockFragment, configuration: Configuration) = md += Partial.codeBlock(code)

  def print(block: CodeBlock): String =
    block match {
      case CodeBlock(Some(language), code) => s"""```$language\n${code.value}\n```"""
      case CodeBlock(_, code)              => s"""```\n${code.value}\n```"""
    }
