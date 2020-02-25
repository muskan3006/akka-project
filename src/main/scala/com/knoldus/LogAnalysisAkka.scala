package com.knoldus

import scala.language.postfixOps
import java.io.File

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

class LogAnalysisAkka extends LogAnalysis with Actor with ActorLogging {

  var errorCount = 0
  var infoCount = 0
  var warnCount = 0

  def receive: Receive = {
    case path: File => val a = Helper.countError(path)
      val b = Helper.countInfo(path)
      val c = Helper.countWarn(path)
      errorCount = errorCount + a
      infoCount = infoCount + b
      warnCount = warnCount + c
      sender() ! (errorCount, warnCount, infoCount)
    case _ => log.info("No file found")

  }

}

object LogAnalyse extends App with ReadFile {
  implicit val timeout: Timeout = Timeout(10.second)
  val system = ActorSystem("LogAnalyse")
  val worker = system.actorOf(Props[LogAnalysisAkka], name = "worker")
  // val listOfWorkers = getFiles.map(_ => system.actorOf(Props[LogAnalysisAkka]))
  //Thread.sleep(10.seconds)
  //val total = listOfTotal.
  val a = getFiles.map(file => {(worker ? file).mapTo[(Int,Int,Int)]})
  val b = Future.sequence(a).map(_.last)

  val result = Await.result(b, 10 second)
  println(result)

}