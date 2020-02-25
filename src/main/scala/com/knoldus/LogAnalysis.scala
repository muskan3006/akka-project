package com.knoldus

import java.io.File

import com.typesafe.scalalogging.LazyLogging

import scala.io.Source

object ReadFiles extends App with LazyLogging {

  val directoryPath: String = "/home/knoldus/workspace/log-files"
  private val getFiles: List[File] = {
    val directory = new File(directoryPath)
    if (directory.exists && directory.isDirectory) {
      val list = directory.listFiles.toList
      println(list)
      list
    } else {
      List[File]()
    }
  }

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
        //  val temp = Map("error" -> countError+tempError,"info" -> countInfo+tempInfo,"warn" ->countWarn + tempWarn)
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
