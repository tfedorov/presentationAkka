package com.epam.maf

import com.epam.maf.Table._
import com.epam.maf.player.{Player, Vote}

object DayStepMaker {
  def makeDayJugment(): Unit = {

    val electedPlayer = doDayEllecion()
    removePlayer(electedPlayer)

  }

  def setCandidates4Vote(): Unit = {
    // val cand = Table.sherifCheck.filter(_.isMafia)
    if (!Table.checkedBlack().isEmpty) {
      val maph = Table.checkedBlack().head
      Table.candidates = maph :: maph.makeCandidate() :: Nil
    } else {
      Table.candidates = availablePlayers.map(_.makeCandidate()).take(3)
    }

  }

  private def doDayEllecion(): Player = {
    val dayVotes = availablePlayers.map(_.makeVote)

    dayVotes.foreach(println)

    return calcuateJudges(dayVotes)
  }

  private def calcuateJudges(dayVotes: Seq[Vote]): Player = {
    val calculated = dayVotes.groupBy(_.whom)
      .map(numberCallVote => (numberCallVote._1, numberCallVote._2.size))
      .toList
      .sortWith((l, r) => l._2 > r._2)
    calculated.foreach(p => println(s"player ${p._1.number}: ${p._2} votes"))
    calculated.head._1

  }
}
