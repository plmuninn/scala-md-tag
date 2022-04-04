package pl.muninn.markdown

import pl.muninn.markdown.print.GraphPrinter

import scala.reflect.ClassTag

trait MarkdownGraphGeneration:

  def generateGraph[T <: MarkdownNode](markdown: T): Either[Throwable, String] = GraphPrinter.generate(markdown)

  def generateGraphUnsafe[T <: MarkdownNode](markdown: T): String =
    generateGraph(markdown) match {
      case Left(value)  => throw value
      case Right(value) => value
    }
