package javaa.flume;
/**
 * 监控一个文件，能够监控一个文件的追加
 *
 */
public class FlumeTail {
	/**
	 * ### define agent
	 * a2.sources = r2
	 * a2.channels = c2
	 * a2.sinks = k2
	 * 
	 * ### define sources
	 * a2.sources.r2.type = exec
	 * a2.sources.r2.command = tail -f /opt/cdh-5.3.6/hive-0.13.1-cdh5.3.6/logs/hive.log
	 * a2.sources.r2.shell = /bin/bash -c
	 * 
	 * ### define channels
	 * a2.channels.c2.type = memory
	 * a2.channels.c2.capacity = 1000
	 * a2.channels.c2.transactionCapacity = 100
	 * 
	 * ### define sink
	 * a2.sinks.k2.type = hdfs
	 * a2.sinks.k2.hdfs.path = hdfs://ns1/user/beifeng/flume/hive-logs/
	 * a2.sinks.k2.hdfs.fileType = DataStream 
	 * a2.sinks.k2.hdfs.writeFormat = Text
	 * a2.sinks.k2.hdfs.batchSize = 10
	 * 
	 * 
	 * ### bind the soures and  sink to the channel
	 * a2.sources.r2.channels = c2
	 * a2.sinks.k2.channel = c2
	 * 
	 */
}
