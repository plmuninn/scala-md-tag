package pl.muninn.scalamdtag

package object tags {

  import Renderer._
  import MarkdownRenderOps._

  private[scalamdtag] trait MarkdownTag {
    self =>

    val rendered: String = self.render

    def md: String = rendered

    def isMultiline: Boolean

    def canBeInSameLine: Boolean

    def shouldEndWithNewLine: Boolean

    def ::[T <: MarkdownTag](value: T): MarkdownTag = value + this

    def ::(value: String): MarkdownTag = MarkdownText(value) + this

    def +(value: String): MarkdownTag = this + MarkdownText(value)

    def +[T <: MarkdownTag](value: T): MarkdownTag =
      MarkdownFragment(Iterable(this) ++ Iterable(value))

    def ++(value: MarkdownFragment): MarkdownFragment = this match {
      case MarkdownFragment(values) => MarkdownFragment(values ++ value.tags)
      case _                        => MarkdownFragment(Iterable(this) ++ value.tags)
    }
  }

}
