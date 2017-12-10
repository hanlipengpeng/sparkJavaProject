package scala.scalaJava

/**
  * Created by root on 2017/12/10.
  * 正则表达式
  */
class RegexTest {

}
object RegexTest{
  def main(args: Array[String]): Unit = {
    val patten1 = "[a-z]+".r
    val str ="hello xiaopengpeng 666,you are so bt"
    for(str <-patten1.findAllIn(str)){//这里是关键部分，只需要修改这里
      println(str)
    }
  }
}

