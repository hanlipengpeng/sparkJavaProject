package javaa.spark.tuning;
/**
 * 资源分配问题
 * 1：设置并行度，默认的是根据block的数量来做划分的，有多少个clock就会划分为多少个partition
 * 2：配置默认的partition数量，使用conf.set("spark.default.parallelism","60")
 * 一般为每个cpu core设置2~3个task，来提升资源利用
 * @author rf
 *
 */
public class ResourceAllocation {

}
