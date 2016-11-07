/**
 * ---------------------------------------------------------------------------
 * 类名称   ：BeanConfiguration
 * 类描述   ：
 * 创建人   ： xue.yi
 * 创建时间： 2016/8/16 17:14
 * 版权拥有：银信网银科技
 * ---------------------------------------------------------------------------
 */
package com.iih5.engine.config;


import com.iih5.smartorm.kit.SpringKit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean(name = "orm")
    public SpringKit springKit() {
        return  new SpringKit();
    }
}
