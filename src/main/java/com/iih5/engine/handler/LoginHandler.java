/**
 * ---------------------------------------------------------------------------
 * 类名称   ：LoginHandler
 * 类描述   ： 登录验证
 * 创建人   ： xue.yi
 * 创建时间： 2016/5/20 11:42
 * 版权拥有：银信网银科技
 * ---------------------------------------------------------------------------
 */
package com.iih5.engine.handler;

import com.alibaba.fastjson.JSON;
import com.iih5.engine.clusterkit.ClusterManger;
import com.iih5.engine.constant.ProtocolCode;
import com.iih5.engine.dto.LoginAuthVo;
import com.iih5.netbox.annotation.Protocol;
import com.iih5.netbox.annotation.Request;
import com.iih5.netbox.message.StringMessage;
import com.iih5.netbox.session.ISession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Request
public class LoginHandler {
    static Logger logger= LoggerFactory.getLogger(LoginHandler.class);

    @Protocol(value = ProtocolCode.LOGIN)
    public void auth(StringMessage msg, ISession session){
        String d = msg.getContent();
        logger.info("Auth。。。。。。"+ d);
        String userId = ""+System.currentTimeMillis();
        //本地session绑定uid
        session.bindUserID(userId);
        //綁定群号
        session.setParameter("1",1);
        session.setParameter("2",2);
        session.setParameter("3",3);
        session.setParameter("4",4);
       //分布式session绑定uid
        ClusterManger kit = new ClusterManger();
        kit.registerUser(userId);
        //返回登陆状态
        StringMessage message = new StringMessage(msg.getId());
        LoginAuthVo vo = new LoginAuthVo();
        vo.setUid(userId);
        message.setContent(JSON.toJSONString(vo));

        session.send(message);
    }


}
