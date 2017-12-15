package scala.spark.core

import scala.math.Ordered
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

/**
 * scala 自定义二次排序
 * 还可以使用sortBy
 * case class跟不是case 的class有哪些区别(自动的有apply方法，生成对象的时候不需要使用new关键字，其他的还待发现)
 */
object SecondarySort{
  def main(args: Array[String]) {
  
    val conf = new SparkConf().setAppName("sort").setMaster("local")
    val sc = new SparkContext(conf)
    val file = sc.textFile(SecondarySort.getClass().getClassLoader().getResource("sort.txt").getPath(), 1)
    
    val sort = file.sortBy(line=>SecondSortKey(line.split(" ")(0).toInt,line.split(" ")(1).toInt), true, 2)
    sort.foreach(println(_))
    
    //val pair = file.map(line=>(new SecondSortKey(line.split(" ")(0).toInt,line.split(" ")(1).toInt),line))
    //val sort = pair.sortByKey(true)
    //sort.map(_._2).foreach(println(_))
    
    
    sc.stop
    
}
}

case class SecondSortKey(val first:Int, val second:Int) extends Ordered[SecondSortKey] with Serializable{
  override def compare(that:SecondSortKey):Int={
    val i = this.first - that.first
    if(i==0){
      return this.second-that.second
    }else{
      return i;
    }
  }
}