package com.epam.maf

import scala.io.StdIn.readLine
import Table._

object EmulatorApp extends App {

  println("***********")
  println("Night in the city")
  NightKillMaker.makeNightStep()
  println("")

  println("***********")
  println("Day in the city")
  DayStepMaker.setCandidates4Vote()
  println("on vote candidates: " + candidates.map(_.number))
  DayStepMaker.makeDayJugment()
  readLine()

  println("***********")
  println("Night in the city")
  NightKillMaker.makeNightStep()
  println("")

  println("***********")
  println("Day in the city")
  DayStepMaker.setCandidates4Vote()
  println("on vote candidates: " + candidates.map(_.number))
  DayStepMaker.makeDayJugment()

  readLine()
  availablePlayers.foreach(println)
}
