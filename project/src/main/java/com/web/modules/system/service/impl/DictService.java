package com.web.modules.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.modules.system.dao.DictMapper;
import com.web.modules.system.service.IDictService;

@Service("dictService")
public class DictService implements IDictService {
	@Autowired
	private DictMapper dictMapper;

	@Override
	public List<Map> getDictByKey(Map params) throws Exception {
		// TODO Auto-generated method stub
		return dictMapper.getDictList(params);
	}
	
}
