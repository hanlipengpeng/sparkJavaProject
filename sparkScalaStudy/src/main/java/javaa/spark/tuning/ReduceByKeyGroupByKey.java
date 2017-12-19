package javaa.spark.tuning;
/**
 * val counts = pairs.reduceByKey(_+_)
 * val counts = pairs.groupByKey().map(wordCounts=>(wordCounts._1,wordCounts._2.sum))
 * 如果能用reduceByKey实现的就用reduceByKey,因为他会在map端，先进性一个本地的combine,可以大大的减少要传输的数量，减少网络，io的开销
 * 只有在reduceByKey处理不了的时候，采用groupByKey().map()来替代
 * 
 * 
 * 
 * shuffle性能调优
 * conf.set("spark.shuffle.consolidataFiles","true")  对于同一个cpu core 只产生一个task的文件数量，第二个task产生的文件会直接输出到之前的文件中，从而减少文件的读写，默认为false
 * conf.set("spark.shuffle.maxSizeInFile","48m") reduce task的拉取缓存，默认48m，增大可以减少拉取的次数
 * conf.set("spark.shuffle.file.buffer","32k") map task的写磁盘缓存，默认32k。增大能减少溢写次数
 * conf.set("spark.shuffle.io.maxRetries","3") 拉取失败的最大重试次数，默认3次。gc的时候会停止服务，导致拉取不响应，就失败了
 * conf.set("spark.shuffle.io.retryWait","5s") 拉取失败的重试间隔，默认是5s
 * conf.set("spark.shuffle.memoryFeaction","0.2") 用于reduce端聚合的内存比例，默认0.2.超过比例就会溢出到磁盘上
 *
 */
public class ReduceByKeyGroupByKey {
	public static void main(String[] args) {
		//JAMailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
	}
}
