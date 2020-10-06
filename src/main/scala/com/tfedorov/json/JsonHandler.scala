package com.tfedorov.json

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives
import akka.stream.Materializer
import com.tfedorov.json.JsonGeneratorApp.{Item, Order}
import spray.json.DefaultJsonProtocol

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

class JsonHandler extends Directives with SprayJsonSupport with DefaultJsonProtocol {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global
  implicit val itemFormat = jsonFormat2(Item)
  implicit val orderFormat = jsonFormat1(Order)

  def requestHandler(implicit mat: Materializer): HttpRequest => HttpResponse = {
    case HttpRequest(GET, Uri.Path("/item"), _, _, _) =>

      HttpResponse(entity = HttpEntity(
        ContentTypes.`application/json`,
        """{"id":49,"name":"thing덷㦭⸔"}"""))

    case HttpRequest(POST, Uri.Path("/item"), _, entity, _) =>
      HttpResponse(entity = entity)

    case HttpRequest(POST, Uri.Path("/order"), _, entity, _) =>
      HttpResponse(entity = entity)

    case r: HttpRequest =>
      r.discardEntityBytes() // important to drain incoming HTTP Entity stream
      HttpResponse(404, entity = "Unknown resource!")
  }
}
