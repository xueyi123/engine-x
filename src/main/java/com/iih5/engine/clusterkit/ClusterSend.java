/**
 * ---------------------------------------------------------------------------
 * 类名称   ：ClusterSend
 * 类描述   ： 集群消息发送
 * 创建人   ： xue.yi
 * 创建时间： 2016/11/1 15:08
 * 版权拥有：星电商科技
 * ---------------------------------------------------------------------------
 */
package com.iih5.engine.clusterkit;

import com.iih5.engine.Main;
import com.iih5.netbox.message.StringMessage;
import com.iih5.netbox.session.ISession;
import com.iih5.netbox.session.SessionManager;
import com.iih5.smartorm.cache.Redis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ClusterSend {
    Logger logger = LoggerFactory.getLogger(ClusterSend.class);
    /**
     * 分布式通知推送
     * @param uid 接受这uid
     * @param message
     */
    public boolean sendToUser(String uid, StringMessage message) {
        ISession session = SessionManager.getInstance().getSession(uid);
        if (session != null) {
            session.send(message);
        } else {
            String hostNodeId = Redis.get(KitConstant.CLUSTER_USER + uid);
            if (hostNodeId == null || hostNodeId.equals("")) {
                logger.warn("找不到 hostNodeId for "+uid);
                return false;
            }
            ClusterPack clusterPack = new ClusterPack(uid,message);
            if (Main.busClient != null){
                Main.busClient.publish(hostNodeId,clusterPack.toSerialize());
            }
        }
        return true;
    }
    /**
     * 分布式通知推送给一些人(人数不能超过50人)
     * @param uids
     * @param message
     */
    public boolean sendToSomeUser(List<String> uids, StringMessage message) {
        if (uids.size()<= 0){
            return false;
        }
        if (uids.size()> 50){
            logger.warn("发送的人数过多，发送是吧");
            return false;
        }
        for (String uid : uids) {
            sendToUser(uid, message);
        }
        return true;
    }

    /**
     * 发送到群组里
     * @param groupId 群或者组ID
     * @param message
     */
    public boolean  sendToGroup(String groupId,StringMessage message){
        ClusterPack clusterPack = new ClusterPack(groupId,message);
        if (Main.busClient != null){
            Main.busClient.publish(KitConstant.BROADCAST,clusterPack.toSerialize());
        }
        return true;
    }

}
