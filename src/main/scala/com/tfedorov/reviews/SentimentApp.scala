package com.tfedorov.reviews

import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.alpakka.csv.scaladsl.{CsvParsing, CsvToMap}
import akka.stream.scaladsl.{FileIO, Sink}
import com.tfedorov.reviews.SentimentRequestMaker.makeRequest

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor}

object SentimentApp extends App {

  final val INPUT_PATH = "./src/main/resources/Amazon_Unlocked_Mobile.csv"
  final val SERVICE = "api.deepai.org"

  println(s"The SentimentApp reads a reviews file '$INPUT_PATH'. And process reviews throw $SERVICE")
  implicit val actorSystem: ActorSystem = ActorSystem("Sys")
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  private val csvFilePath = Paths.get(INPUT_PATH)

  val csvFileSource = FileIO.fromPath(csvFilePath)
  val httpsFlow = Http().cachedHostConnectionPool[Product](SERVICE)

  val jsoner = new JsonSentimentReader {}
  val futureResult = csvFileSource.via(CsvParsing.lineScanner())
    .via(CsvToMap.toMap())
    .map(Product.parseMap)
    .map(inputProd => (makeRequest(inputProd.review), inputProd))
    .via(httpsFlow)
    .filter(_._1.isSuccess)
    .mapAsync(2)(jsoner.read)
    .runWith(Sink.foreach(println))

  Await.result(futureResult, 6 seconds)

}
