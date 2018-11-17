package org.ciroque.resolvers

trait SeqResolver[T] {
  def apply(args: SeqArgs): Seq[T]
}

trait Resolver[T] {
  def apply(): T
}

case class SeqArgs(id: Option[String], limit: Option[Int], offset: Option[Int], filters: Option[Map[String, Any]])
object SeqArgs{
  def apply(): SeqArgs = SeqArgs(None, None, None, None)
  def apply(id: Option[String]): SeqArgs = SeqArgs(id, None, None, None)
  def apply(id: Option[String], limit: Int, offset: Int): SeqArgs = SeqArgs(id, Some(limit), Some(offset), None)
  def apply(id: Option[String], limit: Int, offset: Int, filters: Map[String, Any]): SeqArgs = SeqArgs(id, Some(limit), Some(offset), Some(filters))
}
