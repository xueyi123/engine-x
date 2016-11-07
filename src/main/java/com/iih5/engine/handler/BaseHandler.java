/**
 * ---------------------------------------------------------------------------
 * 类名称   ：BaseHandler
 * 类描述   ：
 * 创建人   ： xue.yi
 * 创建时间： 2016/4/21 16:13
 * 版权拥有：银信网银科技
 * ---------------------------------------------------------------------------
 */
package com.iih5.engine.handler;

import com.iih5.netbox.message.StringMessage;
import com.iih5.netbox.session.ISession;

public class BaseHandler {

    /**
     * 请求响应
     * @param session
     * @param msgId
     * @param content
     */
    public  void response(ISession session,short msgId,String content){
        StringMessage stringMessage=  new StringMessage(msgId);
        stringMessage.setContent(content);
        session.send(stringMessage);
    }

    /**
     * 安全关掉session
     * @param session
     */
    public void closeConnect(ISession session){
        session.getChannel().disconnect().awaitUninterruptibly();
        session.getChannel().close();
    }

}
