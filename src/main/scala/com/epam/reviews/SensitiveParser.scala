package com.epam.reviews

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.unmarshalling.Unmarshal

import scala.concurrent.Future

object SensitiveParser {

  protected[reviews] def parse(response: HttpResponse): Future[String] = Unmarshal(response.entity).to[String]
}
