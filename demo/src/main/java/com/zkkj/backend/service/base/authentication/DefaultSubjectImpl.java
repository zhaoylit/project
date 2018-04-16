package com.zkkj.backend.service.base.authentication;

import java.util.List;
import java.util.Map;

public class DefaultSubjectImpl implements Subject {

	private String subjectId;
	
	private boolean isAuthenticated;
	
	private Map<String, Object> param;
	
	private List<Principal> principals;
	
	private String result;

	@Override
	public List<Principal> getPrincipals() {
		return principals;
	}
	
	@Override
	public void setPrincipals(List<Principal> principals) {
		this.principals = principals;
	}
	
	public Map<String, Object> getParam() {
		return this.param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
