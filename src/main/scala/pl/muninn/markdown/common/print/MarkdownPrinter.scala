package pl.muninn.markdown.common.print

import pl.muninn.markdown.common.MarkdownFragment
import pl.muninn.markdown.common.MarkdownFragment.BlockFragment
import pl.muninn.markdown.common.MarkdownNode

import scala.collection.mutable.ArrayBuffer
import scala.reflect.{ClassTag, classTag}
import scala.util.Try

trait MarkdownPrinter:
  def generate[T <: MarkdownNode](markdown: T): Either[Throwable, String]
