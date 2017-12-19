package javaa.storm;
/**
 * storm 应用场景
 * 推荐系统：实时推荐，根据下单或者加入购物车推荐相关商品（而不是相似商品）
 * 金融系统：
 * 预警系统：
 * 网站统计：实时销量，流量统计，如淘宝双11效果图
 * 
 * 
 * 基础部分：
 * 环境搭建
 * api熟悉，常用的spout,bolt详解
 * Grouping策略详解及案例实战
 * 并行度详解及实战案例
 * 线程安全控制机场景，方案分析
 * 综合案例开发结合企业场景
 * 批处理事务详解及案例实战开发
 * DRPC深入讲解及案例开发
 * 
 * 进阶编程：storm trident编程
 * trident state spout bolt
 * 
 * 
 * Storm集群架构
 * storm	Nimbus	Supervisor	topologies（拓扑），死循环
 * hadoop	JobTracker	TaskTracker	MapReduce Job，执行完自动结束
 * 
 * 通过zk链接
 * Nimbus  <-- zookeeper --> Supervisor
 * 
 * 工作原理：
 * Spout是数据源   ---Tuple Tuple ...---> Bolt(处理器) --Tuple Tuple Tuple--->Bolt
 */
public class WhatStorm {
	
}
