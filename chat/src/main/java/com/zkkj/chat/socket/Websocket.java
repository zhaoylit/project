package com.zkkj.chat.socket;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;

import com.zkkj.chat.service.IGroupService;
import com.zkkj.chat.service.IMessageService;
import com.zkkj.chat.service.IUserService;
import com.zkkj.chat.service.impl.GroupServiceImpl;
import com.zkkj.chat.service.impl.MessageServiceImpl;
import com.zkkj.chat.service.impl.UserServiceImpl;
import com.zkkj.chat.util.DateUtil;
import com.zkkj.chat.util.ParamsUtil;
import com.zkkj.chat.util.TimeFormatUtil;

@ServerEndpoint(value="/webSocket")
public class Websocket {
	
	private IUserService service = new UserServiceImpl();
	
	private IGroupService groupService = new GroupServiceImpl();
	
	private IMessageService messageService = new MessageServiceImpl();
	
	//保存所有的连接的客户端对象
	static Map<String, Session> clientsMap = new ConcurrentHashMap<String, Session>();
	//心跳检测 map
	static Map<String, Long> heartMap = new HashMap<String, Long>();
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    /*
     *使用@Onopen注解的表示当客户端链接成功后的回掉。参数Session是可选参数
   	 这个Session是WebSocket规范中的会话，表示一次会话。并非HttpSession
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        Map params = session.getRequestParameterMap();
        String userId = "";
        Object userIdObject = params.get("userId");
        if (userIdObject != null) {
        	userId = ((List<String>)userIdObject).get(0);
        	clientsMap.put(userId, session);
			session.getUserProperties().put("userId", userId);	
		}
        try {
       	 //修改用户的在线状态
            Map userUpdateParamsMap = new HashMap();
            userUpdateParamsMap.put("onlineStatus", "1");
            userUpdateParamsMap.put("userId", userId);
			 service.updateUser(userUpdateParamsMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /*
	*用户断开链接后的回调，注意这个方法必须是客户端调用了断开链接方法后才会回调
	*/
    @OnClose
    public void onClose(Session session) {
    	 Map params = session.getRequestParameterMap();
         String userId = "";
         Object userIdObject = params.get("userId");
         if (userIdObject != null) {
         	userId = ((List<String>)userIdObject).get(0);
 		 }
         
         clientsMap.remove(userId);
         
         try {
			session.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
        
         try {
        	 //修改用户的在线状态
             Map userUpdateParamsMap = new HashMap();
             userUpdateParamsMap.put("onlineStatus", "0");
             userUpdateParamsMap.put("userId", userId);
			 service.updateUser(userUpdateParamsMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @OnMessage
    public void onMessage(String message,Session session){
    	JSONObject json = JSONObject.fromObject(message);
    	String type = json.getString("type");
    	String message_ = json.getString("message");
    	String fromUserId = json.getString("fromUserId");
    	String toUserId = json.getString("toUserId");
    	String goupId = json.getString("groupId");
    	String messageType = json.getString("messageType");//消息类型
    	String extra = json.getString("extra");//额外属性
    	if("1".equals(type)){
    		//单聊
    		try {
    			if("1".equals(messageType) || "3".equals(messageType)){
    				sendMessageToOne("",fromUserId,toUserId,message_,false,messageType,extra);
    			}else if("4".equals(messageType)){
    				//更新用户id的心跳时间
    	    		heartMap.put(fromUserId,new Date().getTime());
    			}else if("5".equals(messageType)){
    				//撤回消息
    				String msgId = json.getString("msgId");
    				//撤回 消息
    				sendMessageToOne(msgId,fromUserId,toUserId,message_,true,messageType,extra);
    			}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("单聊失败==========================" + e.getMessage());
			}
    	}else if("2".equals(type)){
    		//群聊
    		try {
    			if("1".equals(messageType) || "3".equals(messageType)){
    				sendGroupMessage("",fromUserId, goupId, message_,false,messageType,extra);
    			}else if("4".equals(messageType)){
    				//更新用户id的心跳时间
    	    		heartMap.put(fromUserId,new Date().getTime());
    			}else if("5".equals(messageType)){
    				//撤回消息
    				String msgId = json.getString("msgId");
    				sendGroupMessage(msgId,fromUserId, goupId, message_,true,messageType,extra);
    			}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("群聊失败==========================" + e.getMessage());
			}
    	}
    }
    //点对点消息
    public void sendMessageToOne(String msgId_,String fromUserId, String toUserId,String message,boolean isSystem,String messageType,String extra) throws Exception{
    	if(!"".equals(toUserId) && toUserId != null){
    		//
    		Session fromUserSession = clientsMap.get(fromUserId);
    		Session toUserSession = clientsMap.get(toUserId);
    		//查询消息发送者的用户信息
    		Map params = new HashMap();
    		params.put("userId", fromUserId);
        	Map fromUser = service.getUserById(params);
        	//消息发送者的昵称
        	String fromUserNickName = "";
        	String fromUserHeadUrl = "";
        	if(fromUser != null){
        		fromUserNickName = ParamsUtil.nullDeal(fromUser, "nickName", "");
        		fromUserHeadUrl = ParamsUtil.nullDeal(fromUser, "headUrl", "");
        	}
        	
        	JSONObject messageJson = new JSONObject();
        	messageJson.put("message", message);
        	messageJson.put("fromUserId", fromUserId);
        	messageJson.put("toUserId", toUserId);
        	messageJson.put("headUrl", fromUserHeadUrl);
        	messageJson.put("nickName", fromUserNickName);
        	messageJson.put("chatType", "1");//单聊
        	messageJson.put("messageType",messageType);//消息类型
        	messageJson.put("isSystem", isSystem);//是否是系统消息
        	messageJson.put("extra", extra);//附加属性
        	messageJson.put("msgDate", DateUtil.getStringDate());//消息发送的日期
        	messageJson.put("dateShow", TimeFormatUtil.getInterval(DateUtil.strToDateLong(DateUtil.getStringDate())));//消息发送时间，用于显示
        	String msgId = "";
    		try {
    			if(!"4".equals(messageType)){
    				//保存消息到数据库
    				Map addMessageParamsMap = new HashMap();
    				addMessageParamsMap.put("fromUserId", fromUserId);
    				addMessageParamsMap.put("toUserId", toUserId);
    				addMessageParamsMap.put("chatType", "1");
    				addMessageParamsMap.put("messageType",messageType);
    				addMessageParamsMap.put("content", message);
    				addMessageParamsMap.put("extra", extra);
    				messageService.addMessage(addMessageParamsMap);
    				//得到消息的id 
    				msgId = String.valueOf(addMessageParamsMap.get("id"));
    			}
    			
    			if("5".equals(messageType)){
    				//消息撤回,返回消息撤回的id
	    			msgId = msgId_;
	    			//删除消息内容
	    			Map deleteMessageParamsMap = new HashMap();
	    			deleteMessageParamsMap.put("msgId", msgId);
	    			messageService.deleteMessage(deleteMessageParamsMap);
    			}
    			
				//设置消息id
				messageJson.put("msgId", msgId);
				
				//发送消息
				if(fromUserSession != null){
    				//给发送消息着推送消息
    				fromUserSession.getBasicRemote().sendText(messageJson.toString());
    			}
    			if(toUserSession != null){
        			//给接受消息着推送消息
        			toUserSession.getBasicRemote().sendText(messageJson.toString());
    			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    //群聊消息
    public void sendGroupMessage(String msgId_,String fromUserId,String groupId,String message,boolean isSystem,String messageType,String extra) throws Exception{
    	//查询消息发送者的用户信息
		Map fromUserParams = new HashMap();
		fromUserParams.put("userId", fromUserId);
    	Map fromUser = service.getUserById(fromUserParams);
    	//消息发送者的昵称
    	String fromUserNickName = "";
    	String fromUserHeadUrl = "";
    	if(fromUser != null){
    		fromUserNickName = ParamsUtil.nullDeal(fromUser, "nickName", "");
    		fromUserHeadUrl = ParamsUtil.nullDeal(fromUser, "headUrl", "");
    	}
    	
    	JSONObject messageJson = new JSONObject();
    	messageJson.put("message", message);//消息内容
    	messageJson.put("fromUserId", fromUserId);//消息发送者
    	messageJson.put("groupId", groupId);//群组id 
    	messageJson.put("headUrl", fromUserHeadUrl);//消息发送者的用户头像
    	messageJson.put("nickName", fromUserNickName);//消息发送者的用户昵称
    	messageJson.put("chatType", "2");//群聊
    	messageJson.put("messageType",messageType);//消息类型
    	messageJson.put("isSystem", isSystem);//是否是系统消息
    	messageJson.put("extra", extra);//附加属性
    	messageJson.put("msgDate", DateUtil.getStringDate());//消息发送的日期
    	messageJson.put("dateShow", TimeFormatUtil.getInterval(DateUtil.strToDateLong(DateUtil.getStringDate())));//消息发送时间，用于显示
    	
    	//保存消息内容到数据库
    	String msgId = "";
    	
    	if(!"4".equals(messageType)){
    		//保存消息到数据库
    		Map addMessageParamsMap = new HashMap();
    		addMessageParamsMap.put("fromUserId", fromUserId);
    		addMessageParamsMap.put("chatType", "2");
    		addMessageParamsMap.put("messageType",messageType);
    		addMessageParamsMap.put("groupId",groupId);
    		addMessageParamsMap.put("content", message);
    		addMessageParamsMap.put("extra", extra);
    		messageService.addMessage(addMessageParamsMap);
    		
    		msgId = String.valueOf(addMessageParamsMap.get("id"));
    	}
		
    	if("5".equals(messageType)){
			//消息撤回
			msgId = msgId_;
			//删除消息内容
			Map deleteMessageParamsMap = new HashMap();
			deleteMessageParamsMap.put("msgId", msgId);
			messageService.deleteMessage(deleteMessageParamsMap);
		}
    	
    	//设置消息id
		messageJson.put("msgId", msgId);
		
		//根据群组id查询群成员
    	Map params = new HashMap();
    	params.put("groupId", groupId);
    	List<Map> userList = groupService.getGroupUserList(params);
    	
    	//循环给群成员发送消息
    	for(Map user_ : userList){
    		Session session =clientsMap.get(String.valueOf(user_.get("id")));
    		if(session != null){
    			session.getAsyncRemote().sendText(messageJson.toString());
    		}
    	}
    }
	public static Map<String, Long> getHeartMap() {
		return heartMap;
	}
	public static void setHeartMap(Map<String, Long> heartMap) {
		Websocket.heartMap = heartMap;
	}
}
