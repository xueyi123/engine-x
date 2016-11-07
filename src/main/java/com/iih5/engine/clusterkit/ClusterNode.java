/**
 * ---------------------------------------------------------------------------
 * 类名称   ：ClusterNode
 * 类描述   ：
 * 创建人   ： xue.yi
 * 创建时间： 2016/10/28 10:30
 * 版权拥有：星电商科技
 * ---------------------------------------------------------------------------
 */
package com.iih5.engine.clusterkit;

public class ClusterNode {
    private String hostNodeId;
    private String ip;
    private Integer port;
    private Long pingTime;

    public String getHostNodeId() {
        return hostNodeId;
    }

    public void setHostNodeId(String hostNodeId) {
        this.hostNodeId = hostNodeId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Long getPingTime() {
        return pingTime;
    }

    public void setPingTime(Long pingTime) {
        this.pingTime = pingTime;
    }
}
