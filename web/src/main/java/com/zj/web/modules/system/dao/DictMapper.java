package com.zj.web.modules.system.dao;

import java.util.List;
import java.util.Map;

public interface DictMapper {

	//根据key查询字典列表
	List<Map> getDictList(Map params) throws Exception;

}
