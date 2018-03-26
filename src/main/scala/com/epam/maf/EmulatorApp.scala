package com.epam.maf

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
  DayStepMaker.makeDayStep()
  readLine()

  println("***********")
  println("Night in the city")
  NightKillMaker.makeNightStep()
  println("")

  println("***********")
  println("Day in the city")
  DayStepMaker.makeDayStep()
  println("")
}
