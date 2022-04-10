package pl.muninn

import pl.muninn.markdown.common.Configuration.DefaultConfiguration
import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

@main
def Main(args: String*): Unit =

  given Configuration = DefaultConfiguration.notSafeInserting.notEscapingLiterals

//  val title = partial.h1(m"test")
//
//  val otherMd = md {
//    add(title)
//    p(m"huuraay ${b("test")}")
//    p("test")
//  }
//
////    println(printUnsafe(otherMd))
//
  val m = md {
    val title = h1(i(m"test")).partial
    add(title)
    hr
    b {
      "test"
    }
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
//        li {
//          m"test"
//          ul {
//            li("test")
//          }
//        }
      }
    }
    ul {
      li(m"test")
      li(m"test2")
    }
  }

//  print(generateGraphUnsafe(otherMd))
  print(generateGraphUnsafe(m))

//  print(generateUnsafe(m))
//  print(generateUnsafe(otherMd))

//  val listMd = md {
//    p {
//      b(m"test4")
//      ul {
//        li("test")
//        li("test")
//        ul {
//          li("test")
//          li("test")
//          li("test")
//        }
//      }
//    }
//  }

//  print(generateGraphUnsafe(listMd))

end Main
