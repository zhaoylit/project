package com.web.modules.authority.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.modules.authority.dao.RoleAuthMapper;
import com.web.modules.authority.service.IRoleAuthService;
import com.web.util.JsonUtil;
import com.web.util.ParamsUtil;
import com.web.util.StringUtil;
import com.web.util.TreeUtil;

@Service("roleAuthService")
public class RoleAuthServiceImpl implements IRoleAuthService {
	private List<Map> nodes; 
	
	@Autowired
	private RoleAuthMapper roleAuthMapper;
	
	
	@Override
	public List<Map> getRoleList(Map params) throws Exception {
		// TODO Auto-generated method stub
		return roleAuthMapper.getRoleList(params);
	}

	@Override
	public int getRoleListCount(Map params) throws Exception {
		// TODO Auto-generated method stub
		return roleAuthMapper.getRoleListCount(params);
	}

	@Override
	public List<Map> getAllAuthByRole(Map params) throws Exception {
		// TODO Auto-generated method stub
		Integer roleId = Integer.parseInt(ParamsUtil.nullDeal(params, "roleId", "0"));
		List<Map> resultLsit = null;
		List<Map> funList = roleAuthMapper.getAllFunctions(roleId);
		resultLsit = new TreeUtil(funList).buildTree();
		return resultLsit == null ? Collections.EMPTY_LIST : resultLsit;
	}

	@Override
	public String addOrEditRole(Map params) throws Exception {
		// TODO Auto-generated method stub
		Integer roleId = Integer.parseInt(ParamsUtil.nullDeal(params, "roleId", "0"));
		int rowsCount = 0;
		if(roleId == 0){
			//添加角色
			rowsCount = roleAuthMapper.addRole(params);
		}else{
			rowsCount = roleAuthMapper.updateRoleById(params);
		}
		if(rowsCount > 0){
			return JsonUtil.getResultStatusJson("1", "操作成功");	
		}
		return JsonUtil.getResultStatusJson("0", "操作失败");
	}

	@Override
	public String deleteRole(Map params) throws Exception {
		// TODO Auto-generated method stub
		String roleIds = ParamsUtil.nullDeal(params, "ids", "");
		int rowsCount = 0;
		rowsCount = roleAuthMapper.deleteRoleById(roleIds);
		if(rowsCount > 0){
			return JsonUtil.getResultStatusJson("1", "成功删除"+rowsCount + "数据");
		}
		return JsonUtil.getResultStatusJson("0", "操作失败");
	}

	@Override
	public String updateRoleAuth(Map params) throws Exception {
		// TODO Auto-generated method stub
		String roleId = ParamsUtil.nullDeal(params, "roleId", "0");
		String funIds = ParamsUtil.nullDeal(params, "ids", "");
		if("".equals(funIds)){
			return JsonUtil.getResultStatusJson("0", "操作失败");
		}
		int rowsCount = 0;
		roleAuthMapper.deleteAuthByRoleId(Integer.parseInt(roleId));
		
		for(String funId : funIds.split(",")){
			Map insertMap = new HashMap();
			insertMap.put("roleId", roleId);
			insertMap.put("funId", funId);
			rowsCount += roleAuthMapper.addRoleAuth(insertMap);
		}
		return JsonUtil.getResultStatusJson("1", "成功更新"+rowsCount + "数据");
	}
}
