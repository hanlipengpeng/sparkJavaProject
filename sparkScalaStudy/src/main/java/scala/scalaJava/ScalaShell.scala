package scala.scalaJava

import sys.process._

/**
  * Created by root on 2017/12/10.
  */
class ScalaShell {

}
object ScalaShell{
  def main(args: Array[String]): Unit = {
    //使用！执行shell命令，可能执行windows的时候会出现问题
    "dir .." !
  }
}

