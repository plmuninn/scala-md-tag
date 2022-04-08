package pl.muninn.markdown

import pl.muninn.markdown.MarkdownContext.AnyMarkdownFragment
import pl.muninn.markdown.MarkdownFragment.BlockFragment

sealed trait MarkdownNode:

  def name: String = this.getClass.getSimpleName

  infix def +(value: MarkdownNode): MarkdownNode = value // hack for better composition in context functions
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
