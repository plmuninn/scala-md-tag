package pl.muninn

import pl.muninn.markdown.Configuration
import pl.muninn.markdown.Configuration.DefaultConfiguration
import pl.muninn.markdown.Markdown.{*, given}

@main
def Main(args: String*): Unit =

  given Configuration = DefaultConfiguration.notSafeInserting.notEscapingLiterals

  val title = partial.h1(m"test")

  val otherMd = md {
    add(title)
    p(m"huuraay ${b("test")}")
  }

//    println(printUnsafe(otherMd))

  val m = md {
    val title = h1(i(m"test")).partial
    add(title)
    hr
    p(m"test1")
    p(m"test2")
    b("test3")
    br
    p {
      b(m"test4")
      ul {
        li("test")
        li("test")
        li("test")
        li("test")
      }
    }
    ul {
      li(m"test")
      li(m"test2")
    }
  }

  print(generateGraphUnsafe(otherMd))
//  print(generateGraphUnsafe(m))

//  print(generateUnsafe(m))
//  print(generateUnsafe(otherMd))

end Main
