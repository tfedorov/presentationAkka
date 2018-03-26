package com.epam.maf

import com.epam.maf.EmulatorApp.{Vote, random}

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
      return Vote(votePlayer, this)
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
    Vote(randomNotSelf(availablePlayers), this)
  }

  def makeCandidate(availablePlayers: Seq[Player]): Player = {
    randomNotSelf(availablePlayers)
  }

}
