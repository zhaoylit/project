package com.zyl.SpringTest.DI.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class StudentCondition  implements Condition{

	@Override
	public boolean matches(ConditionContext context,
			AnnotatedTypeMetadata metadata) {
		// TODO Auto-generated method stub
		//返回BeanDefinitionRegistry检查bean定义
		context.getRegistry();
		//返回的ConfigurableListableBeanFactory检查bean是否存在，甚至探查bean的属性
		context.getBeanFactory();
		//返回的Environment检查环境变量是否存在以及他的值是什么
		//获取环境变量
		Environment env = context.getEnvironment();
		System.out.println(env.getProperty("path"));
		return env.containsProperty("path");
	}
}
