package com.web.modules.system.service;

import java.util.List;
import java.util.Map;

public interface IResourceService {
	//查询图标列表
	List<Map> getIconList(Map params) throws Exception;
	//查询图标列表数量
	int getIconListCount(Map params) throws Exception;
	//根据id查询字典信息
	Map selectDictById(int parseInt) throws Exception;
	//添加或者编辑字典表
	String addOrEditDict(Map params) throws Exception;
	//删除图标
	String deleteIcon(Map params) throws Exception;
	//根据资源类型查询资源路径
	String selectResourcePathByType(String path) throws Exception;

}
