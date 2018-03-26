package com.epam.maf

import com.epam.maf.Table._

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.StdIn.readLine
import scala.util.Random

object EmulatorApp extends App {


  case class Vote(player: Player, whom: Player) {
    override def toString: String = s"${player.number} -> ${whom.number}"
  }

  val random = Random


  def makeDayStep() = {
    println("***********")
    println("Day in the city")
    candidates.clear()
    candidates ++= availablePlayers.map(_.makeCandidate(availablePlayers)).slice(0,3)

    Table.candidates.foreach(e => println("candidate: " + e.number))
    readLine()

    var killedPlayer: Player = null
    if (Table.candidates.distinct.size == 1)
      killedPlayer = Table.candidates.head
    else
      killedPlayer = doElection(Table.candidates)
    println("killed:" + killedPlayer)
    removePlayer(killedPlayer)
    readLine()

  }

  private def doElection(dayCandidates: mutable.Buffer[Player]) = {
    val dayVotes = availablePlayers.map(_.makeVote(dayCandidates))

    dayVotes.foreach(println)

    val calcVote = dayVotes.groupBy(_.whom).map(numberCallVote => (numberCallVote._1, numberCallVote._2.size)).toList.sortWith((l, r) => l._2 > r._2)
    //calcVote.foreach(println)
    val killedPlayer = calcVote.head._1
    killedPlayer
  }

  def makeNightStep(): Unit = {
    println("***********")
    println("Night in the city")
    while (true) {
      val candNume = random.nextInt(playersNumber)
      val candidate = availablePlayers(candNume)
      if (!candidate.isMafia) {
        removePlayer(candidate)
        println("killed - " + candidate)
        return
      }

    }
  }

  makeNightStep()

  makeDayStep()

  makeNightStep()

  makeDayStep()


}
