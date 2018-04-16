package com.zkkj.backend.service.base.authentication;

public interface SecurityContextHolder {

	public  void clearContext();
	public  SecurityContext getContext();
	public  void setContext(SecurityContext context);
	
}
