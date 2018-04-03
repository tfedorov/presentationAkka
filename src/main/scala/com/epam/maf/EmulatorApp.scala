package com.epam.maf

import scala.io.StdIn.readLine
import Table._
import com.epam.maf.player.SherifPlayer

object EmulatorApp extends App {

  Table.init(10)

  makeNight()
  makeDay()
  printAvailable()
  makeNight()
  readLine()
  println("------------")

  Table.availablePlayers.collect { case s: SherifPlayer =>
    s.printSherif
    printAvailable()
  }
  readLine()

  makeDay()

  readLine()
  println("***********")
  println("result: ")
  availablePlayers.foreach(println)

  def makeNight() = {
    println("***********")
    println("Night in the city:")
    Table.availablePlayers.collect { case s: SherifPlayer =>
      s.checkPlayers()
    }
    NightKillMaker.killInNight()
    println("")
    printAvailable()
    readLine()
  }

  def makeDay() = {
    println("***********")
    println("Day in the city:")
    DayStepMaker.setCandidates4Vote()
    println("on vote candidates: " + candidates.map(_.number))
    DayStepMaker.makeDayJugment()
  }

  def printAvailable() = Table.availablePlayers.foreach(p => print(p.number + " "))
}
