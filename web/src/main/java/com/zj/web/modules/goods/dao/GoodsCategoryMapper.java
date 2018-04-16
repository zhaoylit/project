package com.zj.web.modules.goods.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

public interface GoodsCategoryMapper {
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
	//查询当前节点  和下面的节点信息
	List<Map> getCurAndNextOneNode(Map params) throws Exception;
	//根据父节点查询
	List<Map> selectBypId(int pid)  throws Exception;
	//根据id删除当前和下面所有的子节点
	int deleteAllChildrenNodebyId(int id)  throws Exception;
}
