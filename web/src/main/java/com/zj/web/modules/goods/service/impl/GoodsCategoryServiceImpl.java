package com.zj.web.modules.goods.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zj.web.common.pagination.Page;
import com.zj.web.common.utils.JsonUtil;
import com.zj.web.common.utils.ParamsUtil;
import com.zj.web.common.utils.TreeComboboxUtil;
import com.zj.web.modules.goods.dao.GoodsCategoryMapper;
import com.zj.web.modules.goods.service.IGoodsCategoryService;

/**
 * @describe  商品管理业务实现类 
 * @author ZYL
 * @date 2017-07-26
 * @version 1.0
 */
@Service("goodsCategoryService")
public class GoodsCategoryServiceImpl implements IGoodsCategoryService {
	private List<Map> nodes; 
	@Autowired
	private GoodsCategoryMapper goodsCategoryMapper;

	@Override
	public List<Map> selectList(Map params, Page page)
			throws Exception {
		// TODO Auto-generated method stub
		//获取资源访问路径
		List<Map> selectList = goodsCategoryMapper.selectList(params,new RowBounds(page.getStartItem(), page.getNumPerPage()));
		return selectList;
	}

	@Override
	public List<Map> selectList(Map params) throws Exception {
		// TODO Auto-generated method stub
		List<Map> selectList = goodsCategoryMapper.selectList(params);
		return selectList;
	}


	@Override
	public Map selectOne(Map params) throws Exception {
		// TODO Auto-generated method stub
		Map oneMap = goodsCategoryMapper.selectOne(params);
		return goodsCategoryMapper.selectOne(params);
	}
	
	@Override
	public int insert(Map params) throws Exception {
		// TODO Auto-generated method stub
		return goodsCategoryMapper.insert(params);
	}

	@Override
	public int updateByPrimaryKeySelective(Map params) throws Exception {
		// TODO Auto-generated method stub
		return goodsCategoryMapper.updateByPrimaryKeySelective(params);
	}

	@Override
	public int deleteByPrimaryKey(Map params) throws Exception {
		// TODO Auto-generated method stub
		String ids = ParamsUtil.nullDeal(params, "ids","");
		if("".equals(ids)){
			return 0;
		}
		String []idsArray = ids.split(",");
		return goodsCategoryMapper.deleteByPrimaryKey(idsArray);
	}

	@Override
	public int selectCount(Map params) throws Exception {
		// TODO Auto-generated method stub
		return goodsCategoryMapper.selectCount(params);
	}

	@Override
	public String getGoodsCategoryTreeJson(Map params) throws Exception {
		// TODO Auto-generated method stub
		String isHasRoot = ParamsUtil.nullDeal(params, "isHasRoot", "0");
		List<Map> treeList = new ArrayList<Map>();
		Map resultMap = new HashMap();
		List<Map> resultList = goodsCategoryMapper.selectList(params);
		String json = new TreeComboboxUtil(resultList,null).getJson("1".equals(isHasRoot) ? true : false,"name");
		return json;
	}

	@Override
	public String getCurAndNextOneNode(Map params) throws Exception {
		// TODO Auto-generated method stub
		Integer selectId = Integer.parseInt(ParamsUtil.nullDeal(params, "id", "0"));
		List<Map> list = goodsCategoryMapper.getCurAndNextOneNode(params);
		boolean flag = false;
		if(selectId == 0){
			flag = true;
		}
		String json = new TreeComboboxUtil(list,selectId).getJson(flag,"name");
		return json;
	}

	@Override
	public String addOrEdit(Map params) throws Exception {
		// TODO Auto-generated method stub
		String id = ParamsUtil.nullDeal(params, "id", "0");
		String pid = ParamsUtil.nullDeal(params, "pid", "0");
		String parendId = ParamsUtil.nullDeal(params, "parendId", "0");
		String afterId = ParamsUtil.nullDeal(params, "afterId", "");
		String iconCls = ParamsUtil.nullDeal(params, "iconCls", "");
		
		if("0".equals(id)){
			int insertNodeOrder = 1;//新插入节点的顺序
			//更新父节点分组状态为分组
			Map upadateMap = new HashMap();
			upadateMap.put("id",parendId);
			upadateMap.put("isGroup","1");
			goodsCategoryMapper.updateByPrimaryKeySelective(upadateMap);
			
			//获取父节点下面的所有子节点列表
			List<Map> childrenList = goodsCategoryMapper.selectBypId(Integer.parseInt(parendId));
			if(childrenList != null && childrenList.size() > 0){
				int index = 0;//父节点下面的子节点遍历开始的位置
				int orderIndex = 2;//需更新的子节点的顺序开始值
				if(!parendId.equals(afterId)){
					//选择的父节点下面有节点，查询选中的在节点之后的该节点的节点的信息
					Map queryMap = new HashMap();
					queryMap.put("id", afterId);
					Map afterMap = goodsCategoryMapper.selectOne(queryMap);
					int afterNodeOrder = Integer.parseInt(ParamsUtil.nullDeal(afterMap, "order", "0"));
					insertNodeOrder = afterNodeOrder + 1;//新插入的节点的顺序为选择的节点的顺序+1
					index = afterNodeOrder;
					orderIndex = insertNodeOrder + 1;
				}
					
				//更新选中节点之后节点的顺序
				for(int i = index;i < childrenList.size();i++){
					Map tempMap_ = childrenList.get(i);
					Map updateMap_ = new HashMap();
					updateMap_.put("id", ParamsUtil.nullDeal(tempMap_, "id", ""));
					updateMap_.put("order",orderIndex++);
					goodsCategoryMapper.updateByPrimaryKeySelective(updateMap_);
				}
			}
			
			//在选择的位置新建子节点
			Map insertMap = new HashMap();
			insertMap.put("pid", parendId);
			insertMap.put("name", ParamsUtil.nullDeal(params, "name",""));
			insertMap.put("iconCls", iconCls);
			insertMap.put("isGroup","0");
			insertMap.put("order",insertNodeOrder);
			goodsCategoryMapper.insert(insertMap);
		}else{
			int updateNodeOrder = 1;//修改节点的顺序
			
			//获取父节点下面的所有子节点列表
			List<Map> childrenList = goodsCategoryMapper.selectBypId(Integer.parseInt(pid));

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
						updateMap_.put("order",orderIndex++);
						goodsCategoryMapper.updateByPrimaryKeySelective(updateMap_);
					}
				}
				if(orderIndex == 1 && !pid.equals(parendId)){
					//没有子节点  ，将父节点的分组表示改为0
					Map upadateMap = new HashMap();
					upadateMap.put("id",pid);
					upadateMap.put("isGroup","0");
					goodsCategoryMapper.updateByPrimaryKeySelective(upadateMap);
				}
			}
			
			//更新新的父节点为分组
			Map upadateMap = new HashMap();
			upadateMap.put("id",parendId);
			upadateMap.put("isGroup","1");
			goodsCategoryMapper.updateByPrimaryKeySelective(upadateMap);
			
			//修改新的父节点 
			List<Map> childrenList1 = goodsCategoryMapper.selectBypId(Integer.parseInt(parendId));
			if(childrenList1 != null && childrenList1.size() > 0){
				int index = 0;//父节点下面的子节点遍历开始的位置
				int orderIndex = 2;//需更新的子节点的顺序开始值
				if(!parendId.equals(afterId)){
					//选择的父节点下面有节点，查询选中的在节点之后的该节点的节点的信息
					Map queryMap = new HashMap();
					queryMap.put("id", afterId);
					Map afterMap = goodsCategoryMapper.selectOne(queryMap);
					
					int afterNodeOrder = Integer.parseInt(ParamsUtil.nullDeal(afterMap, "order", "0"));
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
						updateMap_.put("order",orderIndex++);
						goodsCategoryMapper.updateByPrimaryKeySelective(updateMap_);
					}
					
				}
			}
			
			Map updateMap = new HashMap();
			updateMap.put("id", id);
			updateMap.put("pid", parendId);
			updateMap.put("name", ParamsUtil.nullDeal(params, "name",""));
			updateMap.put("iconCls", iconCls);
			updateMap.put("order",updateNodeOrder);
			goodsCategoryMapper.updateByPrimaryKeySelective(updateMap);
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
				Map queryMap = new HashMap();
				queryMap.put("id", str);
				
				Map curNode = goodsCategoryMapper.selectOne(queryMap);
				Integer pid = Integer.parseInt(ParamsUtil.nullDeal(curNode, "pid", ""));
				Integer isGroup = Integer.parseInt(ParamsUtil.nullDeal(curNode, "isGroup", "1"));
				
				//删除当前节点
				goodsCategoryMapper.deleteAllChildrenNodebyId(Integer.parseInt(str));
				List<Map> childrenList1 = goodsCategoryMapper.selectBypId(pid);
				if(childrenList1!= null && childrenList1.size() > 0){
					for(int i = 0;i < childrenList1.size();i++){
						Map tempMap_ = childrenList1.get(i);
						Map updateMap_ = new HashMap();
						updateMap_.put("id", ParamsUtil.nullDeal(tempMap_, "id", ""));
						updateMap_.put("menuOrder",i+1);
						goodsCategoryMapper.updateByPrimaryKeySelective(updateMap_);
					}
					
				}else{
					Map upadateMap = new HashMap();
					upadateMap.put("id", pid);
					upadateMap.put("isGroup","0");
					goodsCategoryMapper.updateByPrimaryKeySelective(upadateMap);
				}
			}
		}
		return JsonUtil.getResultStatusJson("1", "");
	}
}
