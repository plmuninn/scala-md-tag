package pl.muninn.markdown

import pl.muninn.markdown.MarkdownContext.AnyMarkdownFragment
import pl.muninn.markdown.MarkdownNode.{Block, Span}
import pl.muninn.markdown.basic.span.{Text, TextFragment}

object MarkdownStringContext:

  implicit class TextOps(private val sc: StringContext) extends AnyVal:

    def m(args: Any*)(using md: AnyMarkdownFragment, configuration: Configuration): Span =
      val containsBlockNodes = args.exists {
        case _: Block => true
        case _        => false
      }
      if containsBlockNodes then throw new RuntimeException("You can't use Block node in `m` interpolation")
      val containsSpanNodes = args.exists {
        case _: Span => true
        case _       => false
      }
      StringContext.checkLengths(args, sc.parts)
      if !containsSpanNodes then
        val values = StringContext.standardInterpolator((value: String) => value, args, sc.parts)
        md.add(Text.escaped(values, configuration))
      else
        val textFragment                     = TextFragment()
        val strings                          = sc.parts.iterator
        val expressions                      = args.iterator
        def safeAddToFragment(value: String) = if !(value.isEmpty || value.isBlank) then textFragment.add(Text.escaped(value, configuration))
        safeAddToFragment(strings.next())
        while expressions.hasNext do
          expressions.next() match
            case span: Span =>
              textFragment.add(span)
              // We need to remove it from parent context
              md.remove(span)
            case value => safeAddToFragment(value.toString)
          if strings.hasNext then safeAddToFragment(strings.next())
        md.add(textFragment)
    end m

    def mraw(args: Any*)(using md: AnyMarkdownFragment): Span =
      val containsMarkdown = args.exists {
        case _: MarkdownNode => true
        case _               => false
      }
      if containsMarkdown then throw new RuntimeException("You can't use markdown node in `mraw` interpolation")
      val values = StringContext.standardInterpolator((value: String) => value, args, sc.parts)
      md.add(Text(values))
    end mraw
  end TextOps

end MarkdownStringContext
