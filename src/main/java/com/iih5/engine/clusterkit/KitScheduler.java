package com.iih5.engine.clusterkit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class KitScheduler {
    static Logger logger = LoggerFactory.getLogger(KitScheduler.class);

    /**
     * 处理：每N秒执行一次
     * 激活分布式node,让其属于保活状态
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void activeNode() {
        ClusterManger manger = new ClusterManger();
        manger.activeNode();
    }

    /**
     * 定期处理清除无效的在线信息，避免用户状态（在线/离线。）不准确
     * 30秒检查一次
     */
    @Scheduled(cron = "*/20 * * * * ?")
    public void invalidOnlineCheck() {
        ClusterManger manger = new ClusterManger();
        manger.invalidOnlineCheck();
    }

    /**
     * 60秒检查一次清洁员是否存活，没有则推荐一位出来
     */
    @Scheduled(cron = "*/20 * * * * ?")
    public void chooseCleaner(){
        ClusterManger manger = new ClusterManger();
        manger.chooseCleaner();
    }

}
