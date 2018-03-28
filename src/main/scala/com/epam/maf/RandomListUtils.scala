package com.epam.maf

trait RandomListUtils {

  def randomFromList[T](list: Seq[T]): T = {
    randomizeList(list).head
  }

  def randomizeList[T](list: Seq[T]): Seq[T] = {
    scala.util.Random.shuffle(list)
  }
}
