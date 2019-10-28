package com.tfedorov.actor

import akka.actor._


case class PingMessage(num: Int)

case object PongMessage

case object StartMessage

case class StopMessage(reason: String)


class Ping(pong: ActorRef) extends Actor {
  private[this] var internalState = 0

  def receive: PartialFunction[Any, Unit] = {
    case StartMessage =>
      pong ! PingMessage(internalState)

    case PongMessage =>
      if (internalState > 99)
        sender ! StopMessage("Stopped by pong")
      else {
        internalState += 1
        println("ping - " + internalState)
        sender ! PingMessage(internalState)
      }
  }

}

class Pong extends Actor {
  def receive: PartialFunction[Any, Unit] = {
    case PingMessage(num) =>
      println("  pong - " + num)
      sender ! PongMessage
    case StopMessage(reason: String) =>
      println("pong stopped" + reason)
      context.stop(self)
  }
}

object PingPongTest extends App {
  val system = ActorSystem("PingPongSystem")
  val pong = system.actorOf(Props[Pong], name = "pong")
  val ping = system.actorOf(Props(new Ping(pong)), name = "ping")
  // start them going
  ping ! StartMessage

  Thread.sleep(155)
  pong ! StopMessage("Stopped by main App")

}
