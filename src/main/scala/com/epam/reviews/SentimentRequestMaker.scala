package com.epam.reviews

import akka.http.javadsl.model.MediaRanges
import akka.http.scaladsl.model.MediaRanges.PredefinedMediaRange
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.{Accept, RawHeader}

protected[reviews] object SentimentRequestMaker {

  private val CONTENT_TYPE = MediaTypes.`application/x-www-form-urlencoded`.toContentType(HttpCharsets.`UTF-8`)
  private val ACCEPT_HEADER = Accept(MediaRanges.ALL_APPLICATION.asInstanceOf[PredefinedMediaRange])

  protected[reviews] def makeRequest(text: String) = {
    val entity = HttpEntity(CONTENT_TYPE, s"txt=$text")
    HttpRequest(
      uri = Uri("https://community-sentiment.p.mashape.com/text/"),
      method = HttpMethods.POST,
      entity = entity
    ).addHeader(RawHeader("X-Mashape-Key", "NNfrCoW1yfmshbv8DzK3AeZiJr1Gp1LLdOPjsnlsjPog0dZCYd"))
      .addHeader(ACCEPT_HEADER)
  }

}
