/**
 * ---------------------------------------------------------------------------
 * 类名称   ：SpringContext
 * 类描述   ：Spring 上下文启动
 * 创建人   ： xue.yi
 * 创建时间： 2016/4/22 14:06
 * 版权拥有：银信网银科技
 * ---------------------------------------------------------------------------
 */
package com.iih5.engine.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContext {
	private static SpringContext install = new SpringContext();
	public static SpringContext run() {
		return install;
	}
	
	private ApplicationContext ctx = null;
	private SpringContext() {
		this.ctx = new ClassPathXmlApplicationContext(new String[]{"spring.xml","spring_quartz.xml"});
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String arg) {
		return (T)this.ctx.getBean(arg);
	}
	public <T> T getBean(Class<T> t) {
		return (T)this.ctx.getBean(t);
	}
	public ApplicationContext getCtx() {
		return this.ctx;
	}
	public static <T> T getTBean(Class<T> t) {	
		return SpringContext.run().getBean(t);
	}
}
