package scala.scalaJava.listTest

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
/**
 * groupBy groupByKey reduce reduceByKey
 */
object GroupByReduce{
  def main(args: Array[String]) {
	  val conf = new SparkConf().setAppName("group reduce").setMaster("local")
	  val sc = new SparkContext(conf)
	  sc.setLogLevel("WARN")
	  groupByTest(sc)
	  groupByKeyTest(sc)
	  reduceTest(sc)
	  reduceByKeyTest(sc)
	  sc.stop
}
  /**
   * groupBy 根据算子的返回值来分组，返回的是tuple2的形式，第一个是算子的返回值，后面是返回值相同的数据集
   */
  def groupByTest(sc:SparkContext)={
    val numRdd = sc.parallelize(List(1,2,3,4,5,6,7,8,9), 2)
    val groupByT = numRdd.groupBy(line => if(line%3==0) "0" else if(line%3==1) "1" else "2")
    groupByT.foreach(f=>println(f._1+"	"+f._2.toList.toString))
    /**
     * 结果：余数相同的进行分组，前面是余数，后面是集合
     * 0	List(3, 6, 9)
     * 2	List(2, 5, 8)
     * 1	List(1, 4, 7)
     */
  }
  /**
   * groupByKey rdd需要时pariRDD，也就是里面试tuple2的数据，根据tuple里面的第一个数据进行聚合
   * 返回的也是一个tuple(key,iterator),第一个是聚合的key，后面是相同key的value集，以迭代器的形式返回
   */
  def groupByKeyTest(sc:SparkContext)={
    val wordRDD = sc.parallelize(List("han","lipeng","liu","xiaopeng","xiaoli","xiaoming","scala","java","zhang","wang","dog"), 2)
    val leanthWord = wordRDD.keyBy(_.length())
    val groupByKeyT = leanthWord.groupByKey
    groupByKeyT.foreach(f=>println(f._1+"	"+f._2.toList.toString))
    /**
     * 结果：前面是单词字符里面有多少字符，后面是相同字符的单词
     * 4	List(java, wang)
     * 6	List(lipeng, xiaoli)
     * 8	List(xiaopeng, xiaoming)
     * 3	List(han, liu, dog)
     * 5	List(scala, zhang)
     */
  }
  /**
   * reduce是一个action操作
   * reduce多有数据进行reduce里面的算子操作
   */
  def reduceTest(sc:SparkContext)={
    val numRdd = sc.parallelize(List(1,2,3,4,5,6,7,8,9), 2)
    //val reduceT = numRdd.reduce(_+_) 跟下面一行意思一样
    val reduceTT = numRdd.reduce((a,b)=>a+b)
    println(reduceTT)
    /**
     * 结果：所有数据之和
     * 45
     */
  }
  /**
   * reduceByKey 要求rdd是pariRDD，也就是说是tuple2(key,value)的形式，根据key进行分组，然后key相同的value进行算子函数计算。
   * 返回的还是tuple2的形式，key还是原来的数据，value是进行了算子函数的操作后得到的值
   */
  def reduceByKeyTest(sc:SparkContext)={
    val numRdd = sc.parallelize(List(1,2,3,4,5,6,7,8,9), 2)
    val yuNum = numRdd.keyBy(line=>line%3)
    val reduceByKey = yuNum.reduceByKey(_+_)
    reduceByKey.foreach(line=>println(line._1+"	"+line._2))
    /**
     * 结果：前面是余数，后面是所有等于这个余数的和
     * 0	18
     * 2	15
     * 1	12
     */
  }
}