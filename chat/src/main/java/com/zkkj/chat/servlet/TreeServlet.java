package com.zkkj.chat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RespectBinding;

import com.zkkj.chat.service.IGroupService;
import com.zkkj.chat.service.IUserService;
import com.zkkj.chat.service.impl.GroupServiceImpl;
import com.zkkj.chat.service.impl.UserServiceImpl;
import com.zkkj.chat.util.PropertiesConfig;

import net.sf.json.JSONArray;

@WebServlet("/TreeServlet")

public class TreeServlet extends HttpServlet {
	
	private IUserService userServices= new UserServiceImpl();
	private IGroupService groupServce=new GroupServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setCharacterEncoding("UTF-8"); 
		resp.setHeader("Content-type", "text/html;charset=UTF-8");
		try {
			String return_path_z_ = PropertiesConfig.getProperties("return_path_z");
		PrintWriter out =resp.getWriter();
		List<Map> list=new ArrayList<Map>();
		List<Map> userList=new ArrayList<Map>();
		List<Map> userListInfoList=userServices.getUserList(null);
			for (int i = 0; i < userListInfoList.size(); i++) {
				Map userMap=new HashMap();
				userMap.put("id", userListInfoList.get(i).get("id"));
				String userAccount=(String) userListInfoList.get(i).get("account");
				String headUrl = (String) userListInfoList.get(i).get("headUrl");
				if(userAccount==null || userAccount.equals("")){
					userAccount ="";
				}
				String userAccountString;
				if (userAccount == "") {
					userAccountString="";
				}else {
					userAccountString="("+userAccount+")";
				}
				String headText = "<img src='"+(return_path_z_ + headUrl)+"' style='width:15px;height:15px;'>&nbsp;";
				String textNameString=(String) userListInfoList.get(i).get("nickName");
				String onlineStatus =  String.valueOf(userListInfoList.get(i).get("onlineStatus"));
				String onlineStatusText = "["+("1".equals(onlineStatus) ? "<span style='color:green;'>在线</span>" : "<span style='color:gray;'>离线</span>")+"]";
				userMap.put("text", headText + textNameString + onlineStatusText +userAccountString);
				userMap.put("iconCls", "icon-man");
				Map userAttributesChildMap = new HashMap();
				userAttributesChildMap.put("type", "1");
				userAttributesChildMap.put("level", "2");
				userMap.put("attributes", userAttributesChildMap);
				userList.add(userMap);
			}
		Map userMap=new HashMap();
		userMap.put("id", "1");
		userMap.put("text", "用户列表");
		userMap.put("iconCls", "icon-man");
		Map userAttributesMap =new HashMap();
		userAttributesMap.put("level", "1");
		userAttributesMap.put("type", "0");
		userMap.put("attributes", userAttributesMap);
		userMap.put("state", "open");
		userMap.put("children", userList);
		list.add(userMap);
		List<Map> groupList = new ArrayList<Map>();
		Map userSessionMap=(Map) req.getSession().getAttribute("userSession");
		if(userSessionMap != null){
			Map params=new HashMap();
			params.put("userId", userSessionMap.get("id"));
			List<Map> groupByUserIdList=groupServce.getGroupbyUserId(params);
			System.out.println("**************通过userId查询该用户拥有的群组************"+groupByUserIdList);
			for (int i = 0; i < groupByUserIdList.size(); i++) {
				Map groupInfoMap =new HashMap();
				groupInfoMap.put("id", groupByUserIdList.get(i).get("id"));
				groupInfoMap.put("text", groupByUserIdList.get(i).get("groupName"));
				groupInfoMap.put("iconCls", "icon-man");
				Map groupAttributesChildMap = new HashMap();
				groupAttributesChildMap.put("type", "2");
				groupAttributesChildMap.put("level", "2");
				groupInfoMap.put("attributes", groupAttributesChildMap);
				groupList.add(groupInfoMap);
			}
		}
		Map groupMap=new HashMap();
		groupMap.put("id", "2");
		groupMap.put("text", "讨论组列表");
		groupMap.put("iconCls", "icon-man");
		Map groupAttributesMap =new HashMap();
		groupAttributesMap.put("level", "1");
		groupAttributesMap.put("type", "0");
		groupMap.put("attributes", groupAttributesMap);
		groupMap.put("state", "opan");
		groupMap.put("children", groupList);
		list.add(groupMap);
		
		JSONArray jsonArray =new JSONArray().fromObject(list);
		System.out.println(jsonArray);
		out.write(jsonArray.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
