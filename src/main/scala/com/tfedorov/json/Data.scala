package com.tfedorov.json

import scala.util.Random

private[json] object Data {

  final case class Item(name: String, id: Long) {
    def `+`(add: Int): Item = Item(name + add.toString, id + add)
  }


  def randomItem: Item = Item(Random.nextString(3), Random.nextInt(100))


  final case class Order(items: List[Item]) {
    def `+`(add: Int): Order = Order(items.map(_ + add))
  }


  def randomOrder: Order = {
    val items = (1 to Random.nextInt(2) + 1).map(_ => randomItem).toList
    Order(items)
  }

}
