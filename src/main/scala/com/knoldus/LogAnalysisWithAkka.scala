package com.knoldus

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.util.Timeout

import scala.concurrent.duration._

class LogAnalysisWithAkka(head1: ActorRef, head2: ActorRef) extends Actor with ReadFile with ActorLogging {
  def receive: Receive = {
    case "fileTotal"  => head1 ! "total"
    case "fileAverage" if getFiles.nonEmpty => head2 ! "average"
    case _ => log.info("No files to do functioning")
  }

}

class DFG extends LogAnalysis with Actor with ActorLogging with ReadFile {
  def receive: Receive = {
    case "total" =>  totalCount

    case _ => log.info("Can't process")
  }

}

class D extends LogAnalysis with Actor with ActorLogging with ReadFile {
  def receive: Receive = {
    case "average" =>  average
    case _ => log.info("Can't process")
  }
}


object LogAnalyzer extends App {
  val system = ActorSystem("LogAnalyzerSystem")
  val head1 = system.actorOf(Props[DFG], name = "head1")
  val head2 = system.actorOf(Props[D], name = "head2")
  val head = system.actorOf(Props(new LogAnalysisWithAkka(head1, head2)), name = "head")
  implicit val timeout: Timeout = Timeout(50.seconds)

   head ! "fileTotal"
  //total.map(identity)

   head ! "fileAverage"
  //average.map(identity)

}
