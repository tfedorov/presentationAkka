package com.tfedorov.simple

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ClosedShape
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, Merge, RunnableGraph, Sink, Source}

import scala.io.StdIn

object GraphSimple extends App {

  private implicit val system: ActorSystem = ActorSystem("simple-streams")

  val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
    import GraphDSL.Implicits._
    val in = Source(1 to 10)
    val out = Sink.foreach(println)

    val bcast = builder.add(Broadcast[String](2))
    val merge = builder.add(Merge[String](2))

    val f1 = Flow[Int].map(_ + " f1~>")
    val f2 = Flow[String].map(_ + "f2~>")
    val f3 = Flow[String].map(_ + "f3~>")
    val f4 = Flow[String].map(_ + "f4~>")

    in ~> f1 ~> bcast ~> f2 ~> merge ~> f3 ~> out
    /*       */ bcast ~> f4 ~> merge
    ClosedShape
  })

  g.run()
  StdIn.readLine()
  system.terminate()
}
