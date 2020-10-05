package com.tfedorov.mem

case class Mem(top: String, bottom: String, image: String)

object Mem {

  import scala.util.Random

  private val acronymMap = Seq(
    "WPM" -> "Workforce+Planning+and+Management",
    "JiT" -> "Just+in+time",
    "OE" -> "Optimistic+estimate",
    "BT" -> "Business+Trip",
    "TSR" -> "Technical+Support+Request",
    "TBD" -> "To+be+done",
    "ASAP" -> "As+soon+as+possible")
  private val images = "Success-Kid-Original" ::"Peter-Parker-Cry" :: "Condescending-Wonka" :: "Chuck-Norris-Flex" :: Nil

  def random(): Mem = {
    val randomImage = images(Random.nextInt(images.size))
    val randomPair = acronymMap(Random.nextInt(acronymMap.size))
    Mem(randomPair._1, randomPair._2, randomImage)
  }

}