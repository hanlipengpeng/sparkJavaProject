package javaa.spark.tuning;
/**
 * 共享变量
 * 什么时候需要使用共享变量，在算子函数使用了共有变量
 * 变量使用的时候在每个task都去去拷贝一份数据，
 * 使用broadcast共享数据是每个节点上一份数据
 *
 */
public class BroadCastTuning {

}
