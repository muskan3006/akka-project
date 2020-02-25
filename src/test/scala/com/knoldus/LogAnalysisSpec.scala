package com.knoldus
import org.scalatest._

class LogAnalysisSpec extends FunSuite with BeforeAndAfterAll {

    var logAnalysis: LogAnalysis = _

    override def beforeAll(): Unit = {
      logAnalysis = new LogAnalysis
    }

    override def afterAll(): Unit = {
      if (logAnalysis != null) {
        logAnalysis = null
      }
    }


  }
