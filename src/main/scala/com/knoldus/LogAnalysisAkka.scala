package com.knoldus

import scala.language.postfixOps
import java.io.File

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.pattern.ask
import akka.routing.RoundRobinPool
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
  implicit val timeout: Timeout = Timeout(20.second)
  val system = ActorSystem("LogAnalyse")
  val worker = system.actorOf((RoundRobinPool(8)).props(Props[LogAnalysisAkka]).withDispatcher("fixed-thread-pool"), name = "worker")
  val a = getFiles.map(file => {(worker ? file).mapTo[(Int,Int,Int)]})
  val b = Future.sequence(a).map(_.last)

  val result = Await.result(b, 20 second)
  println(result)

}