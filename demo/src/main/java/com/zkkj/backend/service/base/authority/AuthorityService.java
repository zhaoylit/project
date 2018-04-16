package com.zkkj.backend.service.base.authority;

import com.zkkj.backend.service.base.authentication.Subject;

public interface AuthorityService {
	
	public boolean authorize(Subject subject);

}
