package com.zj.web.modules.goods.service;

import java.util.List;
import java.util.Map;

import com.zj.web.common.pagination.Page;

public interface IGoodsCategoryService {
	//查询列表，分页
	List<Map> selectList(Map params,Page page) throws Exception;
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
	int deleteByPrimaryKey(Map params) throws Exception;
	//查询类目json
	String getGoodsCategoryTreeJson(Map params) throws Exception;
	//查询当期节点 和下面节点的json
	String getCurAndNextOneNode(Map params) throws Exception;
	//添加或者编辑 信息
	String addOrEdit(Map params) throws Exception;
	//删除当前节点和子节点
	String deleteAllChildrenNodebyId(Map params) throws Exception;
}
