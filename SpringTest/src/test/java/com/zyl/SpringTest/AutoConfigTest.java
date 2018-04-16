package com.zyl.SpringTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zyl.SpringTest.auto.AutoConfig;
import com.zyl.SpringTest.auto.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AutoConfig.class)
public class AutoConfigTest {
	
	
	@Test
	public void test(){
		ApplicationContext context = new AnnotationConfigApplicationContext("com.zyl.SpringTest.auto");
		context.getBean("student",Student.class).show();
	}
	
	
	
}
