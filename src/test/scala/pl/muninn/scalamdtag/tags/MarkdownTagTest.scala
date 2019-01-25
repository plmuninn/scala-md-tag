package pl.muninn.scalamdtag.tags

import pl.muninn.scalamdtag.test.MarkdownSpec

class MarkdownTagTest extends MarkdownSpec {
  behavior of "Any tag"

  it should "add string properly" in {
    val result = Bold("test") + "test2"
    result match {
      case MarkdownFragment(tags) =>
        tags.size shouldBe 2
        tags.toList should contain theSameElementsInOrderAs Seq(
          Bold("test"),
          MarkdownText("test2")
        )
      case _ => fail("Wrong type of result")
    }
  }

  it should "add other tag properly" in {
    val result = Bold("test") + Italic("test")
    result match {
      case MarkdownFragment(tags) =>
        tags.size shouldBe 2
        tags.toList should contain theSameElementsInOrderAs Seq(
          Bold("test"),
          Italic("test")
        )
      case _ => fail("Wrong type of result")
    }
  }

  it should "be added to tag properly" in {
    val result = Italic("test") :: Bold("test2")
    result match {
      case MarkdownFragment(tags) =>
        tags.size shouldBe 2
        tags.toList should contain theSameElementsInOrderAs Seq(
          Italic("test"),
          Bold("test2")
        )
      case _ => fail("Wrong type of result")
    }
  }

  it should "be added to string properly" in {
    val result = "test" :: Bold("test2")
    result match {
      case MarkdownFragment(tags) =>
        tags.size shouldBe 2
        tags.toList should contain theSameElementsInOrderAs Seq(
          MarkdownText("test"),
          Bold("test2")
        )
      case _ => fail("Wrong type of result")
    }
  }

  "Fragment" should "be concatenated properly" in {
    val first = MarkdownFragment(List(
      Bold("test1"),
      Italic("test2")
    ))

    val second = MarkdownFragment(List(
      Bold("test3"),
      Italic("test4")
    ))

    val result = first ++ second

    result match {
      case MarkdownFragment(tags) =>
        tags.size shouldBe 4
        tags.toList should contain theSameElementsInOrderAs Seq(
          Bold("test1"),
          Italic("test2"),
          Bold("test3"),
          Italic("test4")
        )
      case _ => fail("Wrong type of result")
    }
  }

  "Fragment" should "add string properly" in {
    val result = MarkdownFragment(List(
      Bold("test1"),
      Italic("test2")
    )) + "test3"

    result match {
      case MarkdownFragment(tags) =>
        tags.size shouldBe 3
        tags.toList should contain theSameElementsInOrderAs Seq(
          Bold("test1"),
          Italic("test2"),
          MarkdownText("test3"),
        )
      case _ => fail("Wrong type of result")
    }
  }

  "Fragment" should "be added string properly" in {
    val result = "test1" :: MarkdownFragment(List(
      Bold("test2"),
      Italic("test3")
    ))

    result match {
      case MarkdownFragment(tags) =>
        tags.size shouldBe 3
        tags.toList should contain theSameElementsInOrderAs Seq(
          MarkdownText("test1"),
          Bold("test2"),
          Italic("test3"),
        )
      case _ => fail("Wrong type of result")
    }

  }

  it should "add fragment correctly" in {
    val result = Bold("test1") + MarkdownFragment(List(Bold("test2"), Italic("test3")))

    result match {
      case MarkdownFragment(tags) =>
        tags.size shouldBe 3
        tags.toList should contain theSameElementsInOrderAs Seq(
          Bold("test1"),
          Bold("test2"),
          Italic("test3"),
        )
      case _ => fail("Wrong type of result")
    }

  }

  it should "be added fragment correctly" in {
    val result = Bold("test1") :: MarkdownFragment(List(Bold("test2"), Italic("test3")))

    result match {
      case MarkdownFragment(tags) =>
        tags.size shouldBe 3
        tags.toList should contain theSameElementsInOrderAs Seq(
          Bold("test1"),
          Bold("test2"),
          Italic("test3"),
        )
      case _ => fail("Wrong type of result")
    }
  }
}
