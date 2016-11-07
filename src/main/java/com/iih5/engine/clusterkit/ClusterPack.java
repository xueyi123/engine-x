/**
 * ---------------------------------------------------------------------------
 * 类名称   ：ClusterPack
 * 类描述   ： 集群之间内部通信数据包
 * 创建人   ： xue.yi
 * 创建时间： 2016/10/31 17:33
 * 版权拥有：星电商科技
 * ---------------------------------------------------------------------------
 */
package com.iih5.engine.clusterkit;

import com.iih5.netbox.codec.ws.WsTextForDefaultJsonDecoder;
import com.iih5.netbox.codec.ws.WsTextForDefaultJsonEncoder;
import com.iih5.netbox.message.StringMessage;

public class ClusterPack {
    private String to;
    private StringMessage message;

    public ClusterPack(){};
    public ClusterPack(String to, StringMessage message){
        this.to=to;
        this.message=message;
    }
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public StringMessage getMessage() {
        return message;
    }

    public void setMessage(StringMessage message) {
        this.message = message;
    }

    /**
     * 转换为可以传输的字符串(比直接进行json转换快1000倍)
     * @return
     */
    public String toSerialize(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(to);
        buffer.append("#");
        WsTextForDefaultJsonEncoder encoder = new WsTextForDefaultJsonEncoder();
        encoder.pack(message,buffer);
        return buffer.toString();
    }

    /**
     * 反序列化为对象
     * @param serialize
     * @return
     */
    public static ClusterPack fromSerialize(String serialize){
        if (serialize == null || serialize.equals("")){
            return null;
        }
        String[] arr = serialize.split("#",2);
        if (arr.length < 2){
            return null;
        }
        ClusterPack clusterPack = new ClusterPack();
        clusterPack.setTo(arr[0]);
        WsTextForDefaultJsonDecoder decoder = new WsTextForDefaultJsonDecoder();
        clusterPack.setMessage(decoder.unPack(arr[1]));
        return clusterPack;
    }
}
