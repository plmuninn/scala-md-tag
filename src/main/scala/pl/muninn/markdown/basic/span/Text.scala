package pl.muninn.markdown.basic.span

import pl.muninn.markdown.Configuration
import pl.muninn.markdown.MarkdownContext.AnyMarkdownFragment
import pl.muninn.markdown.MarkdownNode.Span

import scala.collection.mutable.ArrayBuffer

case class Text(value: String) extends Span

object Text:

  private val escapedCharacters: ArrayBuffer[String] = ArrayBuffer("""\""", "`", "*", "_", "{", "}", "[", "]", "(", ")", "#", "+", "-", ".", "!")

  def escaped(value: String, configuration: Configuration): Text =
    if !configuration.shouldEscapeLiterals then new Text(value)
    else
      val escapedValue =
        escapedCharacters.foldLeft(value) { case (text, character) =>
          text.replace(character, """\""" + character)
        }
      new Text(escapedValue)

  object Partial:
    def text(value: String)(using configuration: Configuration): Text = Text.escaped(value, configuration)

  def text(value: String)(using md: AnyMarkdownFragment, configuration: Configuration) = md += Partial.text(value)

  def print(value: Text): String = value.value
