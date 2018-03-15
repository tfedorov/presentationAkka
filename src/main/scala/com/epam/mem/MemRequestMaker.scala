package com.epam.mem

import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader

protected[mem] object MemRequestMaker {

  protected[mem] def makeRequest(mem: Mem) = {
    HttpRequest(
      uri = Uri(s"https://ronreiter-meme-generator.p.mashape.com/meme?bottom=${mem.bottom}&font=Impact&font_size=50&meme=${mem.image}&top=${mem.top}"),
      method = HttpMethods.GET
    ).addHeader(RawHeader("X-Mashape-Key", "NNfrCoW1yfmshbv8DzK3AeZiJr1Gp1LLdOPjsnlsjPog0dZCYd"))

  }

}
