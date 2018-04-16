package com.zj.api.modules.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zj.api.common.utils.ParamsUtil;
import com.zj.api.modules.dao.IndexMapper;
import com.zj.api.modules.service.IndexService;

@Service("indexService")
public class IndexServiceImpl implements IndexService {

	@Autowired
	private IndexMapper indexMapper;
	
	@Override
	public List<Map> getCarouselFigure(Map params) throws Exception {
		// TODO Auto-generated method stub
		return indexMapper.getCarouselFigure(params);
	}

	@Override
	public List<Map> getSecretList(Map params) throws Exception {
		// TODO Auto-generated method stub
		return indexMapper.getSecretList(params);
	}

	@Override
	public String getSecretByKey(Map params) throws Exception {
		// TODO Auto-generated method stub
		String app_key = ParamsUtil.nullDeal(params, "app_key", "");
		System.out.println("--------------------------------------" + app_key);
		return indexMapper.getSecretByKey(app_key);
	}
}
