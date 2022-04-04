package pl.muninn.markdown.basic.span

import pl.muninn.markdown.MarkdownContext.AnyMarkdownFragment
import pl.muninn.markdown.MarkdownNode.Span

import scala.collection.mutable.ArrayBuffer

case class Text(value: String) extends Span

object Text:

  private val escapedCharacters: ArrayBuffer[String] = ArrayBuffer("""\""", "`", "*", "_", "{", "}", "[", "]", "(", ")", "#", "+", "-", ".", "!")

  def apply(value: String): Text =
    val escapedValue =
      escapedCharacters.foldLeft(value) { case (text, character) =>
        text.replace(character, """\""" + character)
      }
    new Text(escapedValue)

  def text(value: String)(using md: AnyMarkdownFragment) = md += Text(value)

  def print(value: Text): String = value.value
