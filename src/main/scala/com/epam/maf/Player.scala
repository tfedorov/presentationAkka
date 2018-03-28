package com.epam.maf

abstract class Player(val number: Int, val isMafia: Boolean) extends RandomListUtils {
  def makeVote(): Vote

  def makeCandidate(): Player

  protected def randomNotSelf(chooseList: Seq[Player]): Player = randomFromList(chooseList.filterNot(_ == this))

}

case class MafiaPlayer(num: Int) extends Player(num, true) {

  override def makeVote(): Vote = {
    Vote(this, randomNotSelf(Table.candidates.filterNot(_.isMafia)))
  }

  override def makeCandidate(): Player = {
    randomNotSelf(Table.candidates.filterNot(_.isMafia))
  }

}

case class PeacePlayer(num: Int) extends Player(num, false) {
  override def makeVote(): Vote = {
    Vote(this, randomNotSelf(Table.candidates))
  }

  override def makeCandidate(): Player = {
    randomNotSelf(Table.availablePlayers)
  }

}

case class Vote(whoVote: Player, whom: Player) {
  override def toString: String = s"${whoVote.number} -> ${whom.number}"
}