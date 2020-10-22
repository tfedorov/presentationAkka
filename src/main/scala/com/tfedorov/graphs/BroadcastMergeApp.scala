package com.tfedorov.graphs

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ClosedShape
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, Merge, RunnableGraph, Sink, Source}

import scala.io.StdIn

object BroadcastMergeApp extends App {

  private implicit val system: ActorSystem = ActorSystem("simple-streams")

  val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
    import GraphDSL.Implicits._
    val in = Source(1 to 10)
    val out = Sink.foreach(println)

    val bcast = builder.add(Broadcast[String](2))

    val merge = builder.add(Merge[String](2))

    val fStart = Flow[Int].map("Source:" + _ + "~>  input ~>")
    val fTop = Flow[String].map(_ + "top   ~>")
    val fBottom = Flow[String].map(_ + "bottom~>")
    val fFinal = Flow[String].map(_ + "final~>")

    in ~> fStart ~> bcast ~> fTop ~> merge ~> fFinal ~> out
    /*           */ bcast ~> fBottom ~> merge
    ClosedShape
  })

  g.run()
  StdIn.readLine()
  system.terminate()
}
