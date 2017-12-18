package javaa.spark.tuning;

import java.io.Serializable;

/**
 * 1：使用高性能的序列化类库
 * 2：优化数据结构
 * 3：对多次使用的RDD进行持久化/checkpoint
 * 4：使用学历恶化的持久化级别
 * 5：java虚拟机垃圾回收调优
 * 6:提高并行度
 * 7：广播变量共享数据
 * 8：数据本地化
 * 9：reudceByKey和groupByKey的合理使用
 * 10：shuffle调优（核心调优）
 *
 */
public class Memory {
	
}
