package com.web.modules.system.service;

import java.util.List;
import java.util.Map;

public interface IDictService {
	//查询字典列表
	List<Map> getDictByKey(Map params) throws Exception;

}
