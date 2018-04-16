package com.zyl.SpringTest.aop.javaconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan
public class AopConfig {
	/**
	 * 在spring xml配置文件中，通过以下方式启用aspect注解
	 * <context:component-scan base-package="com.zyl.SpringTest.aop.javaconfig"/>
	 * <aop:aspectj-autoproxy/>
	 * <bean class="com.zyl.SpringTest.aop.javaconfig.Audience"/>
	 * 
	 */
	/*@Bean
	public Audience audience(){
		return new Audience();
	}*/
}
