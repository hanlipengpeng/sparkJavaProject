package javaa.spark.machineLearning.mllib.collaborativeFiltering;

import javaa.spark.machineLearning.mllib.SVMsTest;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;

import scala.Tuple2;

/**
 * 协同过滤  推荐算法
 * @author 韩利鹏
 * 
 * 数据格式是  user product rating
 * 先训练模型，然后使用 user和product进行预测，然后用预测和rating进行对比
 *
 */
public class ALSTest {
    public static void main(String[] args){
        SparkConf conf = new SparkConf().setAppName("svm").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        String path = SVMsTest.class.getClassLoader().getResource("als/test.txt").getPath();
        
        //对数据进行分割处理
        JavaRDD<String> data = jsc.textFile(path);
        //user product rating
        JavaRDD<Rating> ratings = data.map(
		  new Function<String, Rating>() {
			private static final long serialVersionUID = 1L;

			public Rating call(String s) {
		      String[] sarray = s.split(",");
		      return new Rating(Integer.parseInt(sarray[0]), Integer.parseInt(sarray[1]),
		        Double.parseDouble(sarray[2]));
		    }
		  }
		);
        
        //build the recommendation model using ALS
        int rank = 10;
        int numInterations = 10;
        MatrixFactorizationModel model = ALS.train(JavaRDD.toRDD(ratings), rank, numInterations,0.01);
        
        //Evaluate the model on rating using ALS
        //用户和产品
        JavaRDD<Tuple2<Object,Object>> userProducts = ratings.map(new Function<Rating, Tuple2<Object,Object>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<Object, Object> call(Rating v1) throws Exception {
				
				return new Tuple2<Object, Object>(v1.user(), v1.product());
			}
		});
        
        JavaRDD<Tuple2<Tuple2<Integer, Integer>, Double>> map = model.predict(JavaRDD.toRDD(userProducts)).toJavaRDD().map(new Function<Rating, Tuple2<Tuple2<Integer,Integer>,Double>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<Tuple2<Integer, Integer>, Double> call(Rating r)
					throws Exception {
				return new Tuple2<Tuple2<Integer,Integer>, Double>(new Tuple2<Integer,Integer>(r.user(), r.product()),r.rating());
			}
		});
        
        //用户和推荐产品
        JavaPairRDD<Tuple2<Integer,Integer>,Double> predictions = JavaPairRDD.fromJavaRDD(map);
        
        //产品和推荐产品
        JavaRDD<Tuple2<Double,Double>> ratesAndPreds = 
        		JavaPairRDD.fromJavaRDD(ratings.map(new Function<Rating, Tuple2<Tuple2<Integer,Integer>,Double>>() {
					private static final long serialVersionUID = 1L;

					@Override
					public Tuple2<Tuple2<Integer, Integer>, Double> call(
							Rating r) throws Exception {
						return new Tuple2<Tuple2<Integer,Integer>, Double>(new Tuple2<Integer,Integer>(r.user(), r.product()), r.rating());
					}
		})).join(predictions).values();
        
        double MSE = JavaDoubleRDD.fromRDD(ratesAndPreds.map(new Function<Tuple2<Double,Double>, Object>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Double call(Tuple2<Double, Double> v1) throws Exception {
				Double err = v1._1 - v1._2;
				return err*err;
			}
		}).rdd()).mean();
        
        System.out.println("Mean Squared Error = "+MSE);
        
        //dave and load model
        String savePath = "F:\\tmp\\model\\als";
        model.save(jsc.sc(), savePath);
        //MatrixFactorizationModel sameModel = MatrixFactorizationModel.load(jsc.sc(),savePath);
        jsc.close();
        
    }

}
