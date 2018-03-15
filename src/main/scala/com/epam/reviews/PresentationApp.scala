package com.epam.reviews

import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.alpakka.csv.scaladsl.{CsvParsing, CsvToMap}
import akka.stream.scaladsl.{FileIO, Flow, Keep, Sink}
import akka.stream.{ActorMaterializer, Materializer}
import com.epam.reviews.SentimentRequestMaker.makeRequest

import scala.concurrent.ExecutionContextExecutor
import scala.language.implicitConversions
import scala.util.{Success, Try}

object PresentationApp extends App {

  implicit val actorSystem: ActorSystem = ActorSystem("Sys")
  implicit val materializer: Materializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = materializer.executionContext

  private val csvFilePath = Paths.get("C:\\work\\workspace\\epam\\presentation\\src\\main\\resources\\Amazon_Unlocked_Mobile.csv")

  val csvFileSource = FileIO.fromPath(csvFilePath)

  case class Product(name: String, review: String)

  val httpsFlow: Flow[(HttpRequest, Product), (Try[HttpResponse], Product), Http.HostConnectionPool] = Http().newHostConnectionPoolHttps[Product]("community-sentiment.p.mashape.com")


  val fN = csvFileSource.via(CsvParsing.lineScanner())
    .via(CsvToMap.toMap())
    .map(lineMap => Product(lineMap("Brand Name").utf8String, lineMap("Reviews").utf8String))
    //.filter(prod => "Nokia".equals(prod.name))
    .map(nokiaProd => (makeRequest(nokiaProd.review), nokiaProd))
    .via(httpsFlow)
    .collect { case (Success(resp), product) => (resp.entity, product.review) }
    .mapAsync(2)(entRew => Unmarshal(entRew._1).to[String].map(entRew._2 + ":" + _))
    .toMat(Sink.head)(Keep.right).run()


  fN.onComplete {
    case Success(e) => {
      println(e)
    }
  }
}
