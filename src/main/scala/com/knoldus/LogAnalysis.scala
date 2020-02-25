package com.knoldus

import java.io.File

import scala.io.Source

class LogAnalysis extends ReadFile {

  def average: Map[String, Int] ={
    val noOfFiles = getFiles.length
    val total = totalCount
    val avgErrors = total._1/noOfFiles
    val avgInfo = total._2/noOfFiles
    val avgWarn = total._3/noOfFiles
    Map("averageErrors" -> avgErrors,"averageInfo" -> avgInfo,"averageWarn" -> avgWarn)
  }

  def totalCount: (Int, Int, Int) = {
    @scala.annotation.tailrec
    def operatingMethod(list: List[File], countError: Int, countInfo: Int, countWarn: Int): (Int, Int, Int) = {
      list match {
        case first :: listOfFile => val a = count(first)
          val tempError = a.getOrElse("error", countError)
          val tempInfo = a.getOrElse("info", countInfo)
          val tempWarn = a.getOrElse("warn", countWarn)
          operatingMethod(listOfFile, countError + tempError, countInfo + tempInfo, countWarn + tempWarn)
        case first :: Nil => val a = count(first)
          val tempError = a.getOrElse("error", countError)
          val tempInfo = a.getOrElse("info", countInfo)
          val tempWarn = a.getOrElse("warn", countWarn)
          (countError + tempError, countInfo + tempInfo, countWarn + tempWarn)
        case Nil => (countError, countInfo, countWarn)
      }
    }

    operatingMethod(getFiles,0,0,0)
  }

  private def count(file: File): Map[String, Int] = {
    val source = Source.fromFile(file)
    val content = source.getLines.toList
    content.foldLeft(Map("error" -> 0, "warn" -> 0, "info" -> 0)) { (result, line) => {
      if (line.contains("[ERROR]")) {
        result + ("error" -> (result("error") + 1))
      }
      else if (line.contains("[WARN]")) {
        result + ("warn" -> (result("warn") + 1))
      }
      else if (line.contains("[INFO]")) {
        result + ("info" -> (result("info") + 1))
      }
      else {
        result
      }
    }
    }

  }

}

//object A extends App{
//  val a = new LogAnalysis
//  println(a.totalCount)
//}

