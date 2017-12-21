package file.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext

object TT {
  def main(args: Array[String]) {

    val conf = new SparkConf
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._
    val parquetFileDF = sqlContext.read.parquet("hdfs://node1:8020/system/file15.par")
    parquetFileDF.registerTempTable("parquetFile")
    val namesDF = sqlContext.sql("select * from parquetFile")
    val count = namesDF.take(1)(0).getAs[Int](0)

  }

}