package javaa.flume;
/**
 * 数据采集
 * 
 * flume架构图
 * webServer -->Agent(Source,Channel,Sink)  -->HDFS
 * Channel是使用队列来缓存数据的，具有先进先出的原则
 * Source --push-->Channel <--Poll--Sink
 * 
 * 
 * 运行环境：
 * 运行在logs地方
 * 系统 linux
 * jvm
 */
public class WhatFlume {
	/**
	 * flume配置步骤（通用简化版）
	 * 1：定义一个agent
	 * 2：定义source
	 * 3：定义Channel
	 * 4：定义Sink
	 * 5：绑定
	 * 
	 * ### define agent 定义一个agent，
	 * a1.sources = r1
	 * a1.channels = c1
	 * a1.sinks = k1
	 * 
	 * ### define sources 定义一个source
	 * a1.sources.r1.type = netcat
	 * a1.sources.r1.bind = hadoop-senior.ibeifeng.com
	 * a1.sources.r1.port = 44444
	 * 
	 * ### define channels 定义一个channel
	 * a1.channels.c1.type = memory
	 * a1.channels.c1.capacity = 1000
	 * a1.channels.c1.transactionCapacity = 100
	 * 
	 * 
	 * ### define sink 定义一个sink
	 * a1.sinks.k1.type = logger
	 * a1.sinks.k1.maxBytesToLog = 2014
	 * 
	 * ### bind the soures and  sink to the channel  进行绑定
	 * a1.sources.r1.channels = c1
	 * a1.sinks.k1.channel = c1
	 * 
	 * 
	 * 启动flume $FLUME_HOME/bin/flume-ng -c conf -n a1 -f conf/a1.conf         -Dflume.root.logger=INFO,console
	 * 
	 * 对于每一个source chennel sink的配置，前面都是一样的，只是后面不一样，后面的可以参考官网的手册，看都需要配置哪些属性
	 * http://flume.apache.org/releases/content/1.6.0/FlumeUserGuide.html
	 * 
	 */

}
