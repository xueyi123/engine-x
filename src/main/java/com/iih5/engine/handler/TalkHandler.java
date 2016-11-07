/**
 * ---------------------------------------------------------------------------
 * 类名称   ：TalkHandler
 * 类描述   ：
 * 创建人   ： xue.yi
 * 创建时间： 2016/10/28 16:07
 * 版权拥有：星电商科技
 * ---------------------------------------------------------------------------
 */
package com.iih5.engine.handler;

import com.alibaba.fastjson.JSON;
import com.iih5.engine.clusterkit.ClusterSend;
import com.iih5.engine.constant.ProtocolCode;
import com.iih5.engine.dto.BaseVo;
import com.iih5.engine.dto.TalkDto;
import com.iih5.netbox.annotation.Protocol;
import com.iih5.netbox.annotation.Request;
import com.iih5.netbox.message.StringMessage;
import com.iih5.netbox.session.ISession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Request
public class TalkHandler extends BaseHandler {
    static Logger logger= LoggerFactory.getLogger(LoginHandler.class);

    @Protocol(value = ProtocolCode.PRIVATE_TALK)
    public void privateTalk(StringMessage msg,ISession session){
        logger.info("private Talk。。。。。。。。"+ msg.getContent());
        //1.返回成功状态
        responseOK(msg, session);
        //2.通知聊天对方
        TalkDto dto = JSON.parseObject(msg.getContent(),TalkDto.class);
        StringMessage iMessage = new StringMessage(ProtocolCode.PRIVATE_TALK_NOTICE);
        iMessage.setContent(msg.getContent());
        iMessage.setEncrypt(msg.getEncrypt());
        ClusterSend sender = new ClusterSend();
        sender.sendToUser(dto.getTo(),iMessage);
    }
    @Protocol(value = ProtocolCode.GROUP_TALK)
    public void groupTalk(StringMessage msg,ISession session){
        logger.info("group Talk。。。。。。。。"+ msg.getContent());
        //1.返回成功状态
        responseOK(msg, session);
        //2.通知聊天群成员
        TalkDto dto =  JSON.parseObject(msg.getContent(),TalkDto.class);
        StringMessage iMessage = new StringMessage(ProtocolCode.GROUP_TALK_NOTICE);
        iMessage.setContent(msg.getContent());
        iMessage.setEncrypt(msg.getEncrypt());
        ClusterSend sender = new ClusterSend();
        sender.sendToGroup(dto.getTo(),iMessage);
    }
    //返回成功状态
    private void responseOK(StringMessage msg,ISession session){
        StringMessage message = new StringMessage(msg.getId());
        message.setContent(new BaseVo().toSerialize());
        session.send(message);
    }

}
