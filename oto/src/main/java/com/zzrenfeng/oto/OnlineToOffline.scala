package com.zzrenfeng.oto

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext

object OnlineToOffline{
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("o2o").setMaster("local")
    val sc = new SparkContext(conf)
    val sql = new SQLContext(sc)
    import sql.implicits._
    /**
     * 读取offline文件，使用csv直接读取，option设置hreader为true 直接使用第一行为Schema
     */
    val offline = sql.read.format("csv").option("header", "true").load("E:\\天池\\数据\\ccf_offline_stage1_train.csv")
    
    /**
     * 读取online文件
     */
    val onLine = sql.read.format("csv").option("header", "true").load("E:\\天池\\数据\\ccf_online_stage1_train.csv")
	
    /**
     * 读取需要预测的数据
     */
    val revised = sql.read.format("csv").option("header", "true").load("E:\\天池\\数据\\ccf_offline_stage1_test_revised.csv")
    
    
    
    
    
    /**
	 *  测试+1
	 */
    
    offline.printSchema
    onLine.printSchema
    revised.printSchema
}
  
}