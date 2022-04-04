package pl.muninn

import pl.muninn.markdown.Markdown.*

@main
def Main(args: String*): Unit =

  val otherMd = md {
    p(m"huuraay")
  }

//    println(printUnsafe(otherMd))

  val m = md {
    h1(i(m"test"))
    hr
    p(m"test1")
    p(m"test2")
    b("test3")
    br
    p {
      b(m"test4")
    }
    ul {
      li(m"test")
      li(m"test2")
    }
  }

  print(generateGraphUnsafe(m))
//  print(generateUnsafe(m))
//    println(printUnsafe(otherMd))

end Main
