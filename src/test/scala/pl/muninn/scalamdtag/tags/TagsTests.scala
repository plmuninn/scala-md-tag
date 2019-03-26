package pl.muninn.scalamdtag.tags

import pl.muninn.scalamdtag.tags.BaseTags.{BreakLine, HorizontalLine}
import pl.muninn.scalamdtag.test.MarkdownSpec

class TagsTests extends MarkdownSpec {

  "MdText" should "render without anything" in {
    MarkdownText("test").md shouldBe "test"
  }

  "Heading" should "render properly for evert level" in {
    Heading("test", level = 1).md shouldBe "# test"
    Heading("test", level = 2).md shouldBe "## test"
    Heading("test", level = 3).md shouldBe "### test"
  }

  "BreakLine" should "render break line" in {
    BreakLine().md shouldBe "  "
  }

  "HorizontalLine" should "render horizontal line" in {
    HorizontalLine().md shouldBe "---"
  }

  "Italic" should "render value in italic signature" in {
    Italic("test").md shouldBe "_test_"
  }

  "Bold" should "render value in bold signature" in {
    Bold("test").md shouldBe "**test**"
  }

  "Strikethrough" should "render value in strikethrough signature" in {
    Strikethrough("test").md shouldBe "~~test~~"
  }

  "Code" should "render value in code signature" in {
    Code("test").md shouldBe "`test`"
  }

  "Image" should "render proper image tag" in {
    Image(None, "https://placekitten.com/200/300", None).md shouldBe "![](https://placekitten.com/200/300)"
    Image(Some("Kitten"), "https://placekitten.com/200/300", None).md shouldBe "![Kitten](https://placekitten.com/200/300)"
    Image(None, "https://placekitten.com/200/300", Some("awesome kitty")).md shouldBe """![](https://placekitten.com/200/300 "awesome kitty")"""
    Image(Some("Kitten"), "https://placekitten.com/200/300", Some("awesome kitty")).md shouldBe """![Kitten](https://placekitten.com/200/300 "awesome kitty")"""
  }

  "Link" should "render proper link tag" in {
    Link("Kitten", "https://placekitten.com/200/300", None).md shouldBe "[Kitten](https://placekitten.com/200/300)"
    Link("Kitten", "https://placekitten.com/200/300", Some("awesome kitty")).md shouldBe """[Kitten](https://placekitten.com/200/300 "awesome kitty")"""
  }

  "BlockQuotes" should "render block quotes properly" in {
    BlockQuotes("something\nsomething else").md shouldBe
      """> something
        |> something else""".stripMargin
  }

  "CodeBlock" should "render properly" in {
    CodeBlock(Some("javascript"), "console.log(\"test\");\nlet = 1 + 1;").md shouldBe
      """```javascript
        |console.log("test");
        |let = 1 + 1;
        |```""".stripMargin

    CodeBlock(None, "console.log(\"test\");\nlet = 1 + 1;").md shouldBe
      """```
        |console.log("test");
        |let = 1 + 1;
        |```""".stripMargin
  }

  "UnsortedList" should "render unsorted list properly" in {
    UnsortedList(Iterable(
      "test1",
      "test2",
      UnsortedList(Iterable(
        "test3",
        "test4"
      ))
    )).md shouldBe
      """* test1
        |* test2
        |   * test3
        |   * test4""".stripMargin
  }

  "List" should "render list properly" in {
    MarkdownList(Iterable(
      "test1",
      "test2",
      MarkdownList(
        Iterable(
          "test3",
          "test4"
        )
      )
    )).md shouldBe
      """1. test1
        |2. test2
        |   1. test3
        |   2. test4""".stripMargin
  }

  "Paragraph" should "render properly" in {
    MarkdownParagraph(Iterable("test value", "test 2 value")).rendered shouldBe
      """
        |test value test 2 value
        |""".stripMargin
  }

  "Table" should "render table properly" in {
    Table(
      columns = Iterable("test", "test2", "super long column"),
      rows = Iterable(),
      alignment = Some(Right(List(TableAlignment.Left, TableAlignment.Center, TableAlignment.Right)))
    ).md shouldBe
      """| test | test2 | super long column |
        @| :--- | :---: | ----------------: |
        @""".stripMargin('@')

    Table(
      columns = Iterable("test", "test2", "super long column"),
      rows = Iterable(
        Iterable(1, "1", Some(Code("something"))),
        Iterable("1", "1", None)
      ),
      alignment = Some(Right(List(TableAlignment.Left, TableAlignment.Center, TableAlignment.Right)))
    ).md shouldBe
      """| test | test2 | super long column |
        @| :--- | :---: | ----------------: |
        @| 1    | 1     | `something`       |
        @| 1    | 1     |                   |""".stripMargin('@')

    Table(
      columns = Iterable("test", "test2", "super long column"),
      rows = Iterable(
        Iterable(1, "1", Some(Code("something"))),
        Iterable("1", "1", None)
      ),
      alignment = None
    ).md shouldBe
      """| test | test2 | super long column |
        @| ---- | ----- | ----------------- |
        @| 1    | 1     | `something`       |
        @| 1    | 1     |                   |""".stripMargin('@')

    Table(
      columns = Iterable("1", "2", "3"),
      rows = Iterable(
        Iterable(1, "1"),
        Iterable("1", "1", None)
      ),
      alignment = Some(Left(TableAlignment.Center))
    ).md shouldBe
      """| 1     | 2     | 3     |
        @| :---: | :---: | :---: |
        @| 1     | 1     |
        @| 1     | 1     |       |""".stripMargin('@')
  }

  "Md" should "render properly" in {
    val Markdown =
      MarkdownFragment(Iterable(
        Heading("Test header", 1),
        MarkdownParagraph(Iterable(
          "some text",
          Bold(Italic("other test")),
          "and some test",
          UnsortedList(Iterable(
            "test1",
            "test2",
            UnsortedList(Iterable(
              "test3",
              "test4",
            ))
          ))
        )),
        MarkdownParagraph(Iterable("test")),
        "test", "with space",
        Table(
          columns = Iterable("1", "2", "3"),
          rows = Iterable(
            Iterable(1, "1"),
            Iterable("1", "1", None)
          ),
          alignment = Some(Left(TableAlignment.Center))
        )
      ))

    Markdown.md shouldBe
      """# Test header
        @
        @some text **_other test_** and some test
        @* test1
        @* test2
        @   * test3
        @   * test4
        @
        @
        @test
        @test with space
        @
        @| 1     | 2     | 3     |
        @| :---: | :---: | :---: |
        @| 1     | 1     |
        @| 1     | 1     |       |
        @""".stripMargin('@')
  }

  "Md" should "render properly with empty values" in {
    MarkdownFragment(Iterable(
      "test",
      ""
    ))
  }
}
