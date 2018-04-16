package com.zkkj.chat.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zkkj.chat.dao.base.BaseDaoImpl;
import com.zkkj.chat.service.IUserService;
import com.zkkj.chat.util.MD5Util;
import com.zkkj.chat.util.MailUtil;
import com.zkkj.chat.util.RandomPassword;

public class UserServiceImpl extends BaseDaoImpl implements IUserService {

	@Override
	public List<Map> getUserList(Map params) throws Exception {
		// TODO Auto-generated method stub
		return selectList("userInfo.getUserList",params);
	}

	@Override
	public Map getUserById(Map params) throws Exception {
		// TODO Auto-generated method stub
		return (Map) selectOneByParam("userInfo.selectUserById", params);
	}

	@Override
	public int updateUser(Map params) throws Exception {
		// TODO Auto-generated method stub
		return updateByParam("userInfo.updateUser", params);
	}
	
		@Override
	public int addUser(Map params) throws Exception {
		// TODO Auto-generated method stub
		int count =0;
		count =insert("userInfo.addUser", params);
		return count;
	}

	@Override
	public void timerUpdatePWD() throws Exception {
		// TODO Auto-generated method stub
		List<Map> userList=selectList("userInfo.getUserList");
		for (int i = 0; i < userList.size(); i++) {
			int userId = (int) userList.get(i).get("id");
			String email=(String) userList.get(i).get("account");
			if(!email.equals("admin")){
				String password=RandomPassword.makeRandomPassword();
				String MD5Password=MD5Util.string2MD5(password);
				Map params=new HashMap();
				params.put("userId", userId);
				params.put("password", MD5Password);
				updateByParam("userInfo.updateUser", params);
//				MailUtil.sendEmail(email, "众享互动改密通知", "众享互动IM通信的账户为"+email+"的用户，您的登陆密码已经修改为："+password+" 请牢记本次密码，谢谢！系统邮件，勿回！");
			}
		}
	}

	@Override
	public String updateUserPWDOne(String userId) throws Exception {
		// TODO Auto-generated method stub
		Map paramMap=new HashMap();
		paramMap.put("userId", userId);
		List<Map> usersList=selectList("userInfo.selectUserById",paramMap);
		String email=(String) usersList.get(0).get("account");
		String password=RandomPassword.makeRandomPassword();
		String MD5Password=MD5Util.string2MD5(password);
		Map updateParaMap =new HashMap();
		updateParaMap.put("userId", userId);
		updateParaMap.put("password", MD5Password);
		update("userInfo.updateUser", updateParaMap);
		MailUtil.sendEmail(email, "众享互动改密通知", "众享互动IM通信的账户为"+email+"的用户，您的登陆密码已经修改为："+password+" 请牢记本次密码，谢谢！系统邮件，勿回！");
		return password;
	}
		
		
	
}
