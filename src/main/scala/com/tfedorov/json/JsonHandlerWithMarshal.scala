package com.tfedorov.json

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import com.tfedorov.json.Data.{Item, Order}
import spray.json.{DefaultJsonProtocol, RootJsonFormat, _}

import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}

class JsonHandlerWithMarshal extends Directives with SprayJsonSupport with DefaultJsonProtocol {

  implicit val itemFormat: RootJsonFormat[Item] = jsonFormat2(Item)
  implicit val orderFormat: RootJsonFormat[Order] = jsonFormat1(Order)

  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  import scala.concurrent.duration._

  def requestHandler(implicit mat: Materializer): HttpRequest => HttpResponse = {
    case HttpRequest(GET, Uri.Path("/item"), _, _, _) =>
      HttpResponse(entity = HttpEntity(
        ContentTypes.`application/json`, Data.randomItem.toJson.prettyPrint))

    case HttpRequest(POST, Uri.Path("/item"), _, originalEntity, _) => {
      val newItemF: Future[Item] = Unmarshal(originalEntity).to[Item].map(_ + 2)
      val newEntityF: Future[ResponseEntity] = Marshal(newItemF).to[ResponseEntity]
      val newEntity: ResponseEntity = Await.result(newEntityF, 1 seconds)
      HttpResponse(entity = newEntity)
    }

    case HttpRequest(GET, Uri.Path("/order"), _, _, _) =>
      HttpResponse(entity = HttpEntity(
        ContentTypes.`application/json`, Data.randomOrder.toJson.prettyPrint))

    case HttpRequest(POST, Uri.Path("/order"), _, originalEntity, _) => {
      val newOrderF: Future[Order] = Unmarshal(originalEntity).to[Order].map(_ + 2)
      val newEntityF: Future[ResponseEntity] = Marshal(newOrderF).to[ResponseEntity]
      val newEntity: ResponseEntity = Await.result(newEntityF, 1 seconds)
      HttpResponse(entity = newEntity)
    }

    case r: HttpRequest =>
      r.discardEntityBytes() // important to drain incoming HTTP Entity stream
      HttpResponse(404, entity = "Unknown resource!")
  }
}
