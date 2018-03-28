package com.epam.maf

import scala.util.Random

object Table extends RandomListUtils {

  val random = Random

  var candidates: Seq[Player] = Seq.empty[Player]

  var availablePlayers: Seq[Player] = shuffle(10).toBuffer

  def onlyOneCandidate() = candidates.distinct.size == 1

  def playersNumber = availablePlayers.size

  def removePlayer(player2Kill: Player) {
    availablePlayers = availablePlayers.filter(_ != player2Kill)
    println("killed:" + player2Kill.number)
  }

  def randomPlayer(): Player = {
    val candNume = random.nextInt(playersNumber)
    return availablePlayers(candNume)
  }

  var availableMaphs = 3
  var availablePlayer = 10

  private def isMaph: Boolean = {
    if (availableMaphs == 0)
      return false

    val playerNum = random.nextInt(availablePlayer)
    availablePlayer -= 1
    if (playerNum >= availableMaphs)
      return false
    availableMaphs -= 1
    return true

  }

  private def shuffle(playerNum: Int): Seq[Player] = {
    val shufledNum = randomizeList(1 to playerNum)
    val firstMaph = MafiaPlayer(shufledNum.head)
    val secondMaph = MafiaPlayer(shufledNum.tail.head)
    val thirdMaph = MafiaPlayer(shufledNum.tail.tail.head)
    val peacePlayers: Seq[Player] = shufledNum.tail.tail.tail.map(PeacePlayer(_))
    peacePlayers :+ firstMaph :+ secondMaph :+ thirdMaph
  }

}
