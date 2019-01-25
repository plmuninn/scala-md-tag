# Scala markdown tags

Simple library to generate Markdown Tags - inspired by [scalatags](https://github.com/lihaoyi/scalatags)  
It uses plain scala ( **no cats, shapeless etc.** )
# Overview

Library tries to guarantee:
* Simple html like API
* Properly displayed document
* Simple way to generate and compose markdown

# Getting started

Add to yours `build.sbt` :
```
resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies += "pl.muninn" %% "scala-md-tag" % "0.2"
```
Then you need to only add in your code:
```scala
import pl.muninn.scalamdtag._
```
And you are good to go.
# Example

Example of usage, you can find [here.](./src/Readme.sc)

Supported tags:

| Tag name             | Description / function              | Function name      |
| -------------------- | ----------------------------------- | ------------------ |
| Text                 | Representation of text fragment     | `text`             |
| Horizontal line      | Generate horizontal line `---`      | `hr`               |
| Break line           | Symbol of breaking line             | `br`               |
| Fragment             | Represent concatenation of elements | `frag` , `::`      |
| Header               | Header like `# Header`              | `h` `h1` `h2` etc. |
| Italic               | Italic `_italic_`                   | `i`                |
| Bold                 | Bold `**bold**`                     | `b`                |
| Code                 | Cod \`some code\`                   | `code`             |
| Paragraph / Markdown | Paragraph like html p.              | `p` , `markdown`   |
| Image                | Generate markdown image             | `img`              |
| Link                 | Generate markdown link              | `a`                |
| Code block           | Generate code block                 | `codeBlock`        |
| Unordered list       | Generate unsorted list              | `ul`               |
| Ordered list         | Generate ordered list               | `ol`               |
| Table                | Generate markdown table             | `table`            |

# Concatenation

Markdown tags provides three way of concatenation of tags / strings.  
* `::` was added to fix problem with `+` during concatenation with strings. Use it if you want to concatenate string with tag
* `+` will add tags to each other and creat new fragment
* `++` is to merge two fragments


# What is in plan to 1.0.0
- [ ] Create decoding markdown text to scala-md-structures


