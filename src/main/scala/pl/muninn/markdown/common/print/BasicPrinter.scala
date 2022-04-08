package pl.muninn.markdown.common.print

import pl.muninn.markdown.common.MarkdownFragment
import pl.muninn.markdown.common.MarkdownFragment.{BlockFragment, BlockWithSpanFragment, MarkdownDocument, SpanFragment}
import pl.muninn.markdown.common.basic.block.*
import pl.muninn.markdown.common.basic.block.List.ListElement
import pl.muninn.markdown.common.basic.span.*
import pl.muninn.markdown.common.MarkdownNode
import pl.muninn.markdown.common.basic.block
import pl.muninn.markdown.common.basic.block.{Blockquotes, BreakLine, CodeBlock, Heading, HorizontalLine, Paragraph, Table}
import pl.muninn.markdown.common.basic.span.{Bold, Code, Image, Italic, Link, Strikethrough, Text, TextFragment}

import scala.util.Try

object BasicPrinter extends MarkdownPrinter:

  def getBody[T <: MarkdownNode](node: T): Option[String] =
    node match {
      case textFragment: TextFragment                   => Option(textFragment.values.map(printNode).mkString(""))
      case spanFragment: SpanFragment                   => Option(spanFragment.values.map(printNode).mkString(" "))
      case blockFragment: BlockFragment                 => Option(blockFragment.values.map(printNode).mkString(""))
      case blockWithSpanFragment: BlockWithSpanFragment => Option(blockWithSpanFragment.values.map(printNode).mkString(" "))
      case _                                            => None
    }

  def printNode[T <: MarkdownNode](node: T): String =
    val bodyOpt: Option[String] = getBody(node)
    (bodyOpt, node) match {
      case (None, node: Text)                  => Text.print(node)
      case (None, node: BreakLine)             => BreakLine.print
      case (None, node: HorizontalLine)        => HorizontalLine.print
      case (None, node: Code)                  => Code.print(node)
      case (None, node: Image)                 => Image.print(node)
      case (None, node: Link)                  => Link.print(node)
      case (None, node: block.List)            => List.print(node, printNode)
      case (None, node: CodeBlock)             => CodeBlock.print(node)
      case (None, node: Table)                 => Table.print(node, printNode)
      case (Some(body), node: Blockquotes)     => Blockquotes.print(body)
      case (Some(body), node: Heading)         => Heading.print(node, body)
      case (Some(body), node: Paragraph)       => Paragraph.print(body)
      case (Some(body), node: Bold)            => Bold.print(body)
      case (Some(body), node: Italic)          => Italic.print(body)
      case (Some(body), node: Strikethrough)   => Strikethrough.print(body)
      case (Some(body), node: ListElement)     => body
      case (Some(body), node: TextFragment)    => body
      case (maybeBody, node: MarkdownDocument) => maybeBody.getOrElse("")
      case (maybeBody, node)                   => throw new RuntimeException(s"Node $node unsupported with body $maybeBody")
    }

  override def generate[T <: MarkdownNode](markdown: T): Either[Throwable, String] =
    Try(printNode(markdown)).toEither