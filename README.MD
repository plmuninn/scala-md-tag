# Scala markdown tags

Simple library to generate Markdown Tags - inspired by [scalatags](https://github.com/lihaoyi/scalatags)  
It uses plain scala ( **no cats, shapeless etc.** )

Library was design to help generate markdown automatically, have similar API as any html library.  
During generation of markdown syntax, it try to minimize any problems that could end with not properly displayed document.  
Library try to guarantee:
* Simple html like API
* Properly displayed document
* Simple way to generate and compose markdown


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


Example of usage, you can find [here.](./src/Readme.sc)

