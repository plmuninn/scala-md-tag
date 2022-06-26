package pl.muninn.scalamdtag.tags

case class MarkdownFragment(tags: Iterable[MarkdownTag]) extends MarkdownTag {
  val isMultiline = rendered.lines.toArray.length > 1
  val canBeInSameLine = isMultiline
  val shouldEndWithNewLine = isMultiline
}

object MarkdownFragment {

  implicit val renderMarkdownFragment: Renderer[MarkdownFragment] = {
    case MarkdownFragment(tags) =>
      tags
        .filter(_.rendered.nonEmpty)
        .foldLeft("") {
          case (acc, tag) =>
            var rendered = {
              var text = tag.rendered

              if (tag.shouldEndWithNewLine && !text.lastOption.contains('\n')) text += '\n'

              tag match {
                case _: Table =>
                  text.take(2) match {
                    case "\n\n" => text
                    case firstTwo =>
                      if (firstTwo.lastOption.contains('\n') || firstTwo.headOption.contains('\n')) "\n" + text
                      else "\n\n" + text
                  }
                case _ => text
              }
            }

            if (acc.isEmpty) {
              rendered
            } else if (rendered.isEmpty) {
              acc
            } else {

              if (tag.canBeInSameLine && (!acc.lastOption.contains('\n') && !acc.lastOption.contains(' ') && !rendered.headOption
                    .contains(' ')))
                rendered = " " + rendered

              if (tag.isMultiline && !acc.lastOption.contains('\n') && !rendered.headOption.contains('\n'))
                rendered = "\n" + rendered

              acc + rendered
            }
        }
  }
}
