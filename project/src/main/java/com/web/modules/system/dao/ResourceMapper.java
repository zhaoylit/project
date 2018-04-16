package com.web.modules.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ResourceMapper {
	//查询图标列表
	List<Map> getIconList(Map params) throws Exception;
	//查询图标数量
	int getIconListCount(Map params) throws Exception;
	//根据id查询icon
	Map selectIconById(@Param("id")Integer id) throws Exception;
	//添加字典数据
	int insert(Map params) throws Exception;
	//根据id更新
	int updateById(Map params) throws Exception;
	//根据id删除
	int deletebyId(String ids) throws Exception;
	//根据资源类型查询资源path
	String selectResourcePathByType(@Param("type")String path) throws Exception;

}
