/**
 * ---------------------------------------------------------------------------
 * 类名称   ：KitConstant
 * 类描述   ：
 * 创建人   ： xue.yi
 * 创建时间： 2016/10/28 11:40
 * 版权拥有：星电商科技
 * ---------------------------------------------------------------------------
 */
package com.iih5.engine.clusterkit;

import java.util.UUID;

public class KitConstant {
    //-------------------------全局变量----------------------------
    /**服务内部操作命令，不针对用户*/
    public static final String CMD = "CMD";
    /**广播监听（针对群、组的广播）*/
    public static final String BROADCAST = "BROADCAST";
    /**主机节点唯一ID*/
    public static  final String CLUSTER_HOST_NODE_ID = UUID.randomUUID().toString();;

    //---------------------Redis实例用途分类-----------------------
    /**专用于数据缓存的redis*/
    public static final String CLUSTER_CACHE_REDIS = "CLUSTER_CACHE_REDIS";

    //-----------------------Redis存储键值--------------------------
    /**在线用户信息表*/
    public static final String CLUSTER_USER ="CLUSTER_USER:";
    /**主机信息表*/
    public static final String CLUSTER_HOST_NODE = "CLUSTER_HOST_NODE";
    /**主机用户信息表*/
    public static final String CLUSTER_HOST_NODE_USER = "CLUSTER_HOST_NODE_USER:";
    /**集群清洁员*/
    public static final String CLUSTER_CLEANER = "CLUSTER_CLEANER";
}
