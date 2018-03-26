package com.epam.maf

import com.epam.maf.Table._

import scala.collection.mutable
import scala.io.StdIn.readLine

object DayStepMaker {
  def makeDayStep() = {

    setCandidates4Vote()

    readLine()

    if (onlyOneCandidate)
      removePlayer(candidates.head)
    else
      removePlayer(doJudge())

  }

  private def setCandidates4Vote(): Unit = {
    val dayVoteCandidates = availablePlayers.map(_.makeCandidate(availablePlayers)).slice(0, 3)
    setCandidates(dayVoteCandidates)

    candidates.foreach(e => println("candidate: " + e.number))
  }

  private def doJudge(): Player = {
    val dayVotes = availablePlayers.map(_.makeVote(Table.candidates))

    dayVotes.foreach(println)

    return calcuateJudges(dayVotes)
  }

  private def calcuateJudges(dayVotes: mutable.Buffer[Vote]): Player = {
    val calcVote = dayVotes.groupBy(_.whom).map(numberCallVote => (numberCallVote._1, numberCallVote._2.size)).toList.sortWith((l, r) => l._2 > r._2)
    //calcVote.foreach(println)
    calcVote.head._1
  }
}
