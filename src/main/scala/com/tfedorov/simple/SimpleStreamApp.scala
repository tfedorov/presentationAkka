package com.tfedorov.simple

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Keep, RunnableGraph, Sink, Source}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}

object SimpleStreamApp extends App {

  println("SimpleStreamApp is an simple example of the Graph: source + flow + sink")
  private implicit val system: ActorSystem = ActorSystem("simple-streams")
  private implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  private val source = Source(1 to 10)
  private val flow = Flow[Int].map(_ * 10)
  private val sink = Sink.fold[Int, Int](0)(_ + _)

  private val runnableGraph: RunnableGraph[Future[Int]] =
    source.via(flow).toMat(sink)(Keep.right)

  private val waitResult: Future[Int] = runnableGraph.run()
  waitResult.foreach(println)

}