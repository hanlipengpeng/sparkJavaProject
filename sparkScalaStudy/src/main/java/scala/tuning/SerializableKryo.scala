package scala.tuning

import org.apache.spark.SparkConf

class SerializableKryo {

}
object SerializableKryo{
  def main(args: Array[String]) {
   val conf = new SparkConf().setMaster("local").setAppName("Kryo")
   conf.registerKryoClasses(Array(classOf[Person]))
  }
}
case class Person extends Serializable()