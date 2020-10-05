package com.tfedorov.mem

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.stream.scaladsl.Flow

object MemServerApp extends App {

  implicit val actorSystem: ActorSystem = ActorSystem("Sys")

  val httpsFlow = Http().cachedHostConnectionPool[NotUsed]("apimeme.com")

  val generateFlow =
    Flow[HttpRequest].map(_ => Mem.random())
      .map(mem => (MemRequestMaker.makeRequest(mem), NotUsed))
      .via(httpsFlow)
      .map(_._1.get)

  val host = "localhost"
  val port = 8080

  val bindingFuture = Http().newServerAt(host, port).bindFlow(generateFlow)

  println(s"curl -XGET 'http://$host:$port/'")
}
