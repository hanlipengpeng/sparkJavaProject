package scala.scalaJava

import java.io._

import scala.io.Source
/**
  * Created by root on 2017/12/10.
  */
class FileTest {

}
object FileTest{
  def main(args: Array[String]): Unit = {
    val file = Source.fromFile(FileTest.getClass.getClassLoader.getResource("sougou.txt").getPath)
    val lines = file.getLines()
    for(line <-lines){
      println(line)
    }
    lines.map(println(_))
  }
  def readFile1={
    val source = Source.fromFile("c://text","utf-8")
    val lines = source.getLines().toArray
    for(line<-lines) println(line)
  }
  def readFile2={
    val source = Source.fromFile("")
    source.mkString
    source.close()
  }
  def html = Source.fromURL("http:www.baidu.com","UTF-8")
  def str = Source.fromString("Hello world")

  /**
    * 使用java来读取文件
    * 序列化  java的序列化
    */
  def readObject: Unit ={
    case class Person(val name:String) extends Serializable
    val leo = new Person("leo")

    val oos = new ObjectOutputStream(new FileOutputStream(""))
    oos.writeObject(leo)
    oos.close()

    val ois = new ObjectInputStream(new FileInputStream(""))
    val restoredLeo = ois.readObject().asInstanceOf[Person]
    restoredLeo.toString
  }


}

