package com.tfedorov.json

import akka.actor.ActorSystem
import akka.http.scaladsl.Http

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object JsonGeneratorApp extends App {

  private val HOST = "localhost"
  private val PORT = 8080
  private implicit val actorSystem: ActorSystem = ActorSystem("my-system")
  private val bindingFuture = Http().newServerAt(HOST, PORT).bindFlow(new JsonRoutes().route)
  //private val bindingFuture = Http().newServerAt(HOST, PORT).bindSync(new JsonHandler().requestHandler)

  println(s"curl http://$HOST:$PORT/item")
  println(s"""curl -X POST -H 'Content-Type: application/json' -d '{"name":"abc", "id": 42}' http://$HOST:$PORT/item""")
  println(s"curl http://$HOST:$PORT/order")
  println(s"""curl -X POST -H 'Content-Type: application/json' -d '{"items":[{"name":"abc", "id": 42},{"name":"2", "id": 2}]}' http://$HOST:$PORT/order""")
  println("Press RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  private implicit val executionContext: ExecutionContextExecutor = actorSystem.dispatcher
  bindingFuture.flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => actorSystem.terminate()) // and shutdown when done

}
