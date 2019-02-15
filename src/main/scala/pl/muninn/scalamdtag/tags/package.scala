package pl.muninn.scalamdtag

package object tags {

  import Renderer._
  import MarkdownRenderOps._

  type Markdown = MarkdownTag

  private[scalamdtag] trait MarkdownTag {
    self =>

    val rendered: String = self.render

    def md: String = rendered

    def isMultiline: Boolean

    def canBeInSameLine: Boolean

    def shouldEndWithNewLine: Boolean

    def ::[T <: MarkdownTag](value: T): MarkdownTag = value.concat(this)

    def ::(value: String): MarkdownTag = MarkdownText(value).concat(this)

    def +(value: String): MarkdownTag = this.concat(MarkdownText(value))

    def +[T <: MarkdownTag](value: T): MarkdownTag = this.concat(value)

    def concat[T <: MarkdownTag](value: T): MarkdownTag =
      (this, value) match {
        case (MarkdownFragment(actualTags), MarkdownFragment(valueTags)) => MarkdownFragment(actualTags ++ valueTags)
        case (MarkdownFragment(actualTags), v)                           => MarkdownFragment(actualTags ++ Iterable(v))
        case (v, MarkdownFragment(valueTags))                            => MarkdownFragment(Iterable(v) ++ valueTags)
        case (_, _)                                                      => MarkdownFragment(Iterable(this) ++ Iterable(value))
      }

    def ++(value: MarkdownFragment): MarkdownFragment = this match {
      case MarkdownFragment(values) => MarkdownFragment(values ++ value.tags)
      case _                        => MarkdownFragment(Iterable(this) ++ value.tags)
    }
  }

}
