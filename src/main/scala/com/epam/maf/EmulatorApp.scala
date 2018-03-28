package com.epam.maf

import scala.io.StdIn.readLine
import Table._

object EmulatorApp extends App {

  Table.init(10)

  makeNight()
  makeDay()
  makeNight()
  makeDay()

  readLine()
  println("***********")
  println("result: ")
  availablePlayers.foreach(println)

  def makeDay() = {
    println("***********")
    println("Night in the city:")
    NightKillMaker.killInNight()
    println("")
  }

  def makeNight() = {
    println("***********")
    println("Day in the city:")
    DayStepMaker.setCandidates4Vote()
    println("on vote candidates: " + candidates.map(_.number))
    DayStepMaker.makeDayJugment()
  }

}
