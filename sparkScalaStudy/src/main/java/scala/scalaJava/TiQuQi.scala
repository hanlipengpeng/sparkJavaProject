package scala.scalaJava

/**
  * Created by root on 2017/12/10.
  */
class TiQuQi {

}
object TiQuQi{
  def main(args: Array[String]): Unit = {

  }
}

class PersonT(name:String,age:Int){
  val this.name=name
  val this.age=age
}
object PersonT{
  //把filed生成对象
  def apply(name: String, age: Int): PersonT = new PersonT(name, age)

  //把对象反转为filed
  def unapply(arg: PersonT): Option[(String, Int)] = {
    //待解决，对于person为什么不能拿到他的filed
    return None
  }
}
