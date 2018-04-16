package com.web.modules.authority.service;

import java.util.Map;

public interface IFunctionService {
	//查询所有功能的功能树html
	String getAllFunTreeHtml() throws Exception;
	//查询功能graidtree json
	String getAllFunTableJson() throws Exception;
	//查询功能  combobox tree
	String getAllFunTreeJson() throws Exception;
	//查询当前节点和下一子节点的树形json
	String getCurAndNextOneNode(Map params) throws Exception;
	//添加或者编辑功能
	String addOrEditFun(Map params) throws Exception;
	//删除当前节点和下面的所有节点
	String deleteAllChildrenNodebyId(Map params) throws Exception;
	//根据id查询节点信息
	Map selectById(Integer id) throws Exception;
}
