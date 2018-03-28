package com.epam.maf

object Table extends RandomListUtils {

  var candidates: Seq[Player] = Seq.empty[Player]

  var availablePlayers: Seq[Player] = Seq.empty[Player]

  def init(playerNum: Int): Unit = {
    val shufledNum = randomizeList(1 to playerNum)
    val firstMaph = MafiaPlayer(shufledNum.head)
    val secondMaph = MafiaPlayer(shufledNum.tail.head)
    val thirdMaph = MafiaPlayer(shufledNum.tail.tail.head)
    val sherif = SherifPlayer(shufledNum.tail.tail.tail.head)

    val peacePlayers: Seq[Player] = shufledNum.tail.tail.tail.tail.map(PeacePlayer(_))
    println("You are: " + peacePlayers.head)
    availablePlayers = (peacePlayers :+ sherif :+ firstMaph :+ secondMaph :+ thirdMaph).sortBy(_.number)
    //println(availablePlayers)
  }

  def onlyOneCandidate() = candidates.distinct.size == 1

  def playersNumber = availablePlayers.size

  def removePlayer(player2Kill: Player) {
    availablePlayers = availablePlayers.filter(_ != player2Kill)
    println("\nkilled:" + player2Kill.number)
    if (player2Kill.isInstanceOf[SherifPlayer])
      println("I am sherif")
  }


}
