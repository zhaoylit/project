package com.zyl.SpringTest.DI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.zyl.SpringTest.JavaConfig1;
import com.zyl.SpringTest.DI.condition.StudentCondition;

@Configuration
@Import(value=JavaConfig1.class)
@ImportResource(value="classpath:spring.xml")
public class JavaConfig {
	@Bean
	@Conditional(value=StudentCondition.class)  //如果StudentCondition类的matchs方法返回true则bean才会被创建
	public Student getStudent(){
		return new Student();
	}
}
