package javaa.spark.core;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

public class ActionReduce {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("action")
				.setMaster("local");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		jsc.setLogLevel("WARN");
		// reduce(jsc);
		// collet(jsc);
		//count(jsc);
		//take(jsc);
		//saveTextFile(jsc);
		//countByKey(jsc);
		foreach(jsc);
		jsc.stop();
	}
	
	//saveTextFile
	private static void saveTextFile(JavaSparkContext jsc) {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		JavaRDD<Integer> listRDD = jsc.parallelize(list);
		listRDD.saveAsTextFile("D:\\save.txt");
	}

	//countByKey
	private static void countByKey(JavaSparkContext jsc){
		List<Tuple2<Integer,Integer>> pariList = Arrays.asList(new Tuple2<Integer, Integer>(1,2),
				new Tuple2<Integer, Integer>(1,3),
				new Tuple2<Integer, Integer>(2,2),
				new Tuple2<Integer, Integer>(2,4),
				new Tuple2<Integer, Integer>(1,5));
		JavaPairRDD<Integer,Integer> pairRDD = jsc.parallelizePairs(pariList);
		Map<Integer,Object> kvMap = pairRDD.countByKey();
		for(Map.Entry<Integer,Object> e : kvMap.entrySet()){
			System.out.println(e.getKey()+" "+e.getValue());
		}
	}

	//foreach
	private static void foreach(JavaSparkContext jsc){
		List<Tuple2<Integer,Integer>> pariList = Arrays.asList(new Tuple2<Integer, Integer>(1,2),
				new Tuple2<Integer, Integer>(1,3),
				new Tuple2<Integer, Integer>(2,2),
				new Tuple2<Integer, Integer>(2,4),
				new Tuple2<Integer, Integer>(1,5));
		JavaPairRDD<Integer,Integer> pairRDD = jsc.parallelizePairs(pariList);
		pairRDD.foreach(new VoidFunction<Tuple2<Integer, Integer>>() {
			@Override
			public void call(Tuple2<Integer, Integer> integerIntegerTuple2) throws Exception {
				System.out.println(integerIntegerTuple2._1()+" "+integerIntegerTuple2._2());
			}
		});

	}
	
	

	// take
	private static void take(JavaSparkContext jsc) {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		JavaRDD<Integer> listRDD = jsc.parallelize(list);
		List<Integer> listTake = listRDD.take(3);
		for(Integer i : listTake){
			System.out.println(i);
		}
	}

	// count
	private static void count(JavaSparkContext jsc) {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		JavaRDD<Integer> listRDD = jsc.parallelize(list);
		long count = listRDD.count();
		System.out.println(count);
	}

	// collect
	private static void collet(JavaSparkContext jsc) {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		JavaRDD<Integer> listRDD = jsc.parallelize(list);
		List<Integer> listcollect = listRDD.collect();
		for (Integer i : listcollect) {
			System.out.println(i);
		}
	}

	// reduce
	private static void reduce(JavaSparkContext jsc) {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		JavaRDD<Integer> listRDD = jsc.parallelize(list);
		Integer count = listRDD
				.reduce(new Function2<Integer, Integer, Integer>() {

					@Override
					public Integer call(Integer v1, Integer v2)
							throws Exception {
						// TODO Auto-generated method stub
						return v1 + v2;
					}
				});
		System.out.println(count);
	}
}
