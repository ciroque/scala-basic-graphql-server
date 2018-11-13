package org.ciroque

import org.ciroque.models.{About, Lift, Trail}
import sangria.schema.{Field, IntType, ListType, ObjectType, Schema, StringType, fields}
import org.ciroque.resolvers._

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
      Field("name", StringType, resolve = _.value.name, description = Some("This is the name of the Lift, duh.")),
      Field("capacity", IntType, resolve = _.value.capacity)
    )
  )

  val QueryType = ObjectType(
    "Query",
    fields[Any, Unit](
      Field("about", ListType(AboutType), resolve = c => AboutResolver(c)),
      Field("allLifts", ListType(LiftType), resolve = c => LiftResolver(c)),
      Field("allTrails", ListType(TrailType), resolve = c => TrailResolver(c))
    )
  )

  val SchemaDefinition = Schema(QueryType)
}
