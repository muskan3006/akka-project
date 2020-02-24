import java.io.File

import com.typesafe.scalalogging._

import scala.io.Source

object ReadFiles extends App with LazyLogging {

  val directoryPath: String = "/home/knoldus/workspace/log-files"
  private implicit val getFiles: List[File] = {
    val directory = new File(directoryPath)
    if (directory.exists && directory.isDirectory) {
      val list = directory.listFiles.toList
      list
    } else {
      List[File]()
    }
  }

def totalCount: Any ={
  @scala.annotation.tailrec
  def operatingMethod(list: List[File], resultMap:Map[String,Long],countWarn: Long, countInfo: Long, countError: Long):Any = {
    list match {
      case first :: listOfFile => val a = copy(first)
        operatingMethod(listOfFile, resultMap++Map("error" ->a("error").getOrElse(countError)) ++Map("info" -> a("info").getOrElse(countInfo)) ++
          Map("warn" -> a("warn").getOrElse (countError)),a("error") getOrElse countError,a("info").getOrElse(countInfo), a("errors") getOrElse countError)
      case first :: Nil => val a = copy(first)
        resultMap++Map("error" ->a("error").getOrElse(countError)) ++Map("info" -> a("info").getOrElse(countInfo)) ++ Map("error" -> a("warn").getOrElse (countWarn))
      case Nil => resultMap++Map("warn" -> countWarn) ++Map("info" -> countInfo) ++ Map("error" -> countError)
    }
  }
  operatingMethod(getFiles, Map.empty[String,Long], 0, 0, 0)
}
  private def copy(file: File) = {
    val source = Source.fromFile(file.toString)
    val content = source.getLines
    val words = content.flatMap(_.split(" ")).foldLeft(Map.empty[String, Long]) { (count, word) => count + (word -> (count.getOrElse(word, 0) + 1)) }
    Map("error"->words.get("[ERROR]"))++Map("warn"->words.get("[WARN]"))++Map("info"->words.get("[INFO]"))

  }
println(totalCount)
}


