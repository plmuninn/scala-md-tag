package pl.muninn.markdown

import SimpleMarkdown.{*, given}

class SimpleMarkdownSpec extends munit.FunSuite:
  test("SimpleMarkdown should allow and render every possible component using contexts") {
    md {
      h("test")
      h1("test")
      h2("test")
      h3("test")
      h4("test")
      h5("test")
      h6("test")
      text("test")
      div("test")
      blockquotes("test")
      br
      hr
      p("test")
      b("test")
      code("test")
      em("test")
      codeBlock("scala", "test")
      codeBlock("test")
      img("test")
      img("test", "test")
      img("test", "test", "test")
      i("test")
      a("test", "test")
      a("test", "test", "test")
      ul {
        li("test")
      }
      ol {
        li("test")
      }
    }
  }
  test("SimpleMarkdown should allow and render every possible component using partials") {
    val h           = partial.h("test")
    val h1          = partial.h1("test")
    val h2          = partial.h2("test")
    val h3          = partial.h3("test")
    val h4          = partial.h4("test")
    val h5          = partial.h5("test")
    val h6          = partial.h6("test")
    val text        = partial.text("test")
    val div         = partial.div("test")
    val blockquotes = partial.blockquotes("test")
    val br          = partial.br(false)
    val hr          = partial.hr
    val p           = partial.p("test")
    val b           = partial.b("test")
    val code        = partial.code("test")
    val em          = partial.em("test")
    val codeBlock1  = partial.codeBlock("scala", "test")
    val codeBlock2  = partial.codeBlock("test")
    val img         = partial.img("test")
    val img1        = partial.img("test", "test")
    val img2        = partial.img("test", "test", "test")
    val i           = partial.i("test")
    val a1          = partial.a("test", "test")
    val a2          = partial.a("test", "test", "test")
    val ul          = partial.ul { li("test") }
    val ol          = partial.ol { li("test") }
    md {
      add(h)
      add(h1)
      add(h2)
      add(h3)
      add(h4)
      add(h5)
      add(h6)
      add(text)
      add(div)
      add(blockquotes)
      add(br)
      add(hr)
      add(p)
      add(b)
      add(code)
      add(em)
      add(codeBlock1)
      add(codeBlock2)
      add(img)
      add(img1)
      add(img2)
      add(i)
      add(a1)
      add(a2)
      add(ul)
      add(ol)
    }
  }
