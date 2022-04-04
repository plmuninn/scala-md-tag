package pl.muninn.markdown.basic.block

import pl.muninn.markdown.MarkdownNode.Block
import pl.muninn.markdown.basic.span.Text

case class CodeBlock(language: Option[String], code: Text) extends Block

object CodeBlock:
  def print(block: CodeBlock): String =
    block match {
      case CodeBlock(Some(language), code) => s"""```$language\n${code.value}\n```"""
      case CodeBlock(_, code)              => s"""```\n${code.value}\n```"""
    }
