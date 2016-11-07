/**
 * ---------------------------------------------------------------------------
 * 类名称   ：ConnectDisHandler
 * 类描述   ：断开连接或者连接响应
 * 创建人   ： xue.yi
 * 创建时间： 2016/4/21 16:13
 * 版权拥有：银信网银科技
 * ---------------------------------------------------------------------------
 */
package com.iih5.engine.handler;

import com.iih5.engine.clusterkit.ClusterManger;
import com.iih5.netbox.annotation.InOut;
import com.iih5.netbox.core.ConnectExtension;
import com.iih5.netbox.session.ISession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@InOut("【连接|断开】响应")
public class ConnectDisHandler extends ConnectExtension {
	Logger logger = LoggerFactory.getLogger("");
	@Override
	public void connect(ISession session) {
		logger.info("建立连接。。。");

	}
	//Socket断开/异常处理
	@Override
	public void disConnect(ISession session) {
		logger.info("链接断开");
		if (session.getUserID() != null){
			ClusterManger kit = new ClusterManger();
			kit.unRegisterUser(session.getUserID());
		}
	}
}
