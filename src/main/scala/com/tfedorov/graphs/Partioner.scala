package com.tfedorov.graphs

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ClosedShape
import akka.stream.scaladsl.{Flow, GraphDSL, Merge, Partition, RunnableGraph, Sink, Source}

import scala.util.Random

object Partioner extends App {


  val random = new Random()
  val steps = "✂" :: "✊" :: "-" :: Nil

  def getRandom: String = {
    val r = steps(random.nextInt(3))
    //println("random=" + r)
    r
  }

  private implicit val system: ActorSystem = ActorSystem("simple-streams")

  val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
    import GraphDSL.Implicits._


    def isWinner(player1: String, player2: String): Boolean = (player1, player2) match {
      case ("✂", "-") => true
      case ("✊", "✂") => true
      case ("-", "✊") => true
      case _ => false
    }

    def winnerPartitioner(steps: (String, String)): Int = {
      if (isWinner(steps._1, steps._2)) 0 else 1
    }

    val winner = builder.add(new Partition[(String, String)](2, winnerPartitioner, true))

    val merge = builder.add(Merge[String](2))

    val player2Step = Flow[String].map(in => {
      val r = (in, getRandom)
     // print(r)
      r
    })
    val replay = Flow[(String, String)].map { t =>
     // print("replay")
      t._1
    }
    val finish = Flow[(String, String)].map {
      "final result" + _.toString()
    }

    val in = Source(getRandom :: getRandom :: getRandom :: Nil)
    val out = Sink.foreach(println)

    in ~> merge.in(0)
    /*  */ merge.out ~> player2Step ~> winner.in
    /*                              */ winner.out(0) ~> finish ~> out
    /*                              */ winner.out(1) ~> replay ~> merge.in(1)
    ///* */  merge.in(1) <~ replay

    ClosedShape
  })

  g.run()
  //StdIn.readLine()
  system.terminate()
}
