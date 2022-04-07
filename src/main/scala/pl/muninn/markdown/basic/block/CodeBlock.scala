package pl.muninn.markdown.basic.block

import pl.muninn.markdown.Configuration
import pl.muninn.markdown.MarkdownFragment.BlockFragment
import pl.muninn.markdown.MarkdownNode.Block
import pl.muninn.markdown.basic.span.Text

case class CodeBlock(language: Option[String], code: Text) extends Block

object CodeBlock:

  def codeBlock(language: String, code: String)(using md: BlockFragment, configuration: Configuration) =
    md += CodeBlock(language = Some(language), code = Text.escaped(code, configuration))

  def codeBlock(code: String)(using md: BlockFragment, configuration: Configuration) =
    md += CodeBlock(language = None, code = Text.escaped(code, configuration))

  def print(block: CodeBlock): String =
    block match {
      case CodeBlock(Some(language), code) => s"""```$language\n${code.value}\n```"""
      case CodeBlock(_, code)              => s"""```\n${code.value}\n```"""
    }
