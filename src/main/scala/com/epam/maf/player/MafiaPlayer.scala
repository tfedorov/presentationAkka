package com.epam.maf.player

import com.epam.maf.Table

case class MafiaPlayer(num: Int) extends Player(num) {

  override def makeVote(): Vote = {
    val candidats = Table.candidates.filterNot(_.isInstanceOf[MafiaPlayer])
    if (candidats.isEmpty)
      return Vote(this, randomNotSelf(candidats))
    Vote(this, candidats.head)
  }

  override def makeCandidate(): Player = {
    randomNotSelf(Table.noRedCheckPlayers().filterNot(_.isInstanceOf[MafiaPlayer]))
  }

}
