package com.tfedorov.simple

import akka.actor.ActorSystem
import akka.stream.ClosedShape
import akka.stream.scaladsl.{Broadcast, Concat, GraphDSL, RunnableGraph, Sink, Source, ZipWith}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}
import scala.io.StdIn

object CyclesApp extends App {

  private implicit val system: ActorSystem = ActorSystem("simple-streams")
  private implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  // WARNING! The graph below deadlocks!
  val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit b =>
    import GraphDSL.Implicits._

    val source = Source(1 to 10)

    val zip = b.add(ZipWith((left: Int, right: Int) => left))
    val bcast = b.add(Broadcast[Int](2))
    val concat = b.add(Concat[Int]())
    val start = Source.single(0)

    source ~> zip.in0
    zip.out.map { s => println(s); s } ~> bcast ~> Sink.ignore
    zip.in1 <~ concat <~ start
    /*      */ concat <~ bcast
    ClosedShape

  })

  g.run()
  StdIn.readLine()
  system.terminate()
}