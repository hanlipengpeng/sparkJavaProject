package javaa.hadoop.yarn;
/**
 * yarn
 * 
 * ResourceManager
 * 		处理客户端请求
 * 		启动/监控AppliocationMaster
 * 		监控NodeManager
 * 		资源分配与调度
 * 
 * NodeManager
 * 		单个节点上的资源管理
 * 		处理来自ResouceManager的命令
 * 		处理来自ApplicationMaster的命令
 * 
 * Application Master
 * 		数据切分
 * 		为应用申请资源，并分配给内部任务
 * 		任务监控和容错
 *   
 * Container
 * 		对任务运行环境的抽象，
 * 		封装了cpu，内存等多维资源以及环境变量，启动命令等任务相关的信息
 * @author rf
 *
 */
public class WhatYarn {

}
