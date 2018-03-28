package com.epam.maf

import scala.io.StdIn.readLine
import Table._

object EmulatorApp extends App {

  Table.init(10)

  makeNight()
  makeDay()
  makeNight()
  println("------------")
  println(Table.availablePlayers.collect { case s: SherifPlayer =>
    s.showHimself
  }
  )
  readLine()
  makeDay()

  Table.availablePlayers.foreach(p => print(p.number))

  readLine()
  println("***********")
  println("result: ")
  availablePlayers.foreach(println)

  def makeNight () = {
    println("***********")
    println("Night in the city:")
    Table.availablePlayers.collect { case s: SherifPlayer =>
      s.checkPlayers()
    }
    NightKillMaker.killInNight()
    println("")
    readLine()
  }

  def makeDay() = {
    println("***********")
    println("Day in the city:")
    DayStepMaker.setCandidates4Vote()
    println("on vote candidates: " + candidates.map(_.number))
    DayStepMaker.makeDayJugment()
  }

}
