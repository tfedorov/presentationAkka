package com.tfedorov.reviews

import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.alpakka.csv.scaladsl.{CsvParsing, CsvToMap}
import akka.stream.scaladsl.{FileIO, Sink}
import com.tfedorov.reviews.SentimentRequestMaker.makeRequest

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext}
import scala.language.implicitConversions

object SentimentApp extends App {

  implicit val actorSystem: ActorSystem = ActorSystem("Sys")
  implicit val ec = ExecutionContext.global

  private val csvFilePath = Paths.get("./src/main/resources/Amazon_Unlocked_Mobile.csv")

  val csvFileSource = FileIO.fromPath(csvFilePath)
  val httpsFlow = Http().cachedHostConnectionPool[Product]("api.deepai.org")

  val jsoner = new JsonSentimentReader {}
  val futureResult = csvFileSource.via(CsvParsing.lineScanner())
    .via(CsvToMap.toMap())
    .map(lineMap => Product(lineMap("Brand Name").utf8String, lineMap("Reviews").utf8String))
    .map(inputProd => (makeRequest(inputProd.review), inputProd))
    .via(httpsFlow)
    .filter(_._1.isSuccess)
    .mapAsync(2)(jsoner.read)
    .runWith(Sink.foreach(println))

  Await.result(futureResult, 6 seconds)

}
