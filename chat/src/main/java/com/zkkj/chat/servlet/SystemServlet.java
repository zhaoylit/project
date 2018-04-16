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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zkkj.chat.service.IGroupService;
import com.zkkj.chat.service.IUserService;
import com.zkkj.chat.service.impl.GroupServiceImpl;
import com.zkkj.chat.service.impl.UserServiceImpl;

/**
 * Servlet implementation class SystemServlet
 */
@WebServlet("/system")
public class SystemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IGroupService groupService = new GroupServiceImpl();
	private IUserService userService=new UserServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SystemServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map resultMap = new HashMap();
		String method = request.getParameter("method");
		if("settingInit".equals(method)){
			//系统设置初始化
			//查询所有的群组
			try {
				List<Map> groupList = groupService.getGroupList(null);
				request.setAttribute("groupList", groupList);	
				List<Map> userList=userService.getUserList(null);
				List<Map> userListCopy=new ArrayList<Map>();
				for (int i = 0; i < userList.size(); i++) {
					String accountString=(String) userList.get(i).get("account");
					if(!accountString.equals("admin")){
						userListCopy.add(userList.get(i));
					}
				}
				request.setAttribute("userList", userListCopy);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//条状到系统设置页面
			request.getRequestDispatcher("/chat/setting.jsp").forward(request, response);
			
		}else if("getAllUserByGroupId".equals(method)){
			response.setContentType("application/json; charset=utf-8");
            // 设置字符编码为UTF-8, 这样支持汉字显示  
            response.setCharacterEncoding("UTF-8");  
            PrintWriter out = response.getWriter();  
			//查询素有的用户列表根据群组id
			String groupId = request.getParameter("groupId");
			Map params = new HashMap();
			params.put("groupId", groupId);
			try {
				List<Map> userList = groupService.getAllUserByGroupId(params);
				out.write(JSONArray.fromObject(userList).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("saveGroupUser".equals(method)){
			response.setContentType("application/json; charset=utf-8");
            // 设置字符编码为UTF-8, 这样支持汉字显示  
            response.setCharacterEncoding("UTF-8");  
            PrintWriter out = response.getWriter();  
			//查询素有的用户列表根据群组id
			String groupId = request.getParameter("groupId");
			//选择的用户
			String userIds = request.getParameter("userIds");
			if(userIds == null){
				
				resultMap.put("result", "0");
				resultMap.put("message", "参数错误");
				out.write(JSONObject.fromObject(resultMap).toString());
            	return ;
			}
			String ids [] = userIds.split(",");
			if(ids != null && ids.length > 0){
				//删除之前的群组的所有用户
				Map deleteMap = new HashMap();
				deleteMap.put("groupId", groupId);
				try {
					groupService.deleteAllGroupUserByGroupId(deleteMap);
					List<String> list  = new ArrayList<String>();
					for(String str : ids){
						//批量添加
						list.add(str);
					}
					Map addGroupUserMap = new HashMap();
					addGroupUserMap.put("groupId", groupId);
					addGroupUserMap.put("list", list);
					int count = groupService.addGroupUser(addGroupUserMap);
					if(count > 0){
						resultMap.put("result", "1");
						out.write(JSONObject.fromObject(resultMap).toString());
	                	return ;
					}else{
						resultMap.put("result", "0");
						resultMap.put("message", "操作失败");
						out.write(JSONObject.fromObject(resultMap).toString());
	                	return ;
					}
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else if("saveGroup".equals(method)){
			//添加群组
			response.setContentType("application/json; charset=utf-8");
            // 设置字符编码为UTF-8, 这样支持汉字显示  
            response.setCharacterEncoding("UTF-8");  
            PrintWriter out = response.getWriter();  
			//查询素有的用户列表根据群组id
			String groupName = request.getParameter("groupName");
			Map params = new HashMap();
			params.put("groupName", groupName);
			try {
				int count = groupService.addGroup(params);
				if(count > 0){
					resultMap.put("result", "1");
					resultMap.put("message", "添加群组成功");
					out.write(JSONObject.fromObject(resultMap).toString());
                	return ;
				}else{
					resultMap.put("result", "0");
					resultMap.put("message", "添加群组失败");
					out.write(JSONObject.fromObject(resultMap).toString());
                	return ;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
