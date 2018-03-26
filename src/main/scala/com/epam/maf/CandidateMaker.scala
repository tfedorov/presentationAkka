package com.epam.maf

import com.epam.maf.Table._

import scala.collection.mutable.ListBuffer

object CandidateMaker {

  val candidates: ListBuffer[Player] = ListBuffer.empty[Player]

  def makeCandidate(selfPlayer: Player): Unit = {
    if (candidates.size >= 3)
      return

    val candidate: Player = randomPlayer()
    if (selfPlayer != candidate)
      candidates += candidate

  }
}
