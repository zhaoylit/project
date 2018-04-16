package com.zj.web.modules.document.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zj.web.common.utils.ParamsUtil;
import com.zj.web.common.utils.TreeComboboxUtil;
import com.zj.web.modules.document.dao.ApiMapper;
import com.zj.web.modules.document.service.IApiService;

@Service("apiService")
public class ApiServiceImpl implements IApiService {
	@Autowired
	private ApiMapper apiMapper;

	@Override
	public String getApiCategoryTreeJson(Map params) throws Exception {
		// TODO Auto-generated method stub
		//生成 json树
		List<Map> treeList = new ArrayList<Map>();
		Map resultMap = new HashMap();
		List<Map> apiCategoryList = apiMapper.getApiCategoryList(params);
		String json = new TreeComboboxUtil(apiCategoryList,null).getJson(true,"name");
		return json;
	}
}
