package com.zkkj.backend.service.base.authentication;

public interface AuthenticationService {

	public boolean authenticate(Subject subject);
	public boolean login(String username, String password);
	public boolean logout(Subject subject);
	public Subject getSubject(Object param);

}
