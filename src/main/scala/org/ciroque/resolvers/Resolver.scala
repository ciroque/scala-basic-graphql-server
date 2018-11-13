package org.ciroque.resolvers

trait Resolver[T] {
  def apply(context: Any): Seq[T]
}
