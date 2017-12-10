package scala.scalaJava

/**
  * Created by root on 2017/12/9.
  * java和scala相互转换
  */
class ArrayList {

}
object ArrayList{
  def main(args: Array[String]): Unit = {
    import scala.collection.JavaConversions.bufferAsJavaList
    import scala.collection.mutable.ArrayBuffer
    val command = ArrayBuffer("java","c:\\test.java")
    val processBuider = new ProcessBuilder(command)
    val process = processBuider.start()
    val res = process.waitFor()

    import scala.collection.JavaConversions.asScalaBuffer
    import scala.collection.mutable.Buffer

    val cmd:Buffer[String] = processBuider.command()
  }
}
