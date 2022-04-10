package pl.muninn.markdown.common

import pl.muninn.markdown.common.MarkdownNode.{Block, Span}
import pl.muninn.markdown.common.basic.block.{BreakLine, Heading, Paragraph, List => MarkdownList}

import scala.collection.mutable.ArrayBuffer

object MarkdownSafeInserting:

  def safeInsert[T <: MarkdownNode](node: T, values: ArrayBuffer[MarkdownNode])(using configuration: Configuration): Unit =
    (values.lastOption, node) match {
      case (Some(_: Span), _: Block)  => values.addAll(List(BreakLine.Partial.br(), node))
      case (Some(_: Block), _: Span)  => values.addAll(List(BreakLine.Partial.br(), node))
      case (Some(_: Block), _: Block) => values.addAll(List(BreakLine.Partial.br(), node))
      case _                          => values.addOne(node)
    }

  def safeInsertMany[T <: MarkdownNode](nodes: Iterable[T], values: ArrayBuffer[MarkdownNode])(using configuration: Configuration): Unit =
    nodes.foreach(node => safeInsert(node, values))
