package com.epam.maf

object Table extends RandomListUtils {

  var candidates: Seq[Player] = Seq.empty[Player]

  var availablePlayers: Seq[Player] =Seq.empty[Player]

  def init(playerNum: Int) = {
    val shufledNum = randomizeList(1 to playerNum)
    val firstMaph = MafiaPlayer(shufledNum.head)
    val secondMaph = MafiaPlayer(shufledNum.tail.head)
    val thirdMaph = MafiaPlayer(shufledNum.tail.tail.head)
    val peacePlayers: Seq[Player] = shufledNum.tail.tail.tail.map(PeacePlayer(_))
    availablePlayers = peacePlayers :+ firstMaph :+ secondMaph :+ thirdMaph
  }

  def onlyOneCandidate() = candidates.distinct.size == 1

  def playersNumber = availablePlayers.size

  def removePlayer(player2Kill: Player) {
    availablePlayers = availablePlayers.filter(_ != player2Kill)
    println("killed:" + player2Kill.number)
  }



}
