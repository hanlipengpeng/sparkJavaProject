package file.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.Row;

public class T {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf();
		JavaSparkContext jsc= new JavaSparkContext(conf);
		SQLContext sqlContext = new SQLContext(jsc);
		DataFrame parquetFileDF = sqlContext.read().option("header", "true").parquet("hdfs://node1:8020/system/file15.par");
		parquetFileDF.registerTempTable("parquetFile");
		DataFrame namesDF = sqlContext.sql("select * from parquetFile");
		namesDF.take(1)[0].getString(0);	
		//sqlContext.read().parquet("hdfs://node1:8020/system/file15.par");
		
		
		/*val conf = new SparkConf
		val sc = new SparkContext(conf)
		val sqlContext = new SQLContext(sc)
		import sqlContext.implicits._
		val parquetFileDF = sqlContext.read.parquet("hdfs://node1:8020/system/file15.par")
		parquetFileDF.registerTempTable("parquetFile")
		val namesDF = sqlContext.sql("select * from parquetFile")
		val count = namesDF.take(1)(0).getAs[Int](0)*/
	
	
	}

}
