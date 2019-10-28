package com.tfedorov.admin

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor
import scala.io.{Source, StdIn}

object WebAdmin extends App {

  private implicit val system: ActorSystem = ActorSystem("my-system")
  private implicit val materializer: ActorMaterializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  private implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  private val PORT = 8080

  private lazy val readMANIFEST: String = Source.fromResource("META-INF/MANIFEST.MF").getLines.mkString("\n")

  val route =
    path("admin") {
      get {
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, readMANIFEST))
      }
    }

  val bindingFuture = Http().bindAndHandle(route, "localhost", PORT)

  println(s"Server online at http://localhost:$PORT/admin \nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture.flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

}
