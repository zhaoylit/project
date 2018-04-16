package com.zj.web.modules.content.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

public interface IconFunMapper {
	//查询列表，分页
	List<Map> selectList(Map params,RowBounds rowBounds) throws Exception;
	//查询列表，不分页
	List<Map> selectList(Map params) throws Exception;
	//查询单条
	Map selectOne(Map params) throws Exception;
	//查询列表数量
	int selectCount(Map params) throws Exception; 
	//添加
	int insert(Map params) throws Exception;
	//更新
	int updateByPrimaryKeySelective(Map params) throws Exception;
	//删除
	int deleteByPrimaryKey(String [] id) throws Exception;
	
	
}
