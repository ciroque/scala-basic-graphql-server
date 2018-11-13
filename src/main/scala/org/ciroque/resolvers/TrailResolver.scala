package org.ciroque.resolvers

import org.ciroque.models.Trail

object TrailResolver extends Resolver[Trail] {
  override def apply(context: Any) = List(
    Trail("ABCD1234", "Whisper Hill", "Easy", "OPEN"),
    Trail("ABCD1235", "Anon", "Easy", "OPEN"),
    Trail("ABCD1236", "Brake Nexxx", "Impossible", "OPEN"),
    Trail("ABCD1237", "Slope", "Easy", "CLOSED")
  )
}