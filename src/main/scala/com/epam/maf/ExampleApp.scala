package com.epam.maf

object ExampleApp extends App {
  trait Node[+B] {
    def prepend[U >: B](elem: U): Node[U]
  }

  case class ListNode[+B](h: B, t: Node[B]) extends Node[B] {
    def prepend[U >: B](elem: U): ListNode[U] = ListNode(elem, this)
    def head: B = h
    def tail: Node[B] = t
  }

  case class Nil[+B]() extends Node[B] {
    def prepend[U >: B](elem: U): ListNode[U] = ListNode(elem, this)
  }
  trait Bird

  case class AfricanSwallow() extends Bird

  case class EuropeanSwallow() extends Bird


  val africanSwallowList = ListNode[Bird](AfricanSwallow(), Nil())
  val birdList: Node[Bird] = africanSwallowList
  println(africanSwallowList.prepend(new EuropeanSwallow))

  println(africanSwallowList)
}
