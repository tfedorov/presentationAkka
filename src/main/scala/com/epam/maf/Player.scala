package com.epam.maf

abstract class Player(val number: Int, val isMafia: Boolean) extends RandomListUtils {
  def makeVote(): Vote

  def makeCandidate(): Player

  protected def randomNotSelf(chooseList: Seq[Player]): Player = randomFromList(chooseList.filterNot(_ == this))

}

case class MafiaPlayer(num: Int) extends Player(num, true) {

  override def makeVote(): Vote = {
    val candidats = Table.candidates.filterNot(_.isMafia)
    if (candidats.isEmpty)
      return Vote(this, randomNotSelf(candidats))
    Vote(this, candidats.head)
  }

  override def makeCandidate(): Player = {
    randomNotSelf(Table.noRedCheckPlayers().filterNot(_.isMafia))
  }

}

case class PeacePlayer(num: Int) extends Player(num, false) {
  override def makeVote(): Vote = {
    Vote(this, randomNotSelf(Table.candidates))
  }

  override def makeCandidate(): Player = {
    randomNotSelf(Table.noRedCheckPlayers())
  }

}

case class SherifPlayer(numb: Int) extends Player(numb, true) {

  var checkedPlayers: Seq[Player] = Seq.empty

  override def makeVote(): Vote = {
    Vote(this, randomNotSelf(Table.candidates))
  }

  override def makeCandidate(): Player = {
    randomNotSelf(Table.noRedCheckPlayers())
  }

  def checkPlayers() = {
    val candidates = Table.availablePlayers.filterNot(_ == this).filterNot(checkedPlayers.contains(_))
    checkedPlayers = checkedPlayers :+ randomFromList(candidates)
  }

  def showHimself(): String = {
    Table.sherifCheck = checkedPlayers
    s"I am $number and : ${checkedPlayers.map(p => (p.number, p.isMafia))}"
  }

}

case class Vote(whoVote: Player, whom: Player) {
  override def toString: String = s"${whoVote.number} -> ${whom.number}"
}