package com.zkkj.backend.service.common;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class BaseService {

	private static ClassPathXmlApplicationContext context = null;
	{
		context = new ClassPathXmlApplicationContext(
				new String[] { "dubboConsumer.xml" });	
		context.start();
	}

	public static Object getService(String serviceName) {
		try {
			Object object = context.getBean(serviceName);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
