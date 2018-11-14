package org.ciroque

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import sangria.ast.Document
import sangria.execution.{ErrorWithResolver, Executor, QueryAnalysisError}
import sangria.parser.QueryParser
import spray.json.{JsObject, JsString, JsValue}
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import sangria.marshalling.sprayJson._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object GraphQLServer {
  def endpoint(requestJSON: JsValue)(implicit ec: ExecutionContext): Route = {
    val JsObject(fields) = requestJSON
    val JsString(query) = fields("query")
    QueryParser.parse(query) match {
      case Success(queryAst) =>
        val operation = fields.get("operationName") collect {
          case JsString(op) => op
        }

        val variables = fields.get("variables") match {
          case Some(obj: JsObject) => obj
          case _ => JsObject.empty
        }

        complete(executeGraphQLQuery(queryAst, operation, variables))

      case Failure(error: Throwable) =>
        val body = JsObject(
          "message" -> JsString("Something went horribly horribly wrong..."),
          "error" -> JsString(error.getMessage()),
          "tag" -> JsString("Come back tomorrow, Indiana Jones.")
        )
        complete(BadRequest, body)
    }
  }

  private def executeGraphQLQuery(query: Document, operation: Option[String], vars: JsObject)(implicit ec: ExecutionContext)= {
    Executor.execute(
      GraphQLSchema.SchemaDefinition,
      query,
      Context(),
      variables = vars,
      operationName = operation
    ).map(OK -> _)
      .recover {
        case error: QueryAnalysisError => BadRequest -> error.resolveError
        case error: ErrorWithResolver => InternalServerError -> error.resolveError
      }
  }
}
