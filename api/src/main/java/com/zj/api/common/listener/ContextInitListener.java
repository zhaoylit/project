package com.zj.api.common.listener;

import java.util.List;
import java.util.Map;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.zj.api.common.utils.ParamsUtil;
import com.zj.api.modules.service.IndexService;

@Component
public class ContextInitListener implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private RedisTemplate<String, String> stringRedisTemplate;
	@Autowired
	private IndexService indexService;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {// root  
			//查询所有的app_key和 app_secret  保存到缓存中
			try {
				//查询所有的app_key和密码
				List<Map> secretList = indexService.getSecretList(null);
				//将app_key和app_secret存进缓存中
				if(secretList != null && secretList.size() > 0){
					for(Map mm : secretList){
						String appKey = ParamsUtil.nullDeal(mm, "app_key", "");
						String appSecret = ParamsUtil.nullDeal(mm, "app_secret", "");
						//加载app_key和 app_secret到缓存中
						stringRedisTemplate.opsForValue().set(appKey,appSecret);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
        }  
	}
}
