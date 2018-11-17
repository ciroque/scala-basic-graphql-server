package org.ciroque.resolvers
import org.ciroque.models.Lift

object LiftResolver extends SeqResolver[Lift] {
  override def apply(args: SeqArgs) = {
    println(s"args: $args")
    List()
  }
}
