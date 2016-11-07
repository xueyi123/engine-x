/**
 * ---------------------------------------------------------------------------
 * 类名称   ：PingHandler
 * 类描述   ：心跳响应
 * 创建人   ： xue.yi
 * 创建时间： 2016/4/21 16:13
 * 版权拥有：银信网银科技
 * ---------------------------------------------------------------------------
 */
package com.iih5.engine.handler;

import com.iih5.engine.constant.ProtocolCode;
import com.iih5.netbox.annotation.Protocol;
import com.iih5.netbox.annotation.Request;
import com.iih5.netbox.message.Message;
import com.iih5.netbox.session.ISession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Request
public class PingHandler{
    static Logger logger= LoggerFactory.getLogger(LoginHandler.class);

    @Protocol(value = ProtocolCode.PING)
    public void ping(Message msg, ISession session){
        //logger.info("心跳检测。。。"+msg.toString());
        session.send(msg);
    }
}
