package javaa.spark.tuning;
/**
 * 数据本地化（数据跟要执行的代码再一个机器上）
 * 数据本地化级别：
 * 1：PROCESS_LOCAL:数据跟计算他的代码在同一个jvm进程中
 * 2：NODE_LOCAL:数据和计算他的代码在同一个节点上，不在同一个进程中，比如在不同的executor进程中，或者是数据在HDFS文件的block中。
 * 3：NO_PREF:数据从哪里过来，都一样
 * 4：RACK_LOCAL:数据和计算它的代码再一个机架上。
 * 5：ANY:数据肯那个在任意地方，比如其他的网络环境内，或者其他的机架上。
 * 
 * 参数调优：
 * spark.locality用来调节等待task可以进行数据本地化测得时间，
 * spark.locality.wait(3000ms),spark.locality.wait.node,spark.locality.wait.process,spark.locality.wait.rack
 *
 */
public class DataLocal {

}
