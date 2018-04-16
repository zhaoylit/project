package com.zkkj.backend.service.base.authority;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.zkkj.backend.entity.AuthorityPrivilege;
import com.zkkj.backend.service.base.authentication.DefaultlePrincipalImpl;
import com.zkkj.backend.service.base.authentication.Principal;
import com.zkkj.backend.service.base.authentication.Subject;

@Service("authorityService")
public class WebAuthorityServiceImpl implements AuthorityService {

	private AntPathMatcher matcher = new AntPathMatcher();

	@Override
	public boolean authorize(Subject subject) {

		if (subject == null)
			return false;
		try {
			Map<String, Object> param = subject.getParam();
			if (param == null) {
				return false;
			}
			HttpSession session = (HttpSession) param.get(Subject.AUTH_SESSION);
			String requestUri = (String) param.get("auth_uri");
			if (session == null || session.getAttribute(Subject.AUTH_SUBJECT) == null) {
				return false;
			}
			subject = (Subject) session.getAttribute(Subject.AUTH_SUBJECT);
			if (subject != null && StringUtils.isNotBlank(requestUri)
					&& !subject.getPrincipals().isEmpty()) {
				boolean res = this.dicision(requestUri, subject.getPrincipals());
				if(res){
					subject.setAuthenticated(true);
					subject.setResult(Subject.AUTH_RESULT_ALLOW);
					return true;
				}
			}

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return false;
		}
		return false;
	}

	public boolean dicision(String uri, List<Principal> principalList) {
		try {
			for (Principal principal : principalList) {
				if (parse(uri, principal))
					return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	protected boolean parse(String uri, Principal principal) {

		if (principal instanceof DefaultlePrincipalImpl) {
			DefaultlePrincipalImpl defaultPrincipal = (DefaultlePrincipalImpl) principal;
			List<AuthorityPrivilege> privilegeList = defaultPrincipal
					.getPrivilegeList();
			for (AuthorityPrivilege privilege : privilegeList) {
				String urlPattern = privilege.getUrlPattern();
				if (matches(urlPattern, uri.toLowerCase()))
					return true;
			}
		}
		return false;
	}

	protected boolean matches(String pattern, String path) {
         if(StringUtils.isBlank(pattern))
        	 return false;
		return matcher.match(pattern.toLowerCase(), path.toLowerCase());
	}
	
	public static void main(String[] args){
		AntPathMatcher matcher = new AntPathMatcher();
		System.out.println("======:"+matcher.match("/**/*Order**", "/osen/stats/getLineOrderDetailPage.do"));
	}
}
