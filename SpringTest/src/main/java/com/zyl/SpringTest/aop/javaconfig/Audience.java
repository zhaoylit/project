package com.zyl.SpringTest.aop.javaconfig;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Audience {
	@Pointcut("execution(** com.zyl.SpringTest.aop.javaconfig.IPerformance.perform(..))")
	public void performance(){}
	
	@Before("performance()")
	public void selenceCellPhones(){
		System.out.println("-------------------------在表演之前手机要静音");
	}
	
	@Before("performance()")
	public void takeSeats(){
		System.out.println("-------------------------在及表演之前要就坐");
	}
	
	@AfterReturning("performance()")
	public void applause(){
		System.out.println("--------------------------在表演之后观众的掌声");
	}
	@AfterThrowing("performance()")
	public void demandRefund(){
		System.out.println("--------------------------不好意思表演失败了");
	}
	@Around("performance()")
	public void watchPerformance(ProceedingJoinPoint jp){
		try {
			System.out.println("around---------------------Silencing cell phones");
			System.out.println("around---------------------Taking seats");
			jp.proceed();
			System.out.println("around---------------------CLAP　CLAP　CLAP CLAP ");
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("around---------------------表演失败");
		}
	}
	
	
}
