package pl.muninn.markdown.common.print

import pl.muninn.markdown.common.MarkdownFragment
import pl.muninn.markdown.common.{MarkdownFragment, MarkdownNode}

import scala.collection.mutable.ArrayBuffer
import scala.util.Try

object GraphPrinter extends MarkdownPrinter:

  private case class GraphNode(name: String, children: ArrayBuffer[GraphNode] = ArrayBuffer.empty)

  private def getChildren[T <: MarkdownNode](node: T): ArrayBuffer[GraphNode] =
    node match {
      case fragment: MarkdownFragment[_] =>
        fragment.values.foldLeft(ArrayBuffer.empty) { case (acc, node) =>
          acc += buildGraph(node)
        }
      case _ => ArrayBuffer.empty
    }

  private def buildGraph[T <: MarkdownNode](node: T): GraphNode =
    GraphNode(node.name, getChildren(node))

  private def flattenGraphNode(node: GraphPrinter.GraphNode, level: Int): ArrayBuffer[(Int, String)] =
    val nodeName    = if node.children.isEmpty then node.name else s"${node.name}:"
    val currentNode = ArrayBuffer((level, nodeName))
    if node.children.isEmpty then currentNode
    else currentNode ++ node.children.flatMap(childNode => flattenGraphNode(childNode, level + 1))

  private def printGraph(graphNode: GraphNode): String =
    val acc = flattenGraphNode(graphNode, 0)
    acc
      .map { case (level, name) =>
        " " * level + s"- ${name}"
      }
      .mkString("\n")

  override def generate[T <: MarkdownNode](node: T): Either[Throwable, String] =
    (for {
      graph  <- Try(buildGraph(node))
      result <- Try(printGraph(graph))
    } yield result).toEither
