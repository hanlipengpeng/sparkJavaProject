package scala.spark.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
/**
 * scala 版本的wordcount
 */
object HelloSpark {
  def main(args: Array[String]) {
  
    val conf = new SparkConf().setMaster("local").setAppName("helloSpark")
    val sc = new SparkContext(conf)
    val file = sc.textFile(HelloSpark.getClass().getClassLoader().getResource("sougou.txt").getPath(), 2)
    val words = file.flatMap(line=>line.split(" "))
    val pair = words.map(word=>(word,1))
    val reduce = pair.reduceByKey(_+_)
    reduce.foreach(kv => println("word:"+kv._1+" count:"+kv._2))
}
}