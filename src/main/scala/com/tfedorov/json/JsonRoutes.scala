package com.tfedorov.json

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{Directives, Route}
import com.tfedorov.json.Data.{Item, Order}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

private[json] class JsonRoutes extends Directives with SprayJsonSupport with DefaultJsonProtocol {
  implicit val itemFormat: RootJsonFormat[Item] = jsonFormat2(Item)
  implicit val orderFormat: RootJsonFormat[Order] = jsonFormat1(Order)


  private val getItemRoute: Route = get {
    complete(Data.randomItem)
  }

  private val postItemRoute: Route = post {
    entity(as[Item]) { item: Item => // will unmarshal JSON to Item
      complete(item + 1)
    }
  }
  private val getOrderRoute: Route = get {
    complete(Data.randomOrder)
  }

  private val postOrderRoute: Route = post {
    entity(as[Order]) { order: Order => // will unmarshal JSON to Order
      complete(order + 1)
    }
  }

  private[json] val route: Route =
    concat(pathPrefix("item") {
      concat(getItemRoute, postItemRoute)
    }, pathPrefix("order") {
      concat(getOrderRoute, postOrderRoute)
    })

}


