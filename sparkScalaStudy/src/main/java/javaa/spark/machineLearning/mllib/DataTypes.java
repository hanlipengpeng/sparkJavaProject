package javaa.spark.machineLearning.mllib;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Matrices;
import org.apache.spark.mllib.linalg.Matrix;
import org.apache.spark.mllib.linalg.QRDecomposition;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.linalg.distributed.BlockMatrix;
import org.apache.spark.mllib.linalg.distributed.CoordinateMatrix;
import org.apache.spark.mllib.linalg.distributed.IndexedRow;
import org.apache.spark.mllib.linalg.distributed.IndexedRowMatrix;
import org.apache.spark.mllib.linalg.distributed.MatrixEntry;
import org.apache.spark.mllib.linalg.distributed.RowMatrix;
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
	 * 局部矩阵具有存储在单个机器上    整数   类型的行和列索引以及双重类型的值。
	 * MLLIB支持密集矩阵，其输入值按列住顺序存储在单个double数组中；
	 * 稀疏矩阵的非零输入值按列主要顺序存储在压缩稀疏列（CSC）格式中。
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
	 * 分布式矩阵具有    长型    的行和列索引和双重类型的值，分布式地存储在一个或多个RDD中。
	 * 选择正确的格式来存储大型和分布式矩阵非常重要，
	 * 将分布式矩阵转换为不同的格式可能需要全局洗牌，这相当耗费资源。目前已经实现了三种分布式矩阵。
	 * 
	 * 基本类型的矩阵为 RowMatrix，RowMatrix是没有意义的行索引的面相行的分布式矩阵，例如特征向量的集合。他是有行的RDD支持，每行都是一个本地的向量。我们假定列的数量并不是很大，
	 * RowMatrix因此可以将一个局部向量合理的转给驱动程序，也可以使用单个节点进行存储行存储操作。
	 * IndexedRoeMatrix 类似于一个RowMatrix，担忧行索引，可以用来标识行和执行连接。
	 * CoordinateMatrix是以坐标列表（COO）格式存储的分布式矩阵，有气条目的RDD支持。
	 */
	public static void distributedMatrix(){
		/**
		 * RowMatrix
		 * RowMatrix 是一个面向行的分布式矩阵，没有有意义的行索引，由行的RDD支持，每行都是局部向量。
		 * 由于每一行有一个局部向量表示，所以咧的数量受整数范围限制，但是设计上应该小的多。
		 */
		JavaRDD<Vector> rows = null;
		//create a rowMatrix from an JavaRDD<Vector>.
		RowMatrix mat = new RowMatrix(rows.rdd());
		//get its size
		long m = mat.numCols();
		long n = mat.numRows();
		//QR decomposition   QR分解
		QRDecomposition<RowMatrix,Matrix> result = mat.tallSkinnyQR(true); 
		
		
		/**
		 * indexedRowMatrix
		 * IndexedRowMatrix 类似于 RowMtrix但有意义的行索引。它由索引行的RDD支持，因此每行都由索引（long-type）和局部向量表示。
		 */
		JavaRDD<IndexedRow> irows = null;
		//create an IndexedRowMatrix from a JavaRDD<IndexedRow>
		IndexedRowMatrix imat = new IndexedRowMatrix(irows.rdd());
		//Get its size.
		long im = imat.numRows();
		long in = imat.numCols();
		//Drop its row indices.
		RowMatrix rowMat = imat.toRowMatrix();
		
		
		/**
		 * CoordinateMatrix
		 * A CoordinateMatrix 是由其条目的RDD支持的分布式矩阵。每个条目是一个元组（i:Long，j:Long，value:Double），
		 * 其中i行索引j是列索引，并且value是条目值。CoordinateMatrix只有当矩阵的两个维度都很大且矩阵非常稀疏时才使用。
		 */
		JavaRDD<MatrixEntry> entries = null;
		CoordinateMatrix cmat = new CoordinateMatrix(entries.rdd());
		long cm = cmat.numRows();
		long cn = cmat.numCols();
		//convert it to an indexRowMatrix whose rows are aparse vectors.
		IndexedRowMatrix indexedRowMatrix = cmat.toIndexedRowMatrix();
		
		
		/**
		 * BlockMatrix
		 * BlockMatrix是通过的RDD支持的分布式矩阵MatrixBlocks，其中一个MatrixBlock是一个元组((Int,Int),Matrix),其中(Int,Int)是block的索引，
		 * 并且Matrix是大小与所述给定索引出的子矩阵rowsPerBlockX colsPerBlock。
		 * toBlockMatrix默认创建大小为1024 X 1024的块
		 */
		//Transfrom the coordinateMatrix to a BlockMatrix
		BlockMatrix matA = cmat.toBlockMatrix().cache();
		
		//Validate whether the BlockMatrix is set up properly.Throws an Exception when it is not valid
		//Nothing happens if it is valid
		matA.validate();
		//Calculate A^T A.
		BlockMatrix ata = matA.transpose().multiply(matA);
		
		
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 * sparse data 
	 * 从文件件中读取数据，形成labeledPoint
	 */
	public static void sparsedata(JavaSparkContext jsc){
		JavaRDD<LabeledPoint> examples = MLUtils.loadLibSVMFile(jsc.sc(), "data/mllib/sample_libsvm_data.txt").toJavaRDD();
	}
	
	

}
