package scala.scalaJava

import scala.annotation.varargs
import scala.beans.{BeanProperty, BooleanBeanProperty}

/**
  * Created by root on 2017/12/10.
  *
  */
@BooleanBeanProperty //标记生成is风格的getter方法，用于boolean类型的field
@BeanProperty   //用于标记生成java风格的setter getter方法
@SerialVersionUID(1L) class AnnoutionTest {
  @volatile var name = "leo" //轻量级的java多线程并发安全控制
  @transient var name1="leo"  //瞬态字段，不会序列化这个字段
  //@native  //标注用c实现的本地方法
  @varargs def test (args:String*){}  //标记可变字长的


  /**
    *
    */

}
object AnnoutionTest{
  @unchecked //让编译器提示类型转换的警告
  @deprecated(message ="让发出警告")
  @throws(classOf[Exception]) def main(args: Array[String]): Unit = {

  }
}
