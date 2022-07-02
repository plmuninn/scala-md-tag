package pl.muninn

import pl.muninn.markdown.common.Configuration.DefaultConfiguration
import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownNode.Span
import pl.muninn.markdown.common.basic.block.Table.{ColumnAlignment, TableElement}

@main
def Main(args: String*): Unit =

  given Configuration = DefaultConfiguration().withEscapeLiterals(false).withSafeInserting(false).withTableStrictPrinting(false)

//  val otherMd = md {
//    h1 {
//      setId("id")
//      m"test"
//    }
//    p(m"huuraay ${b("test")}")
//    p("test")
//    tasks {
//      task {
//        check
//        m"test"
//      }
//      task("test")
//    }
//  }
//
//  println(generateUnsafe(otherMd))

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

//  val listMd = md {
//    ul {
//      li("123. test")
//      li("test2")
//      nested {
//        li("test3")
//        li("test4")
//        nested {
//          li("test5")
//        }
//      }
//    }
//  }

//  print(generateGraphUnsafe(listMd))
//  print(generateUnsafe(listMd))

//  val tableMd = md {
//    table {
//      setDefaultAlignment(ColumnAlignment.Left)
//      headers {
//        header(ColumnAlignment.Left)(m"test")
//        header(ColumnAlignment.Center)(m"test")
//        header(ColumnAlignment.Right)(m"test")
//      }
//      row {
//        col(m"test")
//        col(m"test123123123")
//        col(m"test123123123123")
//        col(m"test")
//      }
//      row {
//        col(m"test")
//        col(m"test22332")
//      }
//    }
//  }

//  print(generateGraphUnsafe(tableMd))
//  print(generateUnsafe(tableMd))

  case class Validator(name: String, forType: Span, compositionUsage: Span, implicitUsage: Span)

  val validators = List(
    Validator("", partial.text(""), partial.text(""), partial.text(""))
  )

  def mapValidatorToRow(validator: Validator): TableElement =
    partial.row {
      col(text(validator.name))
      col(add(validator.forType))
      col(add(validator.compositionUsage))
      col(add(validator.implicitUsage))
    }

  val markdownPartial = md {
    h1("Validators")
    p {
      m"Validators provided by library:"
      br
      table {
        headers {
          col("Name")
          col("For what type")
          col("Composition usage")
          col("Implicit usage")
        }
        validators.map(mapValidatorToRow).map(add).last
      }
    }
  }

  print(generateGraphUnsafe(markdownPartial))
  print(generateUnsafe(markdownPartial))

end Main
