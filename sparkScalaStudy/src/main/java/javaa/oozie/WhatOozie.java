package javaa.oozie;
/**
 * oozie任务流调度框架
 * oozie需要部署到java Servlet容器中运行的
 * 工作流：
 * 
 * 
 * 调度：
 * 定时任务，触发可以是时间，也可以是数据集（有数据触发），
 *
 */
public class WhatOozie {
	/**
	 * 调度框架：
	 * 1：crontab 最简单的任务调度框架         不能回滚
	 * 2：Azkaban hdoop的
	 * 3：oozie   cloudera贡献给spache的      etl特别好
	 * 4：zeus 阿里开源的
	 * 
	 * oozie是通过xml语言来定义工作流的。
	 * oozie定义了控制流节点和动作节点，其中控制流节点定义了流程的开始和节数，以及控制流程的执行路径。
	 * 控制流程的执行路径，如decision，fork，jion等，
	 * 动作节点包括hadoop map-reduce，hadoop文件系统，pig，ssh，http，email和oozie子流程
	 * 
	 * 针对不同类型的任务，workflow模板去套用
	 * 
	 * 
	 */

}
