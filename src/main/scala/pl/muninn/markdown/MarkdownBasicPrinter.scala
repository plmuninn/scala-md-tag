package pl.muninn.markdown

import pl.muninn.markdown.print.BasicPrinter

trait MarkdownBasicPrinter:
  def generate[T <: MarkdownNode](markdown: T): Either[Throwable, String] = BasicPrinter.generate(markdown)

  def generateUnsafe[T <: MarkdownNode](markdown: T): String =
    generate(markdown) match {
      case Left(value)  => throw value
      case Right(value) => value
    }
