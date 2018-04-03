package com.epam.maf.player

import com.epam.maf.Table

case class PeacePlayer(num: Int) extends Player(num) {

  override def makeVote(): Vote = {
    Vote(this, randomNotSelf(Table.candidates))
  }

  override def makeCandidate(): Player = {
    randomNotSelf(Table.noRedCheckPlayers())
  }

}
