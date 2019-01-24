package pl.muninn.scalamdtag.tags

case class CodeBlock(language: Option[String], code: String) extends BlockMarkdownTag

object CodeBlock {
  implicit val renderCodeBlock: Renderer[CodeBlock] = {
    case CodeBlock(Some(language), code) => s"""```$language\n$code\n```"""
    case CodeBlock(_, code)              => s"""```\n$code\n```"""
  }
}
