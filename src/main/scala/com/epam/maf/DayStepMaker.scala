package com.epam.maf

import com.epam.maf.Table._

import scala.collection.mutable

object DayStepMaker {
  def makeDayJugment(): Unit = {

    val electedPlayer = doDayEllecion()
    removePlayer(electedPlayer)

  }

  def setCandidates4Vote(): Unit = {
    Table.candidates = randomizeList(availablePlayers).take(3)
  }

  private def doDayEllecion(): Player = {
    val dayVotes = availablePlayers.map(_.makeVote)

    dayVotes.foreach(println)

    return calcuateJudges(dayVotes)
  }

  private def calcuateJudges(dayVotes: Seq[Vote]): Player = {
    dayVotes.groupBy(_.whom)
      .map(numberCallVote => (numberCallVote._1, numberCallVote._2.size))
      .toList
      .sortWith((l, r) => l._2 > r._2).head._1

  }
}
