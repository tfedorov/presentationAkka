package com.tfedorov.actor

import akka.actor._
import com.tfedorov.actor.Messages._

object PingPongApp extends App {

  println("PingPongApp creates 2 actors and pulls messages between it.")

  val system = ActorSystem("PingPongSystem")
  val player1 = system.actorOf(Props[Player1], name = "pong")
  val player2 = system.actorOf(Props(new Player2(player1)), name = "ping")
  // start them going
  player2 ! StartMessage

  Thread.sleep(155)
  player1 ! StopMessage("Stopped by main App")

}
