package pl.muninn.scalamdtag.tags.github

import pl.muninn.scalamdtag.tags.{BlockMarkdownTag, Renderer, TextMarkdownTag}

case class TaskList(tasks: Iterable[Task]) extends BlockMarkdownTag

object TaskList {
  implicit val taskListRenderer: Renderer[TaskList] = {
    case TaskList(tasks) =>
      tasks.foldLeft("") {
        case (acc, Task(selected, value)) => acc + s"- [${if (selected) "x" else " "}] ${value.rendered}\n"
      }
  }
}

case class Task(selected: Boolean, value: TextMarkdownTag)
