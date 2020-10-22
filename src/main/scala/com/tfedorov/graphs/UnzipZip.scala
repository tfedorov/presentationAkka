package com.tfedorov.graphs

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ClosedShape
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph, Sink, Source, UnzipWith, ZipWith}

import scala.io.StdIn

object UnzipZip extends App {

  private implicit val system: ActorSystem = ActorSystem("simple-streams")

  val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
    import GraphDSL.Implicits._
    val in = Source(1 to 10)
    val out = Sink.foreach(println)

    val unZipped = builder.add(UnzipWith[String, String, String](in => ("unz1:(" + in + ")", "unz2:(" + in + ")")))
    val zipped = builder.add(ZipWith[String, String, String]((in1: String, in2: String) => "(" + in1 + ") zipped (" + in2 + ")"))

    val fStart = Flow[Int].map("Source:" + _ + "~>  input ~>")
    val fTop = Flow[String].map(_ + "top   ~>")
    val fBottom = Flow[String].map(_ + "bottom~>")
    val fFinal = Flow[String].map(_ + "final~>")

    in ~> fStart ~> unZipped.in
    /*            */ unZipped.out0 ~> fTop /*  */ ~> zipped.in0
    /*            */ unZipped.out1 ~> fBottom /**/~> zipped.in1
    /*                                            */ zipped.out ~> fFinal ~> out
    ClosedShape
  })

  g.run()
  StdIn.readLine()
  system.terminate()
}
