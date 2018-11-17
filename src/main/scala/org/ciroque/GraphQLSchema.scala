package org.ciroque

import org.ciroque.models.{About, Lift, Trail}
import sangria.schema.{Argument, Field, IntType, ListType, ObjectType, OptionInputType, Schema, StringType, fields}
import org.ciroque.resolvers._

object GraphQLSchema {
  val IdArg = Argument("id", OptionInputType(StringType), description = "Search by the known ID of an entity")
  val LimitArg = Argument("limit", OptionInputType(IntType), defaultValue = 20)
  val OffsetArg = Argument("offset", OptionInputType(IntType), defaultValue = 0)

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
      Field("about", AboutType, resolve = c => AboutResolver()),
      Field(
        "allLifts",
        ListType(LiftType),
        resolve = c => LiftResolver(SeqArgs(c arg IdArg, c arg LimitArg, c arg OffsetArg)),
        arguments = IdArg :: LimitArg :: OffsetArg :: Nil
      ),
      Field("allTrails", ListType(TrailType), resolve = c => TrailResolver(SeqArgs()))
    )
  )

  val SchemaDefinition = Schema(QueryType)
}
