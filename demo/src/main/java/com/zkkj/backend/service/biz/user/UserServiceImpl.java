package com.zkkj.backend.service.biz.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.util.StringUtils;

import com.zkkj.backend.service.common.BaseService;
import com.zkkj.platform.base.common.DateUtil;
import com.zkkj.platform.user.UserManageService;

/**
 * 用户管理业务实现层
 * 
 * @author 
 *
 */
@Service("userService")
public class UserServiceImpl extends BaseService implements IUserService {

	@Override
	public List<Map<String, String>> getUserList(Map params) throws Exception {
		// TODO Auto-generated method stub
		UserManageService userManageService = (UserManageService) getService("userManageService");
		List<Map<String, String>> resultList = userManageService
				.getUser(params);
		return resultList;
	}

	@Override
	public int getUserListCount(Map params) throws Exception {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public Map addOrEditUser(Map params) throws Exception {
		// TODO Auto-generated method stub
		String rowKey = ParamsUtil.nullDeal(params, "rowKey", "");
		String account = ParamsUtil.nullDeal(params, "account", "");
		Map  userMap=new HashMap();
		userMap.put("account", account);
		UserManageService userManageService = (UserManageService) getService("userManageService");
		
		List<Map<String, String>> userList=userManageService.UserFrozen(userMap);
		//判断用户输入的用户名是否存在
		if(rowKey.equals("") && userList != null && userList.size() > 0){
			Map accountrepeat=new HashMap();
			accountrepeat.put("accountrepeat", "1");
			return accountrepeat;
		}
		Map resultMap = userManageService.addOrUpdateUser(params);
		return resultMap;
	}

	@Override
	public Map deleteUser(Map params) throws Exception {
		UserManageService userManageService = (UserManageService) getService("userManageService");
		Map resultMap = new HashMap();
		String ids = ParamsUtil.nullDeal(params, "ids", "");
		String array[] = ids.split(",");
		if (StringUtils.isNotBlank(ids)) {
			for (String str : array) {
				userManageService.deleteUser(str);
			}
		}
		resultMap.put("result", "1");
		resultMap.put("message", "操作成功");
		return resultMap;
	}

	@Override
	public Map updateUserFrozen(Map params) throws Exception {
		// TODO Auto-generated method stub
		UserManageService userManageService = (UserManageService) getService("userManageService");
		Map resultMap = new HashMap();
		String ids = ParamsUtil.nullDeal(params, "ids", "");
		String array[] = ids.split(",");
		if (StringUtils.isNotBlank(ids)) {
			for (String str : array) {
				userManageService.updateUserFrozen(str);
			}
		}
		resultMap.put("result", "1");
		resultMap.put("message", "操作成功");
		return resultMap;
	}

	@Override
	public Map updateAccountThaw(Map params) throws Exception {
		UserManageService userManageService = (UserManageService) getService("userManageService");
		Map resultMap = new HashMap();
		String ids = ParamsUtil.nullDeal(params, "ids", "");
		String array[] = ids.split(",");
		if (StringUtils.isNotBlank(ids)) {
			for (String str : array) {
				userManageService.updateAccountThaw(str);
			}
		}
		resultMap.put("result", "1");
		resultMap.put("message", "操作成功");
		return resultMap;
	}

	public List user(String params) {
		List list = new ArrayList();
		list.add("admin");
		list.add("123456");
		return list;
	}

	@Override
	public Map getUserById(String rowKey) throws Exception {
		// TODO Auto-generated method stub
		UserManageService userManageService = (UserManageService)getService("userManageService");
		Map resultMap = userManageService.getUserById(rowKey);
		return resultMap;
	}

	@Override
	public Map updatapwd(Map params) throws Exception {
				UserManageService userManageService = (UserManageService) getService("userManageService");
				Map resultMap = userManageService.updatapwd(params);
				return resultMap;
	}

	@Override
	public List<Map<String, String>> loginOne(Map params) throws Exception {
		// TODO Auto-generated method stub
		UserManageService userManageService = (UserManageService) getService("userManageService");
		List<Map<String, String>> resultList=userManageService.loginOne(params);
		return resultList;
	}

	@Override
	public List<Map<String, String>> UserFrozen(Map params) throws Exception {
		// TODO Auto-generated method stub
				UserManageService userManageService = (UserManageService) getService("userManageService");
				List<Map<String, String>> resultList=userManageService.UserFrozen(params);
				return resultList;
	}

	@Override
	public Map UserFrozen(String params) throws Exception {
		UserManageService userManageService = (UserManageService) getService("userManageService");
		Map resultMap = new HashMap();
		userManageService.updateUserFrozen(params);
		resultMap.put("result", "1");
		resultMap.put("message", "操作成功");
		return resultMap;
	}

	
	
}
