package com.epam.maf

import com.epam.maf.Table._

import scala.collection.mutable

object DayStepMaker {
  def makeDayJugment(): Unit = {
    if (onlyOneCandidate)
      return removePlayer(candidates.head)

    val electedPlayer = doDayEllecion()
    removePlayer(electedPlayer)

  }

  def setCandidates4Vote(): Unit = {
    val dayVoteCandidates = availablePlayers.map(_.makeCandidate(availablePlayers)).slice(0, 3)
    setCandidates(dayVoteCandidates)
  }

  private def doDayEllecion(): Player = {
    val dayVotes = availablePlayers.map(_.makeVote(Table.candidates))

    dayVotes.foreach(println)

    return calcuateJudges(dayVotes)
  }

  private def calcuateJudges(dayVotes: mutable.Buffer[Vote]): Player = {
    dayVotes.groupBy(_.whom)
      .map(numberCallVote => (numberCallVote._1, numberCallVote._2.size))
      .toList
      .sortWith((l, r) => l._2 > r._2).head._1

  }
}
