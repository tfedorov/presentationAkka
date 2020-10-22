package com.tfedorov.graphs

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.SinkShape
import akka.stream.scaladsl.{Flow, GraphDSL, Keep, Merge, Partition, Sink, Source}

import scala.io.StdIn
import scala.util.Random

object PartitionerApp extends App {

  private val random = new Random()
  private val steps = "✂" :: "✊" :: "-" :: Nil

  final def getRandom: String = steps(random.nextInt(3))

  private def isWinner(player1: String, player2: String): Boolean = (player1, player2) match {
    case ("✂", "-") => true
    case ("✊", "✂") => true
    case ("-", "✊") => true
    case _ => false
  }

  private implicit val system: ActorSystem = ActorSystem("simple-streams")

  val gameGraph = GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
    import GraphDSL.Implicits._

    def winnerPartitioner(steps: (String, String)): Int = if (isWinner(steps._1, steps._2)) 0 else 1

    val isWinnerPart = builder.add(new Partition[(String, String)](2, winnerPartitioner, true))

    val merge = builder.add(Merge[String](2))

    val player2Step = Flow[String].map((_, getRandom))
    val remove2Step = Flow[(String, String)].map(_._1)

    val finish = Flow[(String, String)].map("final result" + _.toString())

    val out = Sink.foreach(println)

    merge ~> player2Step ~> isWinnerPart ~> finish ~> out
    merge <~ remove2Step <~ isWinnerPart

    SinkShape(merge.in(1))
  }

  val inputSource = Source(getRandom :: getRandom :: getRandom :: Nil)
  val r = inputSource.toMat(Sink.fromGraph(gameGraph))(Keep.both)
  r.run()
  StdIn.readLine()
  system.terminate()
}
