package com.zkkj.chat.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zkkj.chat.service.IGroupService;
import com.zkkj.chat.service.IMessageService;
import com.zkkj.chat.service.IUserService;
import com.zkkj.chat.service.impl.GroupServiceImpl;
import com.zkkj.chat.service.impl.MessageServiceImpl;
import com.zkkj.chat.service.impl.UserServiceImpl;
import com.zkkj.chat.socket.Websocket;
import com.zkkj.chat.util.DateUtil;
import com.zkkj.chat.util.Page;
import com.zkkj.chat.util.ParamsUtil;
import com.zkkj.chat.util.TimeFormatUtil;

/**
 * Servlet implementation class MobileSerblet
 */
@WebServlet("/mobile")
public class MobileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private IUserService userService = new UserServiceImpl();
    
	private IGroupService groupService = new GroupServiceImpl();
	
	private IMessageService messageService = new MessageServiceImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MobileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String method = request.getParameter("method");
		if("chatInit".equals(method)){
			//聊天类型，1.单聊，2.群聊
			String type = request.getParameter("type");
			//消息发送者的用户id 
			String fromUserId = request.getParameter("fromUserId");
			//消息接受者的用户id,对于单聊而言
			String toUserId  = request.getParameter("toUserId");
			//群组id 
			String groupId = request.getParameter("groupId");
			
			try {
				if("1".equals(type)){
					//单聊，查询聊天对方的昵称
					Map params = new HashMap();
					params.put("userId", toUserId);
					Map toUserMap = userService.getUserById(params);
					request.setAttribute("toUserNickName", toUserMap.get("nickName"));
				}
				if("2".equals(type)){
					//群聊，查询群组的名称
					Map params = new HashMap();
					params.put("groupId", groupId);
					Map groupMap = groupService.getGroupById(params);
					request.setAttribute("groupName", groupMap.get("groupName"));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//聊天页面初始化
			request.setAttribute("type", type);
			request.setAttribute("fromUserId", fromUserId);
			request.setAttribute("toUserId", toUserId);
			request.setAttribute("groupId", groupId);

			//最近的聊天消息记录
			List<Map> messageList = getMessageList(type, fromUserId, toUserId, groupId,"");
			
			//设置消息内容
			request.setAttribute("messageList", messageList);
			
			//发送群系统消息
			try {
				new Websocket().sendGroupMessage("",fromUserId, groupId, "进来了",true,"4","");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String userId = "";
			Map userMap = (Map) request.getSession().getAttribute("userSession");
			if(userMap != null ){
				userId = String.valueOf(userMap.get("id"));
			}
			//查询群组成员
			Map groupUserParamsMap = new HashMap();
			groupUserParamsMap.put("userId", userId);
			try {
				List<Map> groupUserList = groupService.getGroupbyUserId(groupUserParamsMap);
				request.setAttribute("groupUserList",groupUserList);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getRequestDispatcher("/chat/mobile/chat.jsp").forward(request,response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/**
	 * 查询消息内容
	 * @param chatType  聊天类型，1.单聊，2.群聊
	 * @param fromUserId  消息发送方的用户id 
	 * @param toUserId 消息接收方的用户id 
	 * @param groupId  如果chatType=2  为群组的id
	 * @return
	 */
	private List<Map> getMessageList(String chatType,String fromUserId,String toUserId,String groupId,String endTime){
		List<Map> messageList = new ArrayList<Map>();
		//查询聊天记录
  		if("1".equals(chatType)){
			try {
				Map fromUserMessageParamsMap = new HashMap();
				fromUserMessageParamsMap.put("chatType", chatType);
				fromUserMessageParamsMap.put("fromUserId", fromUserId);
				fromUserMessageParamsMap.put("toUserId", toUserId);
				fromUserMessageParamsMap.put("endTime", endTime);
				Page page = new Page();
				page.setNumPerPage(20);
				List<Map> fromUserMessageList = messageService.getMessageList(fromUserMessageParamsMap,page);
				
				Map toUserMessageParamsMap = new HashMap();
				toUserMessageParamsMap.put("chatType", chatType);
				toUserMessageParamsMap.put("fromUserId", toUserId);
				toUserMessageParamsMap.put("toUserId", fromUserId);
				toUserMessageParamsMap.put("endTime", endTime);
				
				Page page1 = new Page();
				page1.setNumPerPage(20);
				List<Map> toUserMessageList = messageService.getMessageList(toUserMessageParamsMap,page1);
				//添加消息发送者的消息内容
				messageList.addAll(fromUserMessageList);
				//添加消息接受者的消息内容
				messageList.addAll(toUserMessageList);
				
				//对消息内容进行时间倒叙排列
				Collections.sort(messageList, new Comparator<Map>(){  
					public int compare(Map o1,Map o2){
						String time1 = ParamsUtil.nullDeal(o1, "addedTime", "");
						String time2 = ParamsUtil.nullDeal(o2, "addedTime", "");
						return time1.compareTo(time2);
					}  
				});
				List<Map> subList = messageList.subList(messageList.size() >= 20 ? messageList.size()-20 : 0, messageList.size());
				//取出来后面20条消息
				//对消息列表进行日期显示处理
				for(Map map : subList){
					map.put("dateShow", TimeFormatUtil.getInterval(DateUtil.strToDateLong(String.valueOf(map.get("addedTimeShow")))));//消息发送时间，用于显示
				}
				return subList;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if("2".equals(chatType)){
			//查询群聊的消息记录
			Map groupMessageParamsMap = new HashMap();
			groupMessageParamsMap.put("groupId", groupId);
			groupMessageParamsMap.put("endTime", endTime);
			
			Page page = new Page();
			page.setNumPerPage(20);
			try {
				messageList = messageService.getMessageList(groupMessageParamsMap,page);
				
				//对消息内容进行时间倒叙排列
				Collections.sort(messageList, new Comparator<Map>(){  
					public int compare(Map o1,Map o2){
						String time1 = ParamsUtil.nullDeal(o1, "addedTime", "");
						String time2 = ParamsUtil.nullDeal(o2, "addedTime", "");
						return time1.compareTo(time2);
					}  
				});
				//对消息列表进行日期显示处理
				for(Map map : messageList){
					map.put("dateShow", TimeFormatUtil.getInterval(DateUtil.strToDateLong(String.valueOf(map.get("addedTimeShow")))));//消息发送时间，用于显示
				}
				return messageList;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return messageList;
		
	}

}
