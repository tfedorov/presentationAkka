package com.tfedorov.reviews

import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{FormData, _}

protected[reviews] object SentimentRequestMaker {
  
  //https://rapidapi.com/twinword/api/sentiment-analysis
  protected[reviews] def makeRequest(text: String): HttpRequest = {
    val entity = FormData("text" -> text).toEntity

    HttpRequest(
      uri = Uri("https://api.deepai.org/api/sentiment-analysis"),
      method = HttpMethods.POST,
      entity = entity
    )
      .addHeader(RawHeader("api-key", "a44fb8c7-4b7e-4540-9588-6cd52878dea0"))
  }

}
