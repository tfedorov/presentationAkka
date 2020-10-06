package com.tfedorov.json

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{Directives, Route}
import com.tfedorov.json.JsonGeneratorApp.{Item, Order}
import spray.json.DefaultJsonProtocol

import scala.util.Random

private[json] class JsonRoutes extends Directives with SprayJsonSupport with DefaultJsonProtocol {
  implicit val itemFormat = jsonFormat2(Item)
  implicit val orderFormat = jsonFormat1(Order) // contains List[Item]
  private[json] val route: Route =
    concat(pathPrefix("item") {
      concat(getItemRoute, postItemRoute)
    }, pathPrefix("order") {
      postOrderRoute
    })


  private val getItemRoute: Route = get {
    complete(Item("thing" + Random.nextString(3), Random.nextInt(100)))
  }
  private val postItemRoute: Route = post {
    entity(as[Item]) { item: Item => // will unmarshal JSON to Item
      complete(s"Item id=${item.id},  name=${item.name}\n")
    }
  }
  private val postOrderRoute: Route = post {
    entity(as[Order]) { order: Order => // will unmarshal JSON to Order
      val itemsCount = order.items.size
      val itemNames = order.items.map(_.name).mkString(", ")
      complete(s"Ordered $itemsCount items: $itemNames\n")
    }
  }

}


