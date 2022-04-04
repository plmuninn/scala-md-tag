package pl.muninn.markdown

import pl.muninn.markdown.MarkdownContext.AnyMarkdownFragment
import pl.muninn.markdown.MarkdownNode.Span
import pl.muninn.markdown.basic.span.Text

object MarkdownStringContext:

  implicit class TextOps(private val sc: StringContext) extends AnyVal:
    def m(args: Any*)(using md: AnyMarkdownFragment): Span =
      val containsMarkdown = args.exists {
        case (value: MarkdownNode) => true
        case _                     => false
      }
      if (containsMarkdown) throw new RuntimeException("You can't use markdown node in `m` interpolation")
      val values = StringContext.standardInterpolator((value: String) => value, args, sc.parts)
      md.add(Text(values))
    end m
  end TextOps

end MarkdownStringContext
