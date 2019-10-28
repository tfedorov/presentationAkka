package com.tfedorov.actor

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

class HelloActor extends Actor {
  private[this] var encapsulatedDate = "Shared nothing"

  def receive: PartialFunction[Any, Unit] = {
    case "hello" => println("hello back at you")
    case _ => println("huh?" + encapsulatedDate)
  }
}

object Main extends App {
  val system = ActorSystem("HelloSystem")
  // default Actor constructor
  val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
  helloActor ! "hello"
  helloActor ! "buenos dias"
}