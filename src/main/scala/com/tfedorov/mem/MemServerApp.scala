package com.tfedorov.mem

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.server.Directives.{as, complete, decodeRequest, entity, get, onComplete, path}
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import akka.stream.{ActorMaterializer, Materializer}

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Success, Try}

object MemServerApp extends App {

  implicit val actorSystem: ActorSystem = ActorSystem("Sys")
  implicit val materializer: Materializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = materializer.executionContext

  val httpsFlow: Flow[(HttpRequest, NotUsed), (Try[HttpResponse], NotUsed), Http.HostConnectionPool] =
    Http().newHostConnectionPoolHttps[NotUsed]("ronreiter-meme-generator.p.mashape.com")

  def generateFlow: Flow[HttpRequest, HttpResponse, NotUsed] = Flow[HttpRequest].map(_ => Mem.random())
    .map(str => (MemRequestMaker.makeRequest(str), NotUsed))
    .via(httpsFlow)
    .map(_._1.get)

  val host = "localhost"
  val port = 80

  val bindingFuture = Http().bindAndHandle(generateFlow, host, port)

  println(s"curl -XGET 'http://$host:$port/memGenerate'")
}
