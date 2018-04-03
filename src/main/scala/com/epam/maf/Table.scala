package com.epam.maf

import com.epam.maf.player.{MafiaPlayer, PeacePlayer, Player, SherifPlayer}

object Table extends RandomListUtils {

  var candidates: Seq[Player] = Seq.empty[Player]

  var availablePlayers: Seq[Player] = Seq.empty[Player]

  def sherifChecking() = {
    availablePlayers.filter(_.isInstanceOf[SherifPlayer]).foreach(_.asInstanceOf[SherifPlayer].checkPlayers())
  }

  def sherifShowUP() = {
    availablePlayers.filter(_.isInstanceOf[SherifPlayer]).foreach(_.asInstanceOf[SherifPlayer].printSherif)
  }

  def availableNoMaf() = availablePlayers.filterNot(_.isInstanceOf[MafiaPlayer])

  var sherifCheck: Seq[Player] = Seq.empty

  def noRedCheckPlayers(): Seq[Player] = {
    val redChecks = sherifCheck.filterNot(_.isInstanceOf[MafiaPlayer])
    availablePlayers.filterNot(redChecks.contains(_))
  }

  def checkedBlack() = {
    sherifCheck.filter(_.isInstanceOf[MafiaPlayer])
  }

  def init(playerNum: Int): Unit = {
    val shufledNum = randomizeList(1 to playerNum)
    val firstMaph = MafiaPlayer(shufledNum.head)
    val secondMaph = MafiaPlayer(shufledNum.tail.head)
    val thirdMaph = MafiaPlayer(shufledNum.tail.tail.head)
    val sherif = SherifPlayer(shufledNum.tail.tail.tail.head)

    val peacePlayers: Seq[Player] = shufledNum.tail.tail.tail.tail.map(PeacePlayer(_))
    println("You are: " + peacePlayers.head)
    peacePlayers.head.yourSelf = true
    availablePlayers = (peacePlayers :+ sherif :+ firstMaph :+ secondMaph :+ thirdMaph).sortBy(_.number)
  }

  def onlyOneCandidate() = candidates.distinct.size == 1

  def playersNumber = availablePlayers.size

  def removePlayer(player2Kill: Player) {
    availablePlayers = availablePlayers.filter(_ != player2Kill)
    player2Kill.kill()
  }

}
