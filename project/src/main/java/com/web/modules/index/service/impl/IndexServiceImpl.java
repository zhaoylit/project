package com.web.modules.index.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.web.common.constant.Constant;
import com.web.modules.index.dao.IndexMapper;
import com.web.modules.index.service.IIndexService;
import com.web.modules.user.dao.UserMapper;
import com.web.util.MD5Util;
import com.web.util.ParamsUtil;

@Service("indexService")
public class IndexServiceImpl implements IIndexService{
	@Autowired
	private IndexMapper indexMapper;
	@Autowired
	private UserMapper userMapper;
	@Override
	public Map checkLogin(Map params) throws Exception {
		// TODO Auto-generated method stub
		Map resultMap = new HashMap();
		String userName = ParamsUtil.nullDeal(params, "userName", "");
		String passWord = ParamsUtil.nullDeal(params, "passWord", "");
		if("".equals(userName) || "".equals(passWord)){
			resultMap.put("resultCode", "0");
			resultMap.put("resultMsg", "用户名或密码为空，请输入");
			return resultMap;
		}
		Map userParams = new HashMap();
		userParams.put("userName", userName);
		List<Map> userList = userMapper.selectByParams(userParams);
		if(userList == null || userList.size() == 0){
			resultMap.put("resultCode", "0");
			resultMap.put("resultMsg", "账号不存在");
			return resultMap;
		}
		Map userMap = userList.get(0);
		String passWord_q = ParamsUtil.nullDeal(userMap, "passWord", "");
		if("".equals(passWord_q)){
			resultMap.put("resultCode", "0");
			resultMap.put("resultMsg", "账号存在异常");
			return resultMap;
		}
		if(!passWord_q.equals(MD5Util.string2MD5(passWord))){
			resultMap.put("resultCode", "0");
			resultMap.put("resultMsg", "密码不正确");
			return resultMap;
		}
		resultMap.put("resultCode", "1");
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		request.getSession().setAttribute(Constant.SESSION_USER, userMap);
		return resultMap;
	}

}
