package com.epam.maf

import com.epam.maf.Table._

import scala.io.StdIn.readLine

object EmulatorApp extends App {

  Table.init(10)

  makeNight()
  makeDay()

  makeNight()

  println("------------")
  sherifShowUP()
  printAvailable()
  readLine()

  //makeDay()

  //readLine()
  println("***********")
  println("result: ")
  availablePlayers.foreach(println)

  def makeNight() = {
    println("***********")
    println("Night in the city:")
    sherifChecking()
    NightKillMaker.killInNight()
    printAvailable()
    readLine()
  }

  def makeDay() = {
    println("***********")
    println("Day in the city:")
    DayStepMaker.setCandidates4Vote()
    println("on vote candidates: " + candidates.map(_.number))
    DayStepMaker.makeDayJugment()
    printAvailable()
  }

  def printAvailable() = Table.availablePlayers.foreach(p => print(p.number + " "))
}
