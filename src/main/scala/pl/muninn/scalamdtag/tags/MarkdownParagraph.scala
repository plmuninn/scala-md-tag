package pl.muninn.scalamdtag.tags

case class MarkdownParagraph(tags: Iterable[MarkdownTag]) extends BlockMarkdownTag {
  def toFragment: MarkdownFragment = MarkdownFragment(tags)
}

object MarkdownParagraph {
  implicit val renderMarkdownParagraph: Renderer[MarkdownParagraph] = {
    case paragraph: MarkdownParagraph => "\n" + paragraph.toFragment.rendered + "\n"
  }
}
