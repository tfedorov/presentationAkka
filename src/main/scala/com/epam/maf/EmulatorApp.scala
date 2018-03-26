package com.epam.maf

import com.epam.maf.Table._

import scala.collection.mutable
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

    val dayVoteCandidates = availablePlayers.map(_.makeCandidate(availablePlayers)).slice(0, 3)
    setCandidates(dayVoteCandidates)

    candidates.foreach(e => println("candidate: " + e.number))
    readLine()

    if (candidates.distinct.size == 1)
      removePlayer(candidates.head)
    else
      removePlayer(doJudge(Table.candidates))

    readLine()

  }

  private def doJudge(dayCandidates: mutable.Buffer[Player]): Player = {
    val dayVotes = availablePlayers.map(_.makeVote(dayCandidates))

    dayVotes.foreach(println)

    return calcuateJudges(dayVotes)
  }

  private def calcuateJudges(dayVotes: mutable.Buffer[Vote]): Player = {
    val calcVote = dayVotes.groupBy(_.whom).map(numberCallVote => (numberCallVote._1, numberCallVote._2.size)).toList.sortWith((l, r) => l._2 > r._2)
    //calcVote.foreach(println)
    calcVote.head._1
  }

  def makeNightStep(): Unit = {
    println("***********")
    println("Night in the city")
    val peaceResidents = availablePlayers.filterNot(_.isMafia)
    val nightVictim = peaceResidents(random.nextInt(peaceResidents.size))
    removePlayer(nightVictim)
    println("Night killed - " + nightVictim)

  }

  makeNightStep()

  makeDayStep()

  makeNightStep()

  makeDayStep()

}
