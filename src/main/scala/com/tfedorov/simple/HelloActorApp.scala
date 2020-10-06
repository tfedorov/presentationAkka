package com.tfedorov.simple

import akka.actor.{Actor, ActorSystem, Props}

private class HelloActor extends Actor {
  private[this] val encapsulatedDate = "Shared nothing"

  def receive: PartialFunction[Any, Unit] = {
    case "hello" => println("hello back at you")
    case _ => println("huh?" + encapsulatedDate)
  }
}

object HelloActorApp extends App {
  println("HelloActorApp is a simple actor application. It just creates 2 actors and iterate with them.")
  val system = ActorSystem("HelloSystem")
  // default Actor constructor
  val helloActor = system.actorOf(Props[HelloActor], name = "helloActor")
  helloActor ! "hello"
  helloActor ! "buenos dias"
}