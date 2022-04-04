package pl.muninn.markdown.print

import pl.muninn.markdown.MarkdownFragment.BlockFragment
import pl.muninn.markdown.{MarkdownFragment, MarkdownNode}

import scala.collection.mutable.ArrayBuffer
import scala.reflect.{ClassTag, classTag}
import scala.util.Try

trait MarkdownPrinter:
  def generate[T <: MarkdownNode](markdown: T): Either[Throwable, String]
