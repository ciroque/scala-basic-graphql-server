package org.ciroque.resolvers

import org.ciroque.models.About

object AboutResolver extends Resolver[About] {
  override def apply(args: SeqArgs): About = About()
}
