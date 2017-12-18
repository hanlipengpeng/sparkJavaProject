package javaa.spark.tuning;
/**
 * java虚拟机垃圾回收调优
 * 监控：我们可以对垃圾回收进行检测，包括多久进行一次垃圾回收，以及每次的耗费时间，
 * 需要在spark-submit脚本中增加一个配置即可， 
 * --conf "spark.executor.extra.JavaOptions=-verbose:go -XX:+printGCDetails -XX:+PrintGCTimeStamps"
 * 这个日志信息是打印在worker上的日志中，不是driver的日志中
 * 也可以通过SparkUI(4040端口)来观察每个stage的垃圾回收情况
 * 1：减少内存消耗
 * 2：调节executor内存占比。默认会给cache 60%的内存
 * 调低比例：可以是用conf.set("spark.storage.momoryFaction",0.5) 默认是0.6
 * 3：java的堆内存设置，（了解垃圾回收的机制）
 * 使用-Xmn 来设置Eden区的内存大小，一个executor有4个task，hdfs文件解压之后是解压前的3倍，一般使用 4*3*64*4/3
 * 
 * -XX:SurvivorRatio=4
 * 如果值为4，那么就是两个Survivor跟Eden的比例是2:4 也就是每一个Survivor占据的年轻代的比例是1/6.
 * 
 * -XX:NewRatio=4 调节新生代跟老年代的比例
 *
 */
public class JVMtuning {

}
