import pl.muninn.scalamdtag._

markdown(
  h1("Scala markdown tags"),
  p(
    "Simple library to generate Markdown Tags - inspired by", a("scalatags", "https://github.com/lihaoyi/scalatags"), br,
    frag("It uses plain scala (", b("no cats, shapeless etc."), ")"),
  ),
  p(
    "Library was design to help generate markdown automatically, have similar API as any html library.", br,
    "During generation of markdown syntax, it try to minimize any problems that could end with not properly displayed document.", br,
    "Library try to guarantee:",
    ul(
      "Simple html like API",
      "Properly displayed document",
      "Simple way to generate and compose markdown"
    )
  ),
  p(
    "Supported tags:",
    table(
      ("Tag name", "Description / function", "Function name"),
      List(
        ("Text", "Representation of text fragment", code("text")),
        ("Horizontal line", "Generate horizontal line" :: code("---"), code("hr")),
        ("Break line", "Symbol of breaking line", code("br")),
        ("Fragment", "Represent concatenation of elements", code("frag") + "," + code("::")),
        ("Header", "Header like" :: code("# Header"), code("h") + code("h1") + code("h2") + "etc."),
        ("Italic", "Italic" :: code("_italic_"), code("i")),
        ("Bold", "Bold" :: code("**bold**"), code("b")),
        ("Code", "Cod \\`some code\\`", code("code")),
        ("Paragraph / Markdown", "Paragraph like html p.", code("p") + "," + code("markdown")),
        ("Image", "Generate markdown image", code("img")),
        ("Link", "Generate markdown link", code("a")),
        ("Code block", "Generate code block", code("codeBlock")),
        ("Unordered list", "Generate unsorted list", code("ul")),
        ("Ordered list", "Generate ordered list", code("ol")),
        ("Table", "Generate markdown table", code("table")),
      )
    )
  ),
  p(
    "Example of usage, you can find ", a("here.", "./src/Readme.sc")
  )
).md