package com.tfedorov.actor

import akka.actor.{Actor, ActorRef}

object Messages {

  case class PingMessage(num: Int)

  case object PongMessage

  case object StartMessage

  case class StopMessage(reason: String)


  class Player2(pong: ActorRef) extends Actor {
    private[this] var internalState = 0

    def receive: PartialFunction[Any, Unit] = {
      case StartMessage =>
        println("The game is started")
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

  class Player1 extends Actor {
    def receive: PartialFunction[Any, Unit] = {
      case PingMessage(num) =>
        println("  pong - " + num)
        sender ! PongMessage
      case StopMessage(reason: String) =>
        println("pong stopped" + reason)
        context.stop(self)
    }
  }

}
