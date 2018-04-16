package com.zkkj.backend.service.base.authentication;

import java.util.List;

import com.zkkj.backend.entity.biz.ZkkjUser;


public interface AuthenticationProvider {
	
	public boolean authenticate(Subject subject);
	public List<ZkkjUser> login(Subject subject);
	public boolean logout(Subject subject);
}
