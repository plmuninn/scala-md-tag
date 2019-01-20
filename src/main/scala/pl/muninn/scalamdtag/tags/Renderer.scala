package pl.muninn.scalamdtag.tags

trait Renderer[T] {
  def render(value: T): String
}

object Renderer {
  def apply[A](implicit instance: Renderer[A]): Renderer[A] = instance

  implicit class RendererOps[T](value: T) {
    def render(implicit renderer: Renderer[T]): String = renderer.render(value)
  }

}
