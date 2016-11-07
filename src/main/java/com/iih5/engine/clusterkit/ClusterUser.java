/**
 * ---------------------------------------------------------------------------
 * 类名称   ：ClusterUser
 * 类描述   ：
 * 创建人   ： xue.yi
 * 创建时间： 2016/10/28 10:26
 * 版权拥有：星电商科技
 * ---------------------------------------------------------------------------
 */
package com.iih5.engine.clusterkit;

public class ClusterUser {
    private String hostNodeId;
    private String uid;

    public ClusterUser(){};
    public ClusterUser(String hostNodeId, String uid){
        this.hostNodeId = hostNodeId;
        this.uid = uid;
    };
    public String getHostNodeId() {
        return hostNodeId;
    }

    public void setHostNodeId(String hostNodeId) {
        this.hostNodeId = hostNodeId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
