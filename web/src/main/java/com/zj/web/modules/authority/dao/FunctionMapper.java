package com.zj.web.modules.authority.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface FunctionMapper {
    //查询所有的功能
	List<Map> getAllFuns() throws Exception;
	//查询当前节点和下一子节点的数据
	List<Map> getCurAndNextOneNode(Map params) throws Exception;
	//添加数据
	int insert(Map params) throws Exception;
	//根据id更新
	int updateById(Map params)  throws Exception;
	//根据id查询
	Map selectById(int id)  throws Exception;
	//根据功能地址查询
	Map selectByUrl(@Param("funUrl")String funUrl) throws Exception;
	//根据父节点查询
	List<Map> selectBypId(int pid)  throws Exception;
	//根据id删除当前和下面所有的子节点
	int deleteAllChildrenNodebyId(int id)  throws Exception;
	
}