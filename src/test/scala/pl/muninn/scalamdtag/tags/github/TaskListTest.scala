package pl.muninn.scalamdtag.tags.github

import pl.muninn.scalamdtag.tags.MarkdownText
import pl.muninn.scalamdtag.test.MarkdownSpec

class TaskListTest extends MarkdownSpec {

  "TaskList" should "render properly" in {
    TaskList(List(
      Task(true, MarkdownText("test1")),
      Task(false, MarkdownText("test2")),
      Task(true, MarkdownText("test3")),
      Task(false, MarkdownText("test4")),
    )).md shouldBe
      """- [x] test1
        |- [ ] test2
        |- [x] test3
        |- [ ] test4
        |""".stripMargin
  }

}
