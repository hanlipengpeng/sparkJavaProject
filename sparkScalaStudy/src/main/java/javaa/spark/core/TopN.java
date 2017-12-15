package javaa.spark.core;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

/**
 * topn的时候不能使用sort之后取top，应该是sort之后使用take，
 * 使用top的时候是使用了隐式转换，直接传入了另一个排序规则，
 * 如果想使用top的话可以自定top的排序规则
 * 
 * 分组求取topn
 *
 * 需要考虑的是top的算法跟sort的算法效率是不是一样的，如果是一样的，要怎么才能高效的求取topn？？？
 */
public class TopN {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("broadcast").setMaster("local");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		jsc.setLogLevel("WARN");
		JavaRDD<String> file = jsc.textFile(SortTest.class.getClassLoader().getResource("sort.txt").getPath());
		JavaPairRDD<SecondarySortKey,String> pairKV = file.mapToPair(new PairFunction<String, SecondarySortKey, String>() {

			@Override
			public Tuple2<SecondarySortKey, String> call(String t)
					throws Exception {
				String[] split = t.split(" ");
				return new Tuple2<SecondarySortKey, String>(new SecondarySortKey(Integer.parseInt(split[0]),Integer.parseInt(split[1])), t);
			}
		});
		JavaRDD<String> sortRDD = pairKV.sortByKey().map(new Function<Tuple2<SecondarySortKey,String>, String>() {

			@Override
			public String call(Tuple2<SecondarySortKey, String> v1)
					throws Exception {
				return v1._2;
			}
		});
		
		List<String> list = sortRDD.take(20);
		for(String s :list){
			System.out.println(s);
		}
		System.out.println("**********************************");
		groupTopN(jsc);
		jsc.close();
	}
	/**
	 * 分组求取topn
	 * @param jsc
	 */
	private static void groupTopN(JavaSparkContext jsc){
		JavaRDD<String> file = jsc.textFile(SortTest.class.getClassLoader().getResource("sort.txt").getPath());
		JavaPairRDD<Integer,Integer> pairKV = file.mapToPair(new PairFunction<String, Integer, Integer>() {

			@Override
			public Tuple2<Integer, Integer> call(String t) throws Exception {
				String[] str = t.split(" ");
				return new Tuple2<Integer, Integer>(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
			}
		});
		JavaPairRDD<Integer,Iterable<Integer>> groupRDD = pairKV.groupByKey();
		JavaPairRDD<Integer,Iterable<Integer>> groupTop = groupRDD.mapToPair(new PairFunction<Tuple2<Integer,Iterable<Integer>>, Integer, Iterable<Integer>>() {

			@Override
			public Tuple2<Integer, Iterable<Integer>> call(
					Tuple2<Integer, Iterable<Integer>> t) throws Exception {
				Integer k = t._1;
				Iterator<Integer> iter = t._2.iterator();
				Integer[] top = new Integer[3];
				while(iter.hasNext()){
					Integer value = iter.next();
					for(int i = 0;i<3;i++){
						if(top[i] ==null){
							top[i] = value;
							break;
						}else if(value>top[i]){
							top[i] = value;
						}
					}
				}
				return new Tuple2<Integer, Iterable<Integer>>(k, Arrays.asList(top));
			}
		});
		
		groupTop.foreach(new VoidFunction<Tuple2<Integer,Iterable<Integer>>>() {
			
			@Override
			public void call(Tuple2<Integer, Iterable<Integer>> t) throws Exception {
				System.out.println(t._1);
				Iterator< Integer> i = t._2.iterator();
				while(i.hasNext()){
					System.out.println(i.next());
				}
				System.out.println("================================");
				
			}
		});
	}
}
