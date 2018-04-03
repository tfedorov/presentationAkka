package com.epam.maf.player

import com.epam.maf.Table

case class SherifPlayer(numb: Int) extends Player(numb) {

  private var checkedPlayers: Seq[Player] = Seq.empty

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

  def printSherif() = {
    Table.sherifCheck = checkedPlayers
    println(s"I am $number and : ${checkedPlayers.map(p => (p.number, p.isInstanceOf[MafiaPlayer]))}")
  }

  override def kill() = {
    super.kill()
    printSherif()
  }

}