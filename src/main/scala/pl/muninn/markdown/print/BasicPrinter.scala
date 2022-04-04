package pl.muninn.markdown.print

import pl.muninn.markdown.MarkdownFragment.{BlockFragment, BlockWithSpanFragment, MarkdownDocument, SpanFragment}
import pl.muninn.markdown.basic.block.*
import pl.muninn.markdown.basic.block.List.ListElement
import pl.muninn.markdown.basic.span.*
import pl.muninn.markdown.{MarkdownFragment, MarkdownNode}

import scala.util.Try

object BasicPrinter extends MarkdownPrinter:

  def getBody[T <: MarkdownNode](node: T): Option[String] =
    node match {
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
      case (Some(body), node: Heading)         => Heading.print(node, body)
      case (Some(body), node: Paragraph)       => Paragraph.print(body)
      case (Some(body), node: Bold)            => Bold.print(body)
      case (None, node: Code)                  => Code.print(node)
      case (None, node: Image)                 => Image.print(node)
      case (Some(body), node: Italic)          => Italic.print(body)
      case (None, node: Link)                  => Link.print(node)
      case (Some(body), node: Strikethrough)   => Strikethrough.print(body)
      case (maybeBody, node: MarkdownDocument) => maybeBody.getOrElse("")
      case (Some(body), node: ListElement)     => body
      case (None, node: List)                  => List.print(node, printNode)
      case (maybeBody, node)                   => throw new RuntimeException(s"Node $node unsupported with body $maybeBody")
    }

  override def generate[T <: MarkdownNode](markdown: T): Either[Throwable, String] =
    Try(printNode(markdown)).toEither
