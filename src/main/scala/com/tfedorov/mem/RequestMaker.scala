package com.tfedorov.mem

import akka.http.scaladsl.model._

protected[mem] object RequestMaker {

  private[mem] def makeRequest(mem: MemID): HttpRequest = {
    //http://apimeme.com/meme?meme=Confused-Gandalf&top=Top+text&bottom=Bottom+text&test=1
    val uri = Uri(s"http://apimeme.com/meme?meme=${mem.image}&top=${mem.top}&bottom=${mem.bottom}&test=1")
    println(uri.toString())
    HttpRequest(
      uri = uri,
      method = HttpMethods.GET
    ) //.addHeader(RawHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"))

  }

}
