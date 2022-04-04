package pl.muninn.markdown

sealed trait MarkdownNode:

  def name: String = this.getClass.getSimpleName

  infix def +(value: MarkdownNode): MarkdownNode = value // hack for better composition in context functions
end MarkdownNode

object MarkdownNode:
  trait Span  extends MarkdownNode
  trait Block extends MarkdownNode
end MarkdownNode
