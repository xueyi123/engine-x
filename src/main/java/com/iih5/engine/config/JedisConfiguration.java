/**
 * ---------------------------------------------------------------------------
 * 类名称   ：JedisConfiguration
 * 类描述   ：
 * 创建人   ： xue.yi
 * 创建时间： 2016/8/16 17:05
 * 版权拥有：银信网银科技
 * ---------------------------------------------------------------------------
 */
package com.iih5.engine.config;

import com.iih5.engine.clusterkit.KitConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConfiguration {
    @Bean(name = KitConstant.CLUSTER_CACHE_REDIS)
    public JedisPool cachePool(@Value("${cache.redis.ip}") String ip,
                               @Value("6379") int port,
                               @Value("0") int timeout,
                               @Value("${cache.redis.auth}") String auth,
                               @Value("0") int select) {
        JedisPoolConfig config = new JedisPoolConfig();
        return  new JedisPool(config,ip,port,timeout,auth,select);
    }
}
