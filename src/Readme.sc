import pl.muninn.scalamdtag._

markdown(
  h1("Scala markdown tags"),
  p(
    "Simple library to generate Markdown Tags - inspired by", a("scalatags", "https://github.com/lihaoyi/scalatags"), br,
    "It uses plain scala (" :: b("no cats, shapeless etc.") :: ")",
  ),
  h1("Overview"),
  p("Library tries to guarantee:",
    ul(
      "Simple html like API",
      "Properly displayed document",
      "Simple way to generate and compose markdown"
    )
  ),
  h1("Getting started"),
  p(
    frag("Add to yours", code("build.sbt"), ":"),
    codeBlock(
      """resolvers ++= Seq(
        |  Resolver.sonatypeRepo("releases"),
        |  Resolver.sonatypeRepo("snapshots")
        |)
        |
        |libraryDependencies += "pl.muninn" %% "scala-md-tag" % "0.2"""".stripMargin),
    "Then you need to only add in your code:",
    codeBlock("scala", "import pl.muninn.scalamdtag._"),
    "And you are good to go."
  ),
  h1("Example"),
  p(
    "Example of usage, you can find ", a("here.", "./src/Readme.sc")
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
  h1("Concatenation"),
  p(
    "Markdown tags provides three way of concatenation of tags / strings.", br,
    ul(
      frag(code("::"), "was added to fix problem with", code("+"), "during concatenation with strings. Use it if you want to concatenate string with tag"),
      frag(code("+"), "will add tags to each other and creat new fragment"),
      frag(code("++"), "is to merge two fragments")
    )
  ),
  p(
    h1("What is in plan to 1.0.0"),
    taskList(
      task("Create decoding markdown text to scala-md-structures"),
    )
  )
).md