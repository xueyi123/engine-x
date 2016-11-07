/**
 * ---------------------------------------------------------------------------
 * 类名称   ：ProtocolCode
 * 类描述   ：协议编码
 * 创建人   ： xue.yi
 * 创建时间： 2016/4/21 16:13
 * 版权拥有：银信网银科技
 * ---------------------------------------------------------------------------
 */
package com.iih5.engine.constant;

public class ProtocolCode {

	/**心跳-应答*/
	public static final short PING					=1000;

	/**登陆-应答*/
	public static final short LOGIN					=1001;

	/**私聊请求*/
	public static final short PRIVATE_TALK			=2000;

	/**私聊通知*/
	public static final short PRIVATE_TALK_NOTICE	=2001;

	/**群聊请求*/
	public static final short GROUP_TALK			=2002;

	/**群聊通知*/
	public static final short GROUP_TALK_NOTICE	=2003;
}
