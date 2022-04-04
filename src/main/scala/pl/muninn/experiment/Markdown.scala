//package pl.muninn.markdown
//
//import scala.collection.mutable.ArrayBuffer
//import pl.muninn.markdown.BasicMarkdown.BasicBlockNode.ListNode.ListElement
//
//import scala.annotation.targetName
//
//
//
////object BasicMarkdown:
////
////   trait BasicSpanNode extends MarkdownNode.Span
////
////    object BasicSpanNode:
////
////        case class Bold() extends BasicSpanNode with Fragment:
////            override def print:String = s"**${printed.mkString(" ")}**"
////
////        case class Italic() extends BasicSpanNode with Fragment:
////            override def print:String = s"_${printed.mkString(" ")}_"
////
////        case class Strikethrough() extends BasicSpanNode with Fragment:
////            override def print:String = s"~~${printed.mkString(" ")}~~"
////
////        case class Code(value:Text) extends BasicSpanNode:
////            override def print:String = s"`${value.print}`"
////
////        case class Link(alt:Text, link:Text, title:Option[Text]) extends BasicSpanNode:
////            override def print:String =
////                title match
////                case Some(value) => s"""[${alt.print}](${link.print} "${value.print}")"""
////                case None => s"[${alt.print}](${link.print})"
////
////        case class Image(alt:Option[Text], link:Text, title:Option[Text]) extends BasicSpanNode:
////             override def print:String = s"![${alt.map(_.print).getOrElse("")}](${link.print}${title.map(value => s""" "${value.print}"""").getOrElse("")})"
////    end BasicSpanNode
////
////
////    trait BasicBlockNode extends MarkdownNode.Block
////
////    object BasicBlockNode:
////
////        case object BreakLine extends BasicBlockNode:
////             override def print:String = "  \n"
////
////        case object HorizontalLine extends BasicBlockNode:
////            override def print:String = "---\n"
////
////        case class Heading(level:Int) extends BasicBlockNode with Fragment:
////            override def print:String = ("#" * level) + " " + printed.mkString(" ") + "\n"
////
////        case class Paragraph() extends BasicBlockNode with Fragment:
////              override def print:String = "\n" + printed.mkString(" ") + "\n"
////
////        case class ListNode(listType:ListNode.ListType) extends BasicBlockNode with MarkdownFragment[ListNode.ListElement]:
////              override def print:String = values.zipWithIndex.map {
////                    case (element:MarkdownNode, index:Int) =>
////                        val prefix =
////                            listType match
////                            case ListNode.ListType.Unordered => "*"
////                            case ListNode.ListType.Ordered  => s"$index"
////                        s"$prefix ${element.print}"
////              }.mkString("\n")
////
////
////        object ListNode:
////
////            type ListNodeFragment = MarkdownFragment[ListNode.ListElement]
////
////            enum ListType:
////                case Ordered, Unordered
////            case class ListElement() extends BasicSpanNode with Fragment
////        end ListNode
////
////    end BasicBlockNode
////
////end BasicMarkdown
//
//trait Markdown:
//
//
//    def markdown(init: BasicContextFn) =
//        given fragment:Fragment = MarkdownFragment
//        given conversion:StringConversion = magneticStringToTextConversion(using fragment)
//        init(using fragment)
//        fragment
//
//    def add(markdownNode: MarkdownNode)(using md:Fragment) = md += markdownNode
//
//    def br(using md:Fragment) = md += BreakLine
//    def br = BreakLine
//    def hr(using md:Fragment) = md += HorizontalLine
//    def text(value:String)(using md:Fragment) = md += Text(value)
//    def code(value:String)(using md:Fragment) = md += Code(value)
//    def a(alt:String, link:String)(using md:Fragment) = md += Link(alt, link, None)
//    def a(alt:String, link:String, title:String)(using md:Fragment) = md += Link(alt, link, Some(title))
//    def img(link:String)(using md:Fragment) = md += Image(None, link, None)
//    def img(alt:String, link:Text)(using md:Fragment) = md += Image(Some(alt), link, None)
//    def img(alt:String, link:String, title:String)(using md:Fragment) = md += Image(Some(alt), link, Some(title))
//    def b(init: BasicContextFn)(using md:Fragment) = addToFragmentAndInitContext(Bold(), init)
//    def i(init: BasicContextFn)(using md:Fragment) = addToFragmentAndInitContext(Italic(), init)
//    def s(init: BasicContextFn)(using md:Fragment) = addToFragmentAndInitContext(Strikethrough(), init)
//    def h(init: BasicContextFn)(using md:Fragment) = addToFragmentAndInitContext(Heading(1),init)
//    def h1(init: BasicContextFn)(using md:Fragment) = addToFragmentAndInitContext(Heading(1),init)
//    def h2(init: BasicContextFn)(using md:Fragment) = addToFragmentAndInitContext(Heading(2),init)
//    def h3(init: BasicContextFn)(using md:Fragment) = addToFragmentAndInitContext(Heading(3),init)
//    def h4(init: BasicContextFn)(using md:Fragment) = addToFragmentAndInitContext(Heading(4),init)
//    def h5(init: BasicContextFn)(using md:Fragment) = addToFragmentAndInitContext(Heading(5),init)
//    def h6(init: BasicContextFn)(using md:Fragment) = addToFragmentAndInitContext(Heading(6),init)
//    def p(init: => BasicContextFn)(using md:Fragment) =
//      val paragraph = Paragraph()
//      given fragment: Fragment = paragraph
//      init
//      md.add(paragraph)
//    end p
//
//    def ul(init: ListNode.ListNodeFragment ?=> Unit)(using md:Fragment) =
//        val listNode = ListNode(ListNode.ListType.Unordered)
//        given ulList:ListNode.ListNodeFragment = listNode
//        init(using ulList)
//        md.add(listNode)
//
//    def ol(init: ListNode.ListNodeFragment ?=> Unit)(using md:Fragment) =
//        val listNode = ListNode(ListNode.ListType.Ordered)
//        given ulList:ListNode.ListNodeFragment = listNode
//        init(using ulList)
//        md.add(listNode)
//
//    def li(init: => BasicContextFn)(using ul:ListNode.ListNodeFragment) =
//        val element = ListNode.ListElement()
//        given fragment:Fragment = element
//        given conversion:StringConversion = magneticStringToTextConversion(using fragment)
//        init(using fragment)
//        ul.add(element)
//
//end Markdown
