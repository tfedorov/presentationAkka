package com.epam.maf

import com.epam.maf.EmulatorApp.random

abstract class Player(val number: Int, val isMafia: Boolean) {
  def makeVote(availablePlayers: Seq[Player]): Vote

  protected def randomNotSelf(chooseList: Seq[Player]): Player = {
    val chooseCandidates = chooseList.filterNot(_ == this)
    chooseCandidates(random.nextInt(chooseCandidates.length))
  }

  def makeCandidate(availablePlayers: Seq[Player]): Player

}

case class MafiaPlayer(num: Int) extends Player(num, true) {

  override def makeVote(availablePlayers: Seq[Player]): Vote = {

    val votePlayer = randomNotSelf(availablePlayers)
    if (votePlayer.isMafia)
      Vote(randomNotSelf(availablePlayers), this)
    else
      return Vote(this, votePlayer)
  }

  override def makeCandidate(availablePlayers: Seq[Player]): Player = {
    val cand = randomNotSelf(availablePlayers)
    if (cand.isMafia)
      return randomNotSelf(availablePlayers)
    else
      return cand
  }

}

case class PeacePlayer(num: Int) extends Player(num, false) {
  override def makeVote(availablePlayers: Seq[Player]): Vote = {
    Vote(this, randomNotSelf(availablePlayers))
  }

  def makeCandidate(availablePlayers: Seq[Player]): Player = {
    randomNotSelf(availablePlayers)
  }

}

case class Vote(whoVote: Player, whom: Player) {
  override def toString: String = s"${whoVote.number} -> ${whom.number}"
}