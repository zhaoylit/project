package com.zkkj.backend.service.biz.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.util.StringUtils;
import com.zkkj.backend.service.common.BaseService;
import com.zkkj.platform.user.UserGroupManageService;

@Service("userGroupService")
public class UserGroupServiceImpl extends BaseService implements IUserGroupService {
	//查询所有的用户组列表
		@Override
		public List<Map<String, String>> getUserGroupsList(Map params)
				throws Exception {
					UserGroupManageService UserGroupManageService = (UserGroupManageService) getService("userGroupManageService");
					List<Map<String, String>> resultList = UserGroupManageService.getUserGroups(params);
					return resultList;
		}
		//编辑和添加用户组
		@Override
		public Map addOrEditUserGroups(Map params) throws Exception {
					String rowKey = ParamsUtil.nullDeal(params, "rowKey", "");
					UserGroupManageService UserGroupManageService = (UserGroupManageService) getService("userGroupManageService");
					Map resultMap = UserGroupManageService.addOrUpdateUserGroups(params);
					return resultMap;
		}
		//删除用户组
		@Override
		public Map deleteUserGroups(Map params) throws Exception {
			UserGroupManageService UserGroupManageService = (UserGroupManageService) getService("userGroupManageService");

			Map resultMap = new HashMap();
			String ids = ParamsUtil.nullDeal(params, "ids", "");
			String array[] = ids.split(",");
			if (StringUtils.isNotBlank(ids)) {
				for (String str : array) {
					UserGroupManageService.deleteUserGroups(str);
				}
			}
			resultMap.put("result", "1");
			resultMap.put("message", "操作成功");
			return resultMap;
		}
		//获得一条用户组
		@Override
		public Map getUserGroupsById(String rowKey) throws Exception {
			UserGroupManageService UserGroupManageService = (UserGroupManageService)getService("userGroupManageService");
			Map resultMap = UserGroupManageService.getUserGroupsById(rowKey);
			return resultMap;
		}

		@Override
		public Map userAddUserGroups(Map params) throws Exception {
			UserGroupManageService UserGroupManageService = (UserGroupManageService)getService("userGroupManageService");
			String userId=ParamsUtil.nullDeal(params, "userid", "");
			String ids=ParamsUtil.nullDeal(params, "ids", "");
			String[] idsArray=null;
			idsArray=ids.split(",");
			for(int a = 0;a<idsArray.length;a++){
				Map resultMap = UserGroupManageService.userAddUsers(userId,idsArray[a]);
			}
			return null;
		}

		@Override
		public List<Map<String, String>> getUserGroupListInfo(String userId)
				throws Exception {
			if(userId==null&&"".equals(userId)){
				return null;
			}
			UserGroupManageService UserGroupManageService = (UserGroupManageService)getService("userGroupManageService");
			List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
			resultList = UserGroupManageService.getUserGroupsListInfo(userId);
			return resultList;
		}
		@Override
		public Map deleteUserGroup(Map params) throws Exception {
			UserGroupManageService UserGroupManageService = (UserGroupManageService)getService("userGroupManageService");
			Map map=new HashMap();
			String userid=ParamsUtil.nullDeal(params, "userid", "");
			if(userid==null&&"".equals(userid)){
				return null;
			}
			List<Map<String, String>> list =UserGroupManageService.getUserGroupsListInfo(userid);
			for (int i = 0; i <list.size(); i++) {
				String rowKey=list.get(i).get("rowKey");
				UserGroupManageService.deleteByUserId(rowKey);
			}
			return null;
		}
		
		

}
