package com.zyl.SpringTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zyl.SpringTest.DI.JavaConfig;
import com.zyl.SpringTest.DI.Student;
import com.zyl.SpringTest.DI.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=JavaConfig.class)
public class SpringTest {
	@Test
	public void testDI(){
		ApplicationContext context = new AnnotationConfigApplicationContext("com.zyl.SpringTest.DI");
		
		System.out.println(context.getBean("getStudent"));
		
		System.out.println(context.getBean("teacher"));
		
		context.getBean("getStudent",Student.class).show();
		
		context.getBean("getUser",User.class).show();
		
	}
}
