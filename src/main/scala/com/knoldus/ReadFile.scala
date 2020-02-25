package com.knoldus

import java.io.File

trait ReadFile {
  val directoryPath: String = "/home/knoldus/workspace/log-files"

  val getFiles: List[File] = {
    val directory = new File(directoryPath)
    if (directory.exists && directory.isDirectory) {
      val list = directory.listFiles.toList
      list
    } else {
      List[File]()
    }
  }

}

