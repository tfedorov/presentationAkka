package com.epam.maf

import com.epam.maf.Table.candidates

import scala.io.StdIn.readLine
import scala.util.Random

object EmulatorApp extends App {


  val random = Random

  println("***********")
  println("Night in the city")
  NightKillMaker.makeNightStep()
  println("")

  println("***********")
  println("Day in the city")
  DayStepMaker.setCandidates4Vote()
  println("on vote:")
  Table.printCandidates()
  DayStepMaker.makeDayJugment()
  readLine()

  println("***********")
  println("Night in the city")
  NightKillMaker.makeNightStep()
  println("")

  println("***********")
  println("Day in the city")
  DayStepMaker.setCandidates4Vote()
  println("on vote:")
  Table.printCandidates()
  DayStepMaker.makeDayJugment()
  readLine()
}