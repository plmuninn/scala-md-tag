package pl.muninn.markdown.common.print

import pl.muninn.markdown.common.MarkdownNode

trait GenerateMarkdown:

  def generate[T <: MarkdownNode](markdown: T): Either[Throwable, String]

  def generateUnsafe[T <: MarkdownNode](markdown: T): String =
    generate(markdown) match {
      case Left(value)  => throw value
      case Right(value) => value
    }
