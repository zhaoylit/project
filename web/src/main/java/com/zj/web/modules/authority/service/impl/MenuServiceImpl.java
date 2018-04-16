package com.zj.web.modules.authority.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zj.web.common.utils.JsonUtil;
import com.zj.web.common.utils.ParamsUtil;
import com.zj.web.common.utils.TreeComboboxUtil;
import com.zj.web.common.utils.TreeMenuUtil;
import com.zj.web.modules.authority.dao.MenuMapper;
import com.zj.web.modules.authority.service.IMenuService;


@Service("menuService")
public class MenuServiceImpl implements IMenuService {
	@Autowired
	private MenuMapper menuMapper;
	
	private List<Map> nodes; 
	
	private List<Map> menuTableList;
	
	private List<Map> menuTreeList;
	
	@Override
	public String getAllMenuTreeHtml() throws Exception {
		// TODO Auto-generated method stub
		List<Map> menuList = menuMapper.getAllMenus();
		
		String html = new TreeMenuUtil(menuList).buildMenuTree();
		return html;
	}

	@Override
	public String getAllMenuTableJson() throws Exception {
		// TODO Auto-generated method stub
		menuTableList = new ArrayList<Map>();
		Map resultMap = new HashMap();
		List<Map> menuList = menuMapper.getAllMenus();
		String json = new TreeComboboxUtil(menuList,null).getJson(false,"menuName");
		return json;
	} 
	@Override
	public String getAllMenuTreeJson() throws Exception {
		// TODO Auto-generated method stub
		menuTreeList = new ArrayList<Map>();
		Map resultMap = new HashMap();
		List<Map> menuList = menuMapper.getAllMenus();
		String json = new TreeComboboxUtil(menuList,null).getJson(true,"menuName");
		return json;
	} 

	@Override
	public String getCurAndNextOneNode(Map params) throws Exception {
		// TODO Auto-generated method stub
		Integer selectId = Integer.parseInt(ParamsUtil.nullDeal(params, "id", "0"));
		List<Map> list = menuMapper.getCurAndNextOneNode(params);
		boolean flag = false;
		if(selectId == 0){
			flag = true;
		}
		String json = new TreeComboboxUtil(list,selectId).getJson(flag,"menuName");
		return json;
	}

	@Override
	public String addOrEditMenu(Map params) throws Exception {
		// TODO Auto-generated method stub
		String id = ParamsUtil.nullDeal(params, "id", "0");
		String pid = ParamsUtil.nullDeal(params, "pid", "0");
		String parendId = ParamsUtil.nullDeal(params, "parendId", "0");
		String afterId = ParamsUtil.nullDeal(params, "afterId", "");
		String funId = ParamsUtil.nullDeal(params, "funId", "");
		String iconCls = ParamsUtil.nullDeal(params, "iconCls", "");
		
		if("0".equals(id)){
			int insertNodeOrder = 1;//新插入节点的顺序
			//更新父节点分组状态为分组
			Map upadateMap = new HashMap();
			upadateMap.put("id",parendId);
			upadateMap.put("isGroup","1");
			menuMapper.updateById(upadateMap);
			
			//获取父节点下面的所有子节点列表
			List<Map> childrenList = menuMapper.selectBypId(Integer.parseInt(parendId));
			if(childrenList != null && childrenList.size() > 0){
				int index = 0;//父节点下面的子节点遍历开始的位置
				int orderIndex = 2;//需更新的子节点的顺序开始值
				if(!parendId.equals(afterId)){
					//选择的父节点下面有节点，查询选中的在节点之后的该节点的节点的信息
					Map afterMap = menuMapper.selectById(Integer.parseInt(afterId));
					int afterNodeOrder = Integer.parseInt(ParamsUtil.nullDeal(afterMap, "menuOrder", "0"));
					insertNodeOrder = afterNodeOrder + 1;//新插入的节点的顺序为选择的节点的顺序+1
					index = afterNodeOrder;
					orderIndex = insertNodeOrder + 1;
				}
					
				//更新选中节点之后节点的顺序
				for(int i = index;i < childrenList.size();i++){
					Map tempMap_ = childrenList.get(i);
					Map updateMap_ = new HashMap();
					updateMap_.put("id", ParamsUtil.nullDeal(tempMap_, "id", ""));
					updateMap_.put("menuOrder",orderIndex++);
					menuMapper.updateById(updateMap_);
				}
			}
			
			//在选择的位置新建子节点
			Map insertMap = new HashMap();
			insertMap.put("pid", parendId);
			insertMap.put("menuName", ParamsUtil.nullDeal(params, "menuName",""));
			insertMap.put("funId", funId);
			insertMap.put("iconCls", iconCls);
			insertMap.put("isGroup","0");
			insertMap.put("menuOrder",insertNodeOrder);
			menuMapper.insert(insertMap);
		}else{
			int updateNodeOrder = 1;//修改节点的顺序
			
			//获取父节点下面的所有子节点列表
			List<Map> childrenList = menuMapper.selectBypId(Integer.parseInt(pid));

			//重新定义修改节点之前同级所有节点的顺序
			if(childrenList != null && childrenList.size() > 0){
				int orderIndex = 1;//需更新的子节点的顺序开始值
				//更新选中节点之后节点的顺序
				for(int i = 0;i < childrenList.size();i++){
					Map tempMap_ = childrenList.get(i);
					//节点id
					Integer id_ = Integer.parseInt(ParamsUtil.nullDeal(tempMap_, "id", ""));
					if(Integer.parseInt(id) != id_){
						Map updateMap_ = new HashMap();
						updateMap_.put("id",id_);
						updateMap_.put("menuOrder",orderIndex++);
						menuMapper.updateById(updateMap_);
					}
				}
				if(orderIndex == 1 && !pid.equals(parendId)){
					//没有子节点  ，将父节点的分组表示改为0
					Map upadateMap = new HashMap();
					upadateMap.put("id",pid);
					upadateMap.put("isGroup","0");
					menuMapper.updateById(upadateMap);
				}
			}
			
			//更新新的父节点为分组
			Map upadateMap = new HashMap();
			upadateMap.put("id",parendId);
			upadateMap.put("isGroup","1");
			menuMapper.updateById(upadateMap);
			
			//修改新的父节点 
			List<Map> childrenList1 = menuMapper.selectBypId(Integer.parseInt(parendId));
			if(childrenList1 != null && childrenList1.size() > 0){
				int index = 0;//父节点下面的子节点遍历开始的位置
				int orderIndex = 2;//需更新的子节点的顺序开始值
				if(!parendId.equals(afterId)){
					//选择的父节点下面有节点，查询选中的在节点之后的该节点的节点的信息
					Map afterMap = menuMapper.selectById(Integer.parseInt(afterId));
					int afterNodeOrder = Integer.parseInt(ParamsUtil.nullDeal(afterMap, "menuOrder", "0"));
					updateNodeOrder = afterNodeOrder + 1;//新插入的节点的顺序为选择的节点的顺序+1
					index = afterNodeOrder;
					orderIndex = updateNodeOrder + 1;
				}
				if(pid.equals(parendId)){
					for(int i = 0;i < childrenList1.size();i++){
						Map tempMap_ = childrenList1.get(i);
						Integer id_ = Integer.parseInt(ParamsUtil.nullDeal(tempMap_, "id", ""));
						if(Integer.parseInt(id) == id_){
							childrenList1.remove(i);
						}
					}
				}
				//更新选中节点之后节点的顺序
				for(int i = index;i < childrenList1.size();i++){
					Map tempMap_ = childrenList1.get(i);
					Integer id_ = Integer.parseInt(ParamsUtil.nullDeal(tempMap_, "id", ""));
					if(Integer.parseInt(id) != id_){
						Map updateMap_ = new HashMap();
						updateMap_.put("id",id_);
						updateMap_.put("menuOrder",orderIndex++);
						menuMapper.updateById(updateMap_);
					}
					
				}
			}
			
			Map updateMap = new HashMap();
			updateMap.put("id", id);
			updateMap.put("pid", parendId);
			updateMap.put("menuName", ParamsUtil.nullDeal(params, "menuName",""));
			updateMap.put("funId",funId);
			updateMap.put("iconCls", iconCls);
			updateMap.put("menuOrder",updateNodeOrder);
			menuMapper.updateById(updateMap);
		}
		
		
		JSONObject jo = new JSONObject();
		jo.put("result", "1");
		return jo.toString();
	}

	@Override
	public String deleteAllChildrenNodebyId(Map params) throws Exception {
		// TODO Auto-generated method stub
		String ids  = ParamsUtil.nullDeal(params, "ids", "");
		String array[] = ids.split(",");
		if(array != null && array.length > 0){
			for(String str : array){
				//查询当前删除的节点信息
				Map curNode = menuMapper.selectById(Integer.parseInt(str));
				Integer pid = Integer.parseInt(ParamsUtil.nullDeal(curNode, "pid", ""));
				Integer isGroup = Integer.parseInt(ParamsUtil.nullDeal(curNode, "isGroup", "1"));
				
				//删除当前节点
				menuMapper.deleteAllChildrenNodebyId(Integer.parseInt(str));
				List<Map> childrenList1 = menuMapper.selectBypId(pid);
				if(childrenList1!= null && childrenList1.size() > 0){
					for(int i = 0;i < childrenList1.size();i++){
						Map tempMap_ = childrenList1.get(i);
						Map updateMap_ = new HashMap();
						updateMap_.put("id", ParamsUtil.nullDeal(tempMap_, "id", ""));
						updateMap_.put("menuOrder",i+1);
						menuMapper.updateById(updateMap_);
					}
					
				}else{
					Map upadateMap = new HashMap();
					upadateMap.put("id", pid);
					upadateMap.put("isGroup","0");
					menuMapper.updateById(upadateMap);
				}
			}
		}
		return JsonUtil.getResultStatusJson("1", "");
	}

	@Override
	public Map selectById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return menuMapper.selectById(id);
	}  
}
