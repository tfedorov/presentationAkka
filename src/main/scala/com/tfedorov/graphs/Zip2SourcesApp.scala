package com.tfedorov.graphs

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ClosedShape
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph, Sink, Source, ZipWith}

import scala.io.StdIn

object Zip2SourcesApp extends App {

  private implicit val system: ActorSystem = ActorSystem("simple-streams")

  val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
    import GraphDSL.Implicits._
    val in1 = Source(1 to 10)
    val in2 = Source(101 to 110)
    val out = Sink.foreach(println)

    val zipped = builder.add(ZipWith[String, String, String]("(" + _ + ") zipped (" + _ + ")"))

    val fStart = Flow[Int].map("Source:" + _ + "~>  input ~>")
    val fFinal = Flow[String].map(_ + "final~>")

    in1 ~> fStart ~> zipped.in0
    /*             */ zipped.out ~> fFinal ~> out
    in2 ~> fStart ~> zipped.in1


    ClosedShape

  })

  g.run()
  StdIn.readLine()
  system.terminate()
}
