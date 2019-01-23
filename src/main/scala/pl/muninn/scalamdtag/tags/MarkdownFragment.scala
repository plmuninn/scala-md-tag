package pl.muninn.scalamdtag.tags

case class MarkdownFragment(tags: Iterable[MarkdownTag]) extends MarkdownTag {
  val isMultiline = rendered.lines.length > 1
  val canBeInSameLine = isMultiline
  val shouldEndWithNewLine = isMultiline
}

object MarkdownFragment {

  implicit val renderMarkdownFragment: Renderer[MarkdownFragment] = {
    case MarkdownFragment(tags) =>
      tags.foldLeft("") {
        case (acc, tag) =>
          var rendered = {
            var text = tag.rendered

            if (tag.shouldEndWithNewLine && text.last != '\n') text += '\n'

            tag match {
              case _: Table =>
                text.take(2) match {
                  case "\n\n" => text
                  case firstTwo =>
                    if (firstTwo.last == '\n' || firstTwo.head == '\n') '\n' + text else "\n\n" + text
                }
              case _ => text
            }
          }

          if (acc.isEmpty) rendered
          else {

            if (tag.canBeInSameLine && (acc.last != '\n' && acc.last != ' ' && rendered.head != ' '))
              rendered = ' ' + rendered

            if (tag.isMultiline && acc.last != '\n' && rendered.head != '\n') rendered = "\n" + rendered

            acc + rendered
          }
      }
  }
}
