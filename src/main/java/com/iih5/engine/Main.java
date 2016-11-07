package com.iih5.engine;

import com.iih5.engine.clusterkit.ClusterManger;
import com.iih5.engine.clusterkit.KitConstant;
import com.iih5.engine.clusterkit.SubHandler;
import com.iih5.engine.utils.PropertyConf;
import com.iih5.engine.utils.SpringContext;
import com.iih5.netbox.NetBoxEngine;
import com.iih5.netbox.NetBoxEngineSetting;
import com.iih5.netbox.codec.ws.WsTextForDefaultJsonDecoder;
import com.iih5.netbox.codec.ws.WsTextForDefaultJsonEncoder;
import com.iih5.route.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.LinkedList;

@SpringBootApplication
public class Main {
    static Logger logger = LoggerFactory.getLogger(Main.class);
    static Integer PORT = 9230;
    public static Client busClient = null;
    public static void main(String[] args) throws Exception {
        mainCmd(args);
        SpringContext.run();
        subBus();
        startEngine();
    }
    //处理输入命令
    static void mainCmd(String[] args){
        if (args.length >0){
            PORT = Integer.valueOf(args[0]);
        }
    }
    //启动socket引擎
    static void startEngine() throws Exception {
        NetBoxEngineSetting setting = new NetBoxEngineSetting();
        setting.setBasePackage("com.iih5.engine.handler");
        setting.setPort(PORT);
        setting.setProtocolCoder(new WsTextForDefaultJsonEncoder(), new WsTextForDefaultJsonDecoder());
        setting.setPlayerThreadSize(200);
        setting.setDebug(true);
        NetBoxEngine boxEngine = new NetBoxEngine();
        boxEngine.setSettings(setting);
        logger.info("启动服务监听：");
        boxEngine.start();
    }
    //监听来自总线的消息
    static void subBus(){
        logger.info("CLUSTER HOST ID = "+ KitConstant.CLUSTER_HOST_NODE_ID);
        ClusterManger kit = new ClusterManger();
        kit.registerNode(PORT);
        busClient = new Client(new SubHandler(),KitConstant.CMD,KitConstant.BROADCAST,KitConstant.CLUSTER_HOST_NODE_ID);
        String str = PropertyConf.get("CLUSTER.URL");
        String[] arr = str.split(";");
        LinkedList<String> urls = new LinkedList();
        for (String url:arr) {
            urls.add("ws://"+url+"/websocket");
        }
        busClient.setUrls(urls);
        busClient.connect();
    }
}
