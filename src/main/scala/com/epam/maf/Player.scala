package com.epam.maf

import com.epam.maf.EmulatorApp.{Vote, random}

abstract class Player(val number: Int, val isMafia: Boolean) {
  def makeVote(availablePlayers: Seq[Player]): Vote
}

case class MafiaPlayer(num: Int) extends Player(num, true) {

  override def makeVote(availablePlayers: Seq[Player]): Vote = {
    val votePlayer = availablePlayers(random.nextInt(availablePlayers.length))
    if (votePlayer.isMafia)
      Vote(availablePlayers(random.nextInt(availablePlayers.length)), this)
    else
      return Vote(votePlayer, this)
  }

}

case class PeacePlayer(num: Int) extends Player(num, false) {
  override def makeVote(availablePlayers: Seq[Player]): Vote = {
    val randNum = random.nextInt(availablePlayers.length)
    Vote(availablePlayers(randNum), this)
  }
}
