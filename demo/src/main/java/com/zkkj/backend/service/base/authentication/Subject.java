package com.zkkj.backend.service.base.authentication;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Subject {

	public static final String AUTH_RESULT_NOLOGIN = "no_login";
	public static final String AUTH_RESULT_REJECT = "auth_reject";
	public static final String AUTH_RESULT_ALLOW = "auth_allow";
	
	public static final String AUTH_SESSION = "auth_session";
	public static final String AUTH_URI = "auth_uri";
	public static final String AUTH_SUBJECT = "auth_subject";
	public static final String AUTH_USERNAME = "auth_username";
	public static final String AUTH_PASSWORD = "auth_password";
	public static final String AUTH_NICKNAME = "auth_nickname";
	
	public void setSubjectId(String subjectId);

	public String getSubjectId();

	List<Principal> getPrincipals();
	
	public void setPrincipals(List<Principal> principals);

	Map<String, Object> getParam();
	
	public void setParam(Map<String, Object> param);

	public boolean isAuthenticated();
	
	public void setAuthenticated(boolean isAuthenticated);
	
	public void setResult(String result);
	
	public String getResult();

}
