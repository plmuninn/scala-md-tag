//> using scala "3.1"
//> using lib "pl.muninn::scala-md-tag:1.0.0"

import pl.muninn.markdown.Markdown.{*, given}

val markdown = md {
  h1("Test tile")
  p {
    m"test file"
  }
}