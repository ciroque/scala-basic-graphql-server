package org.ciroque

import org.ciroque.models.{About, Lift, Trail}
import sangria.schema.{Field, IntType, ListType, ObjectType, Schema, StringType, fields}

object GraphQLSchema {
  val AboutType: ObjectType[Unit, About] = ObjectType[Unit, About](
    "About",
    fields[Unit, About](
      Field("name", StringType, resolve = _.value.name),
      Field("version", StringType, resolve = _.value.version),
      Field("license", StringType, resolve = _.value.license),
    )
  )

  val TrailType: ObjectType[Unit, Trail] = ObjectType[Unit, Trail](
    "Trail",
    fields[Unit, Trail](
      Field("id", StringType, resolve = _.value.id),
      Field("name", StringType, resolve = _.value.name),
      Field("difficulty", StringType, resolve = _.value.difficulty),
      Field("status", StringType, resolve = _.value.status)
    )
  )

  val LiftType: ObjectType[Unit, Lift] = ObjectType[Unit, Lift](
    "Lift",
    fields[Unit, Lift](
      Field("id", StringType, resolve = _.value.id),
      Field("name", StringType, resolve = _.value.name),
      Field("capacity", IntType, resolve = _.value.capacity)
    )
  )

  val QueryType = ObjectType(
    "Query",
    fields[Any, Unit](
      Field("about", AboutType, resolve = _ => About()),
      Field("allLifts", ListType(LiftType), resolve = _ => List(Lift("ABCD1234", "Whisper Hill Lift", 27, "OPEN"))),
      Field("allTrails", ListType(TrailType), resolve = _ => List(Trail("ABCD1234", "Whisper Hill", "Easy", "OPEN")))
    )
  )

  val SchemaDefinition = Schema(QueryType)
}
