package com.epam.mem

case class Mem(top: String, bottom: String, image: String)

object Mem {

  import scala.util.Random

  val images = List("2nd-Term-Obama", "Unicorn-MAN", "Pepperidge-Farm-Remembers")

  def random() = {
    val randomImage = images(Random.nextInt(images.size))
    Mem("top", "botom", randomImage)
  }
}