package pl.muninn.markdown.common

import pl.muninn.markdown.common.MarkdownFragment.BlockFragment
import pl.muninn.markdown.common.MarkdownNode
import pl.muninn.markdown.common.MarkdownContext.AnyMarkdownFragment

sealed trait MarkdownNode:

  def name: String = this.getClass.getSimpleName

  infix def +[T <: MarkdownNode](value: T): T = value // hack for better composition in context functions
end MarkdownNode

object MarkdownNode:
  trait Span extends MarkdownNode
  object Span:
    extension (node: Span)(using md: AnyMarkdownFragment)
      def partial: Span =
        md.remove(node)
        node
  trait Block extends MarkdownNode
  object Block:
    extension (node: Block)(using md: BlockFragment)
      def partial: Block =
        md.remove(node)
        node
end MarkdownNode
