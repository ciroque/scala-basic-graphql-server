package org.ciroque.resolvers

trait SeqResolver[T] {
  def apply(context: Any): Seq[T]
}

trait Resolver[T] {
  def apply(context: Any): T
}
