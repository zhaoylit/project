package com.zkkj.backend.service.base.authentication;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.zkkj.backend.entity.biz.ZkkjUser;


public class WebAuthenticationServiceImpl implements AuthenticationService {

	private List<AuthenticationProvider> providers;

	@Override
	public boolean authenticate(Subject subject) {

		Iterator<AuthenticationProvider> iter = providers.iterator();

		while (iter.hasNext()) {
			AuthenticationProvider provider = iter.next();
			if (provider.authenticate(subject))
				return true;
		}
		return false;
	}

	public List<ZkkjUser> login(String username, String password, HttpServletRequest request) {

		Iterator<AuthenticationProvider> iter = providers.iterator();

		while (iter.hasNext()) {
			AuthenticationProvider provider = iter.next();
			if (provider instanceof HttpAuthenticationProviderImpl) {
				String sessionId = request.getSession().getId();
				DefaultSubjectImpl subject = new DefaultSubjectImpl();
				Map<String, Object> param = new HashMap<String, Object>();
				subject.setSubjectId(sessionId);
				param.put(Subject.AUTH_SESSION, request.getSession());
				param.put(Subject.AUTH_USERNAME, username);
				param.put(Subject.AUTH_PASSWORD, password);
				subject.setParam(param);
				List<ZkkjUser> result = provider.login(subject);
				if (result!=null){
					return result;
				}
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean logout(Subject subject) {
		// TODO Auto-generated method stub
		if(subject==null || subject.getParam() ==null)
			return false;
		HttpSession session = (HttpSession)subject.getParam() .get(Subject.AUTH_SESSION);
		if(session!=null){
			session.removeAttribute(Subject.AUTH_USERNAME);
			session.removeAttribute(Subject.AUTH_SUBJECT);
			return true;
		}
		return false;
	}

	@Override
	public Subject getSubject(Object request) {
		try{
			if(request!=null && request instanceof HttpServletRequest){			
				return (Subject)(((HttpServletRequest)request).getSession().getAttribute(Subject.AUTH_SUBJECT));
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public List<AuthenticationProvider> getProviders() {
		return providers;
	}

	public void setProviders(List<AuthenticationProvider> providers) {
		this.providers = providers;
	}

	@Override
	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
