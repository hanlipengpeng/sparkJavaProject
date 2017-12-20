package javaa.flume;
/**
 * flume监控文件夹
 * 
 */
public class FlumeSpooldir {
	/**
	 * ### define agent
	 * a3.sources = r3
	 * a3.channels = c3
	 * a3.sinks = k3
	 * 
	 * 
	 * ### define sources
	 * a3.sources.r3.type = spooldir
	 * a3.sources.r3.spoolDir = /opt/cdh-5.3.6/flume-1.5.0-cdh5.3.6/spoollogs
	 * a3.sources.r3.ignorePattern = ^(.)*\\.log$
	 * a3.sources.r3.fileSuffix = .delete
	 * 
	 * ### define channels
	 * a3.channels.c3.type = file
	 * a3.channels.c3.checkpointDir = /opt/cdh-5.3.6/flume-1.5.0-cdh5.3.6/filechannel/checkpoint
	 * a3.channels.c3.dataDirs = /opt/cdh-5.3.6/flume-1.5.0-cdh5.3.6/filechannel/data
	 * 
	 * ### define sink   需要添加时间的时候需要添加useLocalTimeStamp=true
	 * a3.sinks.k3.type = hdfs
	 * a3.sinks.k3.hdfs.path = hdfs://ns1/user/flume/splogs/%Y%m%d
	 * a3.sinks.k3.hdfs.fileType = DataStream 
	 * a3.sinks.k3.hdfs.writeFormat = Text
	 * a3.sinks.k3.hdfs.batchSize = 10
	 * a3.sinks.k3.hdfs.useLocalTimeStamp = true
	 * 
	 * 
	 * ### bind the soures and  sink to the channel
	 * a3.sources.r3.channels = c3
	 * a3.sinks.k3.channel = c3
	 *
	 *
	 */
}
