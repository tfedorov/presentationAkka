package com.tfedorov.json

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.stream.Materializer
import com.tfedorov.json.Data.{Item, Order}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}

object JsonConsoleApp extends App with SprayJsonSupport with DefaultJsonProtocol {

  implicit val itemFormat: RootJsonFormat[Item] = jsonFormat2(Item)
  implicit val orderFormat: RootJsonFormat[Order] = jsonFormat1(Order)

  val order = Order(Item("abc", 1) :: Nil)

  private implicit val abc: Unmarshaller[Order, String] = new Unmarshaller[Order, String] {
    override def apply(value: Order)(implicit ec: ExecutionContext, materializer: Materializer): Future[String] = {
      Future {
        orderFormat.write(value).prettyPrint
      }
    }
  }

  private implicit val actorSystem: ActorSystem = ActorSystem("my-system")
  private implicit val executionContext: ExecutionContextExecutor = actorSystem.dispatcher

  val resultF: Future[String] = Unmarshal(order).to[String]

  val result = Await.result(resultF, 6 seconds)
  println(result)

  println(orderFormat.write(order).prettyPrint)
  actorSystem.terminate()
}
