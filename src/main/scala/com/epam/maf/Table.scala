package com.epam.maf

import com.epam.maf.EmulatorApp.random

import scala.collection.mutable

object Table {

  val availablePlayers: mutable.Buffer[Player] = shuffle(10).toBuffer

  def playersNumber = availablePlayers.size

  def removePlayer(player: Player) {
    availablePlayers -= player
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

  private def shuffle(playerNum: Int) = (1 to playerNum).map(number => isMaph match {
    case true => MafiaPlayer(number)
    case false => PeacePlayer(number)
  })
}
