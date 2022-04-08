package pl.muninn.markdown.common.print

import pl.muninn.markdown.common.MarkdownNode
import pl.muninn.markdown.common.print.GraphPrinter

import scala.reflect.ClassTag

trait GenerateGraph:

  def generateGraph[T <: MarkdownNode](markdown: T): Either[Throwable, String] = GraphPrinter.generate(markdown)

  def generateGraphUnsafe[T <: MarkdownNode](markdown: T): String =
    generateGraph(markdown) match {
      case Left(value)  => throw value
      case Right(value) => value
    }
