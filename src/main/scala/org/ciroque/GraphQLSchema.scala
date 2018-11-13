package org.ciroque

import org.ciroque.models.About
import sangria.schema.{Field, IntType, ListType, ObjectType, Schema, StringType, fields}

object GraphQLSchema {
  val AboutType = ObjectType[Unit, About](
    "About",
    fields[Unit, About](
      Field("name", StringType, resolve = _.value.name),
      Field("version", StringType, resolve = _.value.version),
    )
  )

  val QueryType = ObjectType(
    "Query",
    fields[Any, Unit](
      Field("about", AboutType, resolve = _ => About())
    )
  )

  val SchemaDefinition = Schema(QueryType)
}