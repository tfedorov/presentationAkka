package com.tfedorov.mem

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.stream.scaladsl.Flow

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object MemServerApp extends App {

  private final val SERVICE = "apimeme.com"
  println(s"The MemServerApp gets acronyms and procces them via API: $SERVICE")

  implicit val actorSystem: ActorSystem = ActorSystem("Sys")
  val httpsFlow = Http().cachedHostConnectionPool[NotUsed](SERVICE)

  val generateFlow =
    Flow[HttpRequest].map(_ => MemID.random())
      .map(mem => (RequestMaker.makeRequest(mem), NotUsed))
      .via(httpsFlow)
      .map(_._1.get)

  final val host = "localhost"
  final val port = 8080

  val bindingFuture = Http().newServerAt(host, port).bindFlow(generateFlow)

  println(s"curl -XGET 'http://$host:$port/'")
  println("Press RETURN to stop...")

  StdIn.readLine() // let it run until user presses return
  private implicit val executionContext: ExecutionContextExecutor = actorSystem.dispatcher
  bindingFuture.flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => actorSystem.terminate()) // and shutdown when done
}
