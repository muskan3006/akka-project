package com.knoldus

import java.io.File

import scala.io.Source

object Helper {
  def countError(file: File): Int = {
    val source = Source.fromFile(file)
    val content = source.getLines.toList
    content.foldLeft(0) { (result, line) => {
      if (line.contains("[ERROR]")) {
        result + 1
      } else {
        result
      }

    }
    }
  }

  def countInfo(file: File): Int = {
    val source = Source.fromFile(file)
    val content = source.getLines.toList
    content.foldLeft(0) { (result, line) => {
      if (line.contains("[INFO]")) {
        result + 1
      } else {
        result
      }

    }
    }
  }

  def countWarn(file: File): Int = {
    val source = Source.fromFile(file)
    val content = source.getLines.toList
    content.foldLeft(0) { (result, line) => {
      if (line.contains("[WARN]")) {
        result + 1
      } else {
        result
      }

    }
    }
  }
}
