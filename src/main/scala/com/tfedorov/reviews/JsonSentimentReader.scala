package com.tfedorov.reviews

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

trait JsonSentimentReader extends SprayJsonSupport with DefaultJsonProtocol {
  final val CUT_SIZE = 60
  implicit val _itemFormat: RootJsonFormat[SentimentResponse] = jsonFormat2(SentimentResponse)

  def read(resp: (Try[HttpResponse], Product))(implicit ec: ExecutionContext, mat: Materializer): Future[String] = resp match {
    case (Success(HttpResponse(_, _, entity, _)), product) => Unmarshal(entity).to[SentimentResponse].map(format(_, product))
    case any => Future(any.toString())
  }

  private def format(sentiment: SentimentResponse, product: Product): String = {
    val review = if (product.review.length > CUT_SIZE) product.review.substring(0, CUT_SIZE - 1) + "..." else product.review
    s"${product.name} :$review : ${sentiment.output}"
  }

}


