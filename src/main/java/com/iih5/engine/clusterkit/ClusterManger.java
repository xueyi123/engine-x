/**
 * ---------------------------------------------------------------------------
 * 类名称   ：ClusterManger
 * 类描述   ：
 * 创建人   ： xue.yi
 * 创建时间： 2016/10/31 10:45
 * 版权拥有：星电商科技
 * ---------------------------------------------------------------------------
 */
package com.iih5.engine.clusterkit;

import com.alibaba.fastjson.JSON;
import com.iih5.smartorm.cache.Redis;
import com.iih5.smartorm.kit.SpringKit;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class ClusterManger {
    Logger logger = LoggerFactory.getLogger(ClusterManger.class);
    /**
     * 【注册主机信息】
     *
     * @param ip
     * @param port
     * @param nodeId
     */
    public void registerNode(String ip, Integer port, String nodeId) {
        ClusterNode node = new ClusterNode();
        node.setPingTime(System.currentTimeMillis());
        node.setHostNodeId(nodeId);
        node.setIp(ip);
        node.setPort(port);
        Redis.hset(KitConstant.CLUSTER_HOST_NODE, node.getHostNodeId(), JSON.toJSONString(node));

    }

    /**
     * 【注册主机信息】
     *
     * @param port
     */
    public void registerNode(Integer port) {
        try {
            registerNode(InetAddress.getLocalHost().getHostAddress(), port, KitConstant.CLUSTER_HOST_NODE_ID);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 【注册分布式用户】Hello, World!
     *
     * @param uid
     */
    public boolean registerUser(String uid) {
        String st = Redis.set(KitConstant.CLUSTER_USER + uid, KitConstant.CLUSTER_HOST_NODE_ID);
        Long lt = Redis.hset(KitConstant.CLUSTER_HOST_NODE_USER + KitConstant.CLUSTER_HOST_NODE_ID, uid, uid);
        if (st.equals("OK") && lt > 0) {
            return true;
        }
        return false;
    }

    /**
     * 【解除分布式注册用户】
     *
     * @param uid
     * @return
     */
    public boolean unRegisterUser(String uid) {
        Long lt = Redis.del(KitConstant.CLUSTER_USER + uid);
        Long lt2 = Redis.hdel(KitConstant.CLUSTER_HOST_NODE_USER + KitConstant.CLUSTER_HOST_NODE_ID, uid);
        if (lt2 > 0 && lt > 0) {
            return true;
        }
        return false;
    }

    /**
     * 清除无效的在线信息，避免用户状态（在线/离线。）不准确
     */
    public void invalidOnlineCheck() {
        String d = Redis.get(KitConstant.CLUSTER_CLEANER);
        if (StringUtils.isNotBlank(d)) {
            ClusterNode node = JSON.parseObject(d, ClusterNode.class);
            if (KitConstant.CLUSTER_HOST_NODE_ID.equals(node.getHostNodeId())) {
                Map<String, String> map = Redis.hgetAll(KitConstant.CLUSTER_HOST_NODE);
                for (String key : map.keySet()) {
                    String value = map.get(key);
                    node = JSON.parseObject(value, ClusterNode.class);
                    if (node.getPingTime() <= 0) {
                        String nodeId = node.getHostNodeId();
                        delInvalidUserOnline(nodeId);
                    }else{
                        node.setPingTime(0L);
                        Redis.hset(KitConstant.CLUSTER_HOST_NODE,node.getHostNodeId(),JSON.toJSONString(node));
                    }
                }
            }
        }
    }
    private void delInvalidUserOnline(String nodeId) {
        Map<String, String> userMap = Redis.hgetAll(KitConstant.CLUSTER_HOST_NODE_USER + nodeId);
        if (userMap == null || userMap.size()==0){
            Redis.hdel(KitConstant.CLUSTER_HOST_NODE,nodeId);
            return;
        }
        for (String uid : userMap.keySet()) {
            String hid = Redis.get(KitConstant.CLUSTER_USER + uid);
            if (nodeId.equals(hid)) {
                Jedis jedis = null;
                try{
                    jedis = SpringKit.getJedisPool(KitConstant.CLUSTER_CACHE_REDIS).getResource();
                    jedis.watch(KitConstant.CLUSTER_USER + uid);
                    Transaction tr = jedis.multi();
                    tr.del(KitConstant.CLUSTER_USER + uid);
                    tr.exec();
                    jedis.unwatch();
                }catch (Exception e){
                    logger.warn("操作异常",e);
                }finally {
                    if (jedis != null){
                        jedis.close();
                    }
                }
            }
        }
        Redis.del(KitConstant.CLUSTER_HOST_NODE_USER + nodeId);
        Redis.hdel(KitConstant.CLUSTER_HOST_NODE,nodeId);
    }
    /**
     * 激活分布式node,让其属于保活状态
     */
    public void activeNode(){
        String d = Redis.hget(KitConstant.CLUSTER_HOST_NODE,KitConstant.CLUSTER_HOST_NODE_ID);
        if (d != null){
            ClusterNode node = JSON.parseObject(d,ClusterNode.class);
            node.setPingTime(System.currentTimeMillis());
            Redis.hset(KitConstant.CLUSTER_HOST_NODE,node.getHostNodeId(),JSON.toJSONString(node));
        }
    }
    /**
     * 60秒检查一次清洁员是否存活，没有则推荐一位出来
     */
    public void chooseCleaner(){
        String d = Redis.get(KitConstant.CLUSTER_CLEANER);
        if (StringUtils.isNotBlank(d)){
            ClusterNode node = JSON.parseObject(d, ClusterNode.class);
            if (KitConstant.CLUSTER_HOST_NODE_ID.equals(node.getHostNodeId())){
                node.setPingTime(Redis.time());
                Redis.set(KitConstant.CLUSTER_CLEANER,JSON.toJSONString(node));
            }else {
                //单位：秒
                if (Redis.time()-node.getPingTime()>60){
                    node.setHostNodeId(KitConstant.CLUSTER_HOST_NODE_ID);
                    node.setPingTime(Redis.time());
                    Redis.set(KitConstant.CLUSTER_CLEANER,JSON.toJSONString(node));
                }
            }
        }else {
            ClusterNode node = new ClusterNode();
            node.setHostNodeId(KitConstant.CLUSTER_HOST_NODE_ID);
            node.setPingTime(Redis.time());
            Redis.set(KitConstant.CLUSTER_CLEANER,JSON.toJSONString(node));
        }

    }

}
