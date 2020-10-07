package com.tfedorov.json

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives
import akka.stream.Materializer
import com.tfedorov.json.Data.{Item, Order}
import spray.json.DefaultJsonProtocol

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

class JsonHandler extends Directives with SprayJsonSupport with DefaultJsonProtocol {
  implicit val itemFormat = jsonFormat2(Item)
  implicit val orderFormat = jsonFormat1(Order)

  def requestHandler(implicit mat: Materializer): HttpRequest => HttpResponse = {
    case HttpRequest(GET, Uri.Path("/item"), _, _, _) =>
      HttpResponse(entity = HttpEntity(
        ContentTypes.`application/json`, itemFormat.write(Data.randomItem).prettyPrint))

    case HttpRequest(POST, Uri.Path("/item"), _, entity, _) => {
      HttpResponse(entity = entity)
    }
    case HttpRequest(GET, Uri.Path("/order"), _, _, _) =>
      HttpResponse(entity = HttpEntity(
        ContentTypes.`application/json`, orderFormat.write(Data.randomOrder).prettyPrint))

    case HttpRequest(POST, Uri.Path("/order"), _, entity, _) =>
      HttpResponse(entity = entity)

    case r: HttpRequest =>
      r.discardEntityBytes() // important to drain incoming HTTP Entity stream
      HttpResponse(404, entity = "Unknown resource!")
  }
}
