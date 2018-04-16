package com.zkkj.chat.service;

import java.util.List;
import java.util.Map;

import com.zkkj.chat.util.Page;

public interface IMessageService {
	//查询消息列表
	List<Map> getMessageList(Map params,Page page) throws Exception;
	//添加消息内容
	int addMessage(Map params) throws Exception;
	//删除消息
	int deleteMessage(Map params) throws Exception;
}
