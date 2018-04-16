package com.zkkj.chat.service.impl;

import java.util.List;
import java.util.Map;

import com.zkkj.chat.dao.base.BaseDaoImpl;
import com.zkkj.chat.service.IMessageService;
import com.zkkj.chat.util.Page;

public class MessageServiceImpl extends BaseDaoImpl implements IMessageService {

	@Override
	public List<Map> getMessageList(Map params,Page page ) throws Exception {
		// TODO Auto-generated method stub
		return selectList("message.getMessageList", params,page);
	}

	@Override
	public int addMessage(Map params) throws Exception {
		// TODO Auto-generated method stub
		return insert("message.addMessage", params);
	}

	@Override
	public int deleteMessage(Map params) throws Exception {
		// TODO Auto-generated method stub
		return deleteByParam("message.deleteMessage", params);
	}

}
