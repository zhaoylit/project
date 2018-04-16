package com.web.modules.authority.service;

import java.util.Map;

public interface IMenuService {
	//查询所有菜单的菜单树html
	String getAllMenuTreeHtml() throws Exception;
	//查询菜单graidtree json
	String getAllMenuTableJson() throws Exception;
	//查询菜单  combobox tree
	String getAllMenuTreeJson() throws Exception;
	//查询当前节点和下一子节点的树形json
	String getCurAndNextOneNode(Map params) throws Exception;
	//添加或者编辑菜单
	String addOrEditMenu(Map params) throws Exception;
	//删除当前节点和下面的所有节点
	String deleteAllChildrenNodebyId(Map params) throws Exception;
	//根据id查询节点信息
	Map selectById(Integer id) throws Exception;
}
