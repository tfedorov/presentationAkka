package com.tfedorov.web

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives.{complete, concat, get, path, pathPrefix}
import akka.http.scaladsl.server.Route

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object WebApp extends App {

  println("App creates the Web Application with routes:")
  private val HOST = "localhost"
  private val PORT = 8080

  private implicit val actorSystem: ActorSystem = ActorSystem("my-system")

  //val route: Flow[HttpRequest, HttpResponse, _] =
  private val route: Route =
    pathPrefix("META-INF") {
      concat(
        path("MANIFEST.MF") {
          get {
            complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, ManifestProcessor.content))
          }
        },
        get {
          complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, ManifestProcessor.manifestPath))
        }
      )
    }

  private val bindingFuture = Http().newServerAt(HOST, PORT).bindFlow(route)
  println(s"  http://$HOST:$PORT/META-INF path to the META-INF/MANIFEST.MF file")
  println(s"  http://$HOST:$PORT/META-INF/MANIFEST.MF content of the META-INF/MANIFEST.MF file")
  println("Press RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  private implicit val executionContext: ExecutionContextExecutor = actorSystem.dispatcher
  bindingFuture.flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => actorSystem.terminate()) // and shutdown when done

}
