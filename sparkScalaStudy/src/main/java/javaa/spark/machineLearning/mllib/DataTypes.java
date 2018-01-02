package javaa.spark.machineLearning.mllib;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Matrices;
import org.apache.spark.mllib.linalg.Matrix;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;


/**
 * 机器学习的一些基本类型
 * @author rf
 *
 */
public class DataTypes {
	public static void main(String[] args) {
		
	}
	
	/**
	 * local vector
	 * 为什么向量只有本地化的向量呢？？？
	 */
	public static void localVector(){
		//稠密矩阵  dense vector
		Vector dv = Vectors.dense(1.0,0.0,3.0);
		//稀疏矩阵  sparse vector
		Vector sv = Vectors.sparse(3, new int[]{0,2}, new double[]{1.0,3.0});
	}
	
	/**
	 * labeledPoint 标记点,   使用localvector创建
	 */
	public static void lablePoint(){
		LabeledPoint pos = new LabeledPoint(1.0, Vectors.dense(1.0,0.0,3.0));
		
		LabeledPoint neg = new LabeledPoint(0.0, Vectors.sparse(3, new int[]{0,2},new double[]{1.0,3.0}));
		
	}
	
	/**
	 * 局部矩阵 local matrix,使用的是csc压缩矩阵。
	 */
	public static void localMatrix(){
		Matrix dm = Matrices.dense(3,2,new double[]{1.0,2.0,3.0,4.0,5.0,6.0});
		/**
		 * (9.0,0.0)
		 * (0.0,8.0)
		 * (0.0,6.0)
		 *  1    2
		 *
		 *  colPtrs:是用来计算每列有多少个数据的 colPtrs:（0,1,3）=>(1-0,3-1) = (1,2)  即第一行有一个数据，第二行有两个数据
		 *  rowIndices:是数据在哪一行上，根据上面的一块做就是，第一列有一个数据，在第一行上，第二列有两个数据，分别在第三行和第二行。
		 *
		 *  参数：numrows numcols colPtrs rowLndices value
		 */
		Matrix sm = Matrices.sparse(3,2,new int[]{0,1,3},new int[]{0,2,1},new double[]{9,6,8});

		
	}
	
	/**
	 * 分布矩阵 distributed matrix
	 */
	public static void distributedMatrix(){
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 * sparse data 
	 * 从文件件中读取数据，形成labeledPoint
	 */
	public static void sparsedata(JavaSparkContext jsc){
		JavaRDD<LabeledPoint> examples = MLUtils.loadLibSVMFile(jsc.sc(), "data/mllib/sample_libsvm_data.txt").toJavaRDD();
	}
	
	

}
