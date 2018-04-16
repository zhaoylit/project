package com.zj.api.common.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;

import com.zj.api.common.utils.ParameterRequestWrapper;

/**
 * 通用的过滤器
 * @author ZYL_PC
 *
 */
public class CommonFilter  extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
    	/*String contextPath = request.getContextPath();
    	String requestUri = request.getRequestURI();
		String servletPath = request.getServletPath(); 
		servletPath = servletPath.substring(1);
		//http请求方式
		String requestType = request.getHeader("X-Requested-With");
		//通过controller 请求的接口，添加标志
		Map<String, String[]> m = new HashMap<String, String[]>(request.getParameterMap());
		m.put("accessType", new String[] { "1" });
		m.put("reqUri",new String[]{servletPath});
		
		ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(request, m);  
        filterChain.doFilter(requestWrapper, response);  */
	}

	
}
