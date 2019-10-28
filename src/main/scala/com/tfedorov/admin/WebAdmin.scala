package com.tfedorov.admin

import java.net.URL
import java.util.jar.Manifest

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object WebAdmin extends App {

  private implicit val system: ActorSystem = ActorSystem("my-system")
  private implicit val materializer: ActorMaterializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  private implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  private val PORT = 8080

  private lazy val readMANIFEST: String = {
    val enumeration = Thread.currentThread.getContextClassLoader.getResources("META-INF/MANIFEST.MF")
    val url: URL = enumeration.nextElement()
    val manifest: Manifest = new Manifest(url.openStream)
    manifest.getMainAttributes.toString
  }

  val route =
    path("admin") {
      get {
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, readMANIFEST))
      }
    }

  val bindingFuture = Http().bindAndHandle(route, "localhost", PORT)

  println(s"Server online at http://localhost:$PORT/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture.flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

}
