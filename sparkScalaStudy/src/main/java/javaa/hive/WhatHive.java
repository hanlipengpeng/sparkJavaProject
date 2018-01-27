package javaa.hive;
/**
 * Hive 中包含以下数据模型：DB、Table，External Table，Partition，Bucket。
 * 
 *
 */
public class WhatHive {
	
	
/**
 * Hive 中包含以下数据模型：DB、Table，External Table，Partition，Bucket。
 * 
 * db：在hdfs中表现为${hive.metastore.warehouse.dir}目录下一个文件夹
 * table：在hdfs中表现所属db目录下一个文件夹
 * external table：外部表, 与table类似，不过其数据存放位置可以在任意指定路径
 * 普通表: 删除表后, hdfs上的文件都删了  .   External外部表删除后, hdfs上的文件没有删除, 只是把文件删除了
 * partition：在hdfs中表现为table目录下的子目录
 * bucket：桶, 在hdfs中表现为同一个表目录下根据hash散列之后的多个文件, 会根据不同的文件把数据放到不同的文件中 
 * 
 * 
 */

}
