package com.epam.maf.player

import com.epam.maf.RandomListUtils

abstract class Player(val number: Int) extends RandomListUtils {

  var yourSelf = false

  def makeVote(): Vote

  def makeCandidate(): Player

  protected def randomNotSelf(chooseList: Seq[Player]): Player = randomFromList(chooseList.filterNot(_ == this))

  def kill() = {
    println("I am killed " + number)
  }

}

case class Vote(whoVote: Player, whom: Player) {
  override def toString: String = s"${whoVote.number} -> ${whom.number}"
}