//> using scala "3.1"
//> using lib "com.lihaoyi::pprint:0.7.3"
//> using lib "com.lihaoyi::os-lib:0.8.1"

import pl.muninn.markdown.common.MarkdownFragment.MarkdownDocument
import pl.muninn.markdown.Markdown.*
import os.*

def generateFiles(values: Map[String, MarkdownDocument]): Unit =
  values.foreach {
    case (path, markdown) =>
      val generatedMarkdown = generateUnsafe(markdown)
      val markdownPath = os.pwd / "docs" / path
      pprint.pprintln(s"Saving markdown $markdownPath")
      os.write.over(markdownPath, generatedMarkdown, createFolders = true)
      pprint.pprintln("Markdown saved:")
      pprint.pprintln(generatedMarkdown)
  }

