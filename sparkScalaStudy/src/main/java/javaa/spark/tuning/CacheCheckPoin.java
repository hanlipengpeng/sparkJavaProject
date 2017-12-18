package javaa.spark.tuning;
/**
 * 持久化操作
 * 
 * 使用cache 或者checkPoint
 * 当一个rdd的信息丢失会先去BlockManager读取cache数据，如果cache数据也丢失的话会去checkPoint去读取数据，如果也读取不到数据
 * 然后再回去重新计算
 *
 */
public class CacheCheckPoin {

}
