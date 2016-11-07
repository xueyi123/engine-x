package com.iih5.engine.clusterkit;

import com.iih5.netbox.session.ISession;
import com.iih5.netbox.session.SessionManager;
import com.iih5.route.client.Handler;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Collection;
import java.util.Timer;

public class SubHandler extends Handler {
    private static Logger logger = LoggerFactory.getLogger(SubHandler.class);

    @Override
    public void connect(Channel channel) {
        logger.info("链接总线成功");
    }

    @Override
    public void connectError(Exception e, Timer timer, int i) {
        logger.info("链接总线失败，已尝试次数："+ i);
    }

    @Override
    public void disconnect(Channel channel) {
        logger.info("断开连接");
    }

    @Override
    public void onMessage(String channel, String message) {
        logger.debug("《《《接收来自总线的数据：" + channel + " 接收消息：" + message);
        if (channel.equals(KitConstant.CLUSTER_HOST_NODE_ID)){
            //用户消息
            userMessageHandle(message);
        }else if (channel.equals(KitConstant.CMD)){
           //命令行
            cmdMessageHandle(message);
        }else if (channel.equals(KitConstant.BROADCAST)){
           //群消息
            groupMessageHandle(message);
        }else {
            logger.warn("channel:"+channel+" 暂时不支持此channel");
        }
    }

    @Override
    public void onMessage(byte[] channel, byte[] message) {
        //TODO
    }

    void userMessageHandle(String message){
        ClusterPack pack =  ClusterPack.fromSerialize(message);
        SessionManager.getInstance().getSession(pack.getTo()).send(pack.getMessage());
    }
    void groupMessageHandle(String message){
        ClusterPack pack =  ClusterPack.fromSerialize(message);
        String groupId = pack.getTo();
        Collection<ISession> sessions =  SessionManager.getInstance().getAllSessions();
        for (ISession session:sessions) {
            if (session.containParameter(groupId)){
                session.send(pack.getMessage());
            }
        }
    }
    void cmdMessageHandle(String message){
        //命令操作
    }
}
