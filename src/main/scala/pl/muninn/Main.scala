package pl.muninn

import pl.muninn.markdown.common.Configuration.DefaultConfiguration
import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.basic.block.Table.TableAlignment

@main
def Main(args: String*): Unit =

  given Configuration = DefaultConfiguration().withEscapeLiterals(false).withSafeInserting(false).withTableStrictPrinting(true)

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
//  val m = md {
//    val title = h1(i(m"test")).partial
//    add(title)
//    hr
//    b {
//      "test"
//    }
//    p(m"test1")
//    p(m"test2")
//    b("test3")
//    br
//    p {
//      b(m"test4")
//      ul {
//        li("test")
//        li("test")
//        li("test")
////        li {
////          m"test"
////          ul {
////            li("test")
////          }
////        }
//      }
//    }
//    ul {
//      li(m"test")
//      li(m"test2")
//    }
//  }

//  print(generateGraphUnsafe(otherMd))
//  print(generateGraphUnsafe(m))

//  print(generateUnsafe(m))
//  print(generateUnsafe(otherMd))

  val listMd = md {
    ul {
      li("test")
      li("test")
      nested {
        li("test")
        li("test")
        li("test")
      }
    }
  }

  print(generateGraphUnsafe(listMd))
//  print(generateUnsafe(listMd))

//  val tableMd = md {
//    table {
//      headers {
//        col(m"")
//      }
//      headers {
//        header(TableAlignment.Left)(m"test")
//        header(TableAlignment.Right)(m"test")
//        header(TableAlignment.Center)(m"test")
//      }
//      row {
//        col(m"test")
//        col(m"test")
//        col(m"test")
//      }
//    }
//  }
//
//  print(generateGraphUnsafe(tableMd))
//  print(generateUnsafe(tableMd))

end Main
