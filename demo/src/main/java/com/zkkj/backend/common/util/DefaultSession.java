package com.zkkj.backend.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultSession {

	
	private String  sessionId;
	
	private Map<String,Object>  container;
	
	public DefaultSession(String sessionId){
		  this.sessionId = sessionId;
		  container = new HashMap<String,Object>();
	}
	
	
	public String getSessionId() {
		return sessionId;
	}



	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}



	public Map<String, Object> getContainer() {
		return container;
	}



	public void setContainer(Map<String, Object> container) {
		this.container = container;
	}

	
	public String getId() {
		// TODO Auto-generated method stub
		return sessionId;
	}

	
	public Object getAttribute(String name) {
		// TODO Auto-generated method stub
		return container.get(name);
	}

	
	public Object getValue(String name) {
		// TODO Auto-generated method stub
		return container.get(name);
	}

	
	public Set<String> getAttributeNames() {
		// TODO Auto-generated method stub
		return container.keySet();
	}

	
	public  Set<String> getValueNames() {
		// TODO Auto-generated method stub
		return container.keySet();
	}

	
	public void setAttribute(String name, Object value) {
		// TODO Auto-generated method stub
		container.put(name, value);
	}

	
	public void putValue(String name, Object value) {
		// TODO Auto-generated method stub
		container.put(name, value);
	}

	
	public void removeAttribute(String name) {
		// TODO Auto-generated method stub
		container.remove(name);
	}

	
	public void removeValue(String name) {
		// TODO Auto-generated method stub
		container.remove(name);
	}

}
