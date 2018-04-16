package com.zyl.SpringTest.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zyl.SpringTest.aop.javaconfig.AopConfig;
import com.zyl.SpringTest.aop.javaconfig.IPerformance;
import com.zyl.SpringTest.aop.javaconfig.OperaPerfromance;
import com.zyl.SpringTest.aop.javaconfig.TrackCounter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AopConfig.class)
public class AopTest {
	@Autowired
	private TrackCounter counter;
	@Test
	public void test(){
		ApplicationContext context = new AnnotationConfigApplicationContext("com.zyl.SpringTest.aop.javaconfig");
		try {
//			System.out.println(context.getBean("operaPerfromance"));
//			context.getBean("operaPerfromance",IPerformance.class).perform();
			System.out.println(counter.getTrackCounts());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
	
}
