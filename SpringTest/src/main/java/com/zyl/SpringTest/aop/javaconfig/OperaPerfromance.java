package com.zyl.SpringTest.aop.javaconfig;

import org.springframework.stereotype.Component;

@Component
public class OperaPerfromance implements IPerformance {

	@Override
	public void perform() {
		// TODO Auto-generated method stub
		System.out.println("-------------------------歌剧表演正在进行");
	}

}
