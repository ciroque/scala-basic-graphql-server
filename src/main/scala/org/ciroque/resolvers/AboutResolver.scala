package org.ciroque.resolvers

import org.ciroque.models.About

object AboutResolver extends Resolver[About] {
  override def apply(context: Any): Seq[About] = List(About())
}
