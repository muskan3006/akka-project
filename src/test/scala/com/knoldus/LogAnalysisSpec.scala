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
test("testing totalCount"){
  val actual = logAnalysis.totalCount
  val expected = (0,8448,47982)
  assert(actual==expected)
}

  test("testing average"){
    val actual = logAnalysis.average
    val expected = Map("averageErrors" -> 0, "averageInfo" -> 256, "averageWarn" -> 1454)
    assert(actual==expected)
  }

}
