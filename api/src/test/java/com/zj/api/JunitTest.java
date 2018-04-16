package com.zj.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.zj.api.modules.service.IndexService;

@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring.xml","classpath:spring-redis.xml","classpath:spring-ehcache.xml"})
public class JunitTest {
	@Autowired
	private IndexService indexService;
	
	@Test
	public void test(){
		Map params = new HashMap();
		params.put("osType", "ios");
		params.put("ip", "192.168.0.1");
		params.put("code", "200");
		params.put("version", "1.0");
		params.put("mobile", "SamsungG9200");
		params.put("msg", "OK");
		try {
			List<Map> carouselFigure = indexService.getCarouselFigure(params);
		    System.out.println(JSONArray.fromObject(carouselFigure).toString());
		    params.put("data", carouselFigure);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			System.out.println(JSONObject.fromObject(params).toString() );
		}
	}
}
