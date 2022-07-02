package pl.muninn.markdown.common.basic.block

import pl.muninn.markdown.common.{Configuration, MarkdownFragment, MarkdownNode}
import pl.muninn.markdown.common.MarkdownFragment.{BlockFragment, SpanFragment}
import pl.muninn.markdown.common.basic.block.TaskList.TaskListFragment
import pl.muninn.markdown.common.MarkdownContext.{SpanContextFn, SpanWithParentContextFn, createSpanContext}
import pl.muninn.markdown.common.MarkdownNode.Block

import scala.annotation.targetName

class TaskList extends TaskListFragment

object TaskList:
  trait TaskListFragment extends MarkdownFragment[Task] with Block

  case class Task(var checked: Boolean) extends SpanFragment

  type TaskListContextFn = TaskListFragment ?=> Task
  type TaskContextFn     = SpanWithParentContextFn[Task]

  def createTaskListPartialContext(list: TaskList, init: TaskListContextFn): TaskList =
    given fragment: TaskListFragment = list
    init(using fragment)
    list

  object Partial:
    def tasks(init: TaskListContextFn): TaskList = createTaskListPartialContext(TaskList(), init)

    def task(checked: Boolean, init: TaskContextFn)(using configuration: Configuration): Task = createSpanContext(Task(checked), init)
  end Partial

  def tasks(init: TaskListContextFn)(using md: BlockFragment, configuration: Configuration) = md += Partial.tasks(init)

  def task(init: TaskContextFn)(using md: TaskListFragment, configuration: Configuration) = md += Partial.task(false, init)

  def check(using task: Task) = task.checked = true

  @targetName("add_task")
  def add(task: Task)(using md: TaskListFragment, configuration: Configuration) = md += task

  @targetName("add_many_tasks")
  def add(task: Task*)(using md: TaskListFragment, configuration: Configuration) = md.addMany(task)

  def print(node: TaskList, printBodyF: MarkdownNode => String): String =
    val tasks = node.values.collect({ case task: Task => task })
    tasks
      .map { task =>
        s"- [${if task.checked then "x" else " "}] ${printBodyF(task)}"
      }
      .mkString("\n")
