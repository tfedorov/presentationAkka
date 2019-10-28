package com.tfedorov.stream

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Keep, RunnableGraph, Sink, Source}

import scala.concurrent.{ExecutionContextExecutor, Future}

object SimpleStreamApp extends App {

  private implicit val system: ActorSystem = ActorSystem("simple-streams")
  private implicit val materializer: ActorMaterializer = ActorMaterializer()
  private implicit val context: ExecutionContextExecutor = materializer.executionContext

  val source = Source(1 to 10)
  val flow = Flow[Int].map(_ * 10)
  val sink = Sink.fold[Int, Int](0)(_ + _)

  val runnableGraph: RunnableGraph[Future[Int]] =
    source.via(flow).toMat(sink)(Keep.right)

  val waitRresult: Future[Int] = runnableGraph.run()
  waitRresult.foreach(println)

}