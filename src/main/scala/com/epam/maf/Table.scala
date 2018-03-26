package com.epam.maf

import com.epam.maf.EmulatorApp.random

import scala.collection.mutable

object Table {

  val candidates: mutable.Buffer[Player] = mutable.Buffer.empty[Player]

  val availablePlayers: mutable.Buffer[Player] = shuffle(10).toBuffer

  def onlyOneCandidate() = candidates.distinct.size == 1

  def setCandidates(newCandidates: Seq[Player]) = {
    candidates.clear()

    candidates ++= newCandidates
  }

  def playersNumber = availablePlayers.size

  def removePlayer(player2Kill: Player) {
    availablePlayers -= player2Kill
    println("killed:" + player2Kill)
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
