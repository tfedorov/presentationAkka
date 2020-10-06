package com.tfedorov.reviews

import akka.util.ByteString

protected[reviews] case class Product(name: String, review: String)

object Product {
  def parseMap(lineMap: Map[String, ByteString]): Product = Product(lineMap("Brand Name").utf8String, lineMap("Reviews").utf8String)
}
