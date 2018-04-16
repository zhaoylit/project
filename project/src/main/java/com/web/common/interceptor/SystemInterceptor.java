package com.web.common.interceptor;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.web.common.constant.Constant;
import com.web.modules.authority.dao.FunctionMapper;
import com.web.modules.authority.service.IAuthorityService;
import com.web.util.DesEncrypt;
import com.web.util.ParamsUtil;

public class SystemInterceptor  extends HandlerInterceptorAdapter{
	private final Logger log = Logger.getLogger(SystemInterceptor.class); 
	//登陆地址
	private String loginUrl;
	
	@Autowired
	private IAuthorityService authorityService;
	
	@Autowired
	private FunctionMapper functionMapper;
    /**  
     * 在业务处理器处理请求之前被调用  
     * 如果返回false  
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
     * 如果返回true  
     *    执行下一个拦截器,直到所有的拦截器都执行完毕  
     *    再执行被拦截的Controller  
     *    然后进入拦截器链,  
     *    从最后一个拦截器往回执行所有的postHandle()  
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()  
     */    
    @Override    
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
    	String url = "";	
    	String contextPath = request.getContextPath();
    	String requestUri = request.getRequestURI();
		String servletPath = request.getServletPath(); 
		servletPath = servletPath.substring(1);
    	if(handler instanceof HandlerMethod){
    		HandlerMethod handlerMethod = (HandlerMethod) handler; 
    		url = handlerMethod.getMethod().getName();
        	System.out.println(url+"------------------------进入拦截器");
    	}
		String requestType = request.getHeader("X-Requested-With");
		
    	Map userMap = (Map)request.getSession().getAttribute(Constant.SESSION_USER);
		if(userMap == null){
			//未登录 跳转到登录页面	
			if("XMLHttpRequest".equals(requestType)){
				//ajax请求
//				response.setStatus(499);//设置stats code
				response.setHeader("sessionstatus", "timeout");//在响应头设置session状态
                response.getWriter().print(contextPath+loginUrl);//打印一个返回值
			}else{
				//其他非ajax请求
				StringBuilder sb = new StringBuilder();
				PrintWriter out = response.getWriter();
				sb.append("<html><body>");
				sb.append("<script type=\"text/javascript\" src=\""+contextPath+"/js/jquery-1.8.3.min.js\"></script>");
				sb.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
				sb.append("$(function(){");
				sb.append("window.parent.location.href = '"+contextPath+loginUrl+"';");
				sb.append("})");
				sb.append("</script>");
				sb.append("</body></html>");
				out.print(sb.toString());
				out.close();
			}
			return false;
		}else{	
			String userName = String.valueOf(userMap.get("userName"));
			if("main.do".equals(servletPath) || Constant.ADMIN_ACCOUNT.equals(userName)){
				return true;
			}
			//判断用户是否有权限操作
			int roleId = Integer.parseInt(ParamsUtil.nullDeal(userMap, "roleId", "0"));
			
			StringBuilder sb = new StringBuilder();
			//根据url和roleId查询权限
			Map params = new HashMap();
			params.put("roleId", roleId);
			params.put("funUrl", servletPath);
			List<Map<String, String>> functionList = authorityService.getFunctionsByRoleId(params);
			if(functionList == null || functionList.size() == 0){
				Map funMap = functionMapper.selectByUrl(servletPath);
				String funName = funMap != null ? String.valueOf(funMap.get("funName")) : "";
				//ajax请求
				if("XMLHttpRequest".equals(requestType)){
					DesEncrypt des = new DesEncrypt();  
					response.setCharacterEncoding("UTF-8");
					response.setHeader("sessionstatus", "noauth");//在响应头设置session状态
					response.setHeader("desc",des.encrypt(funName));//在响应头设置session状态   
	                response.getWriter().print("<font color='red'>没有["+funName+"]的操作权限</font>"); //打印一个返回值，	
				}else{
					response.setContentType("text/html; charset=UTF-8");	
					PrintWriter out = response.getWriter();
					sb.append("<html>");
					sb.append("<head>");
					sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+contextPath+"/easyui/themes/default/easyui.css\">");
					sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+contextPath+"/easyui/themes/icon.css\">");
					sb.append("<script type=\"text/javascript\" src=\""+contextPath+"/js/jquery-1.8.3.min.js\"></script>");
					sb.append("<script type=\"text/javascript\" src=\""+contextPath+"/easyui/jquery.easyui.min.js\"></script>");
					sb.append("<script type=\"text/javascript\" src=\""+contextPath+"/easyui/plugins/jquery.dialog.js\"></script>");
					sb.append("</head>");
					sb.append("<body>");
					sb.append("<div style=\"width:100%;height:100%;\">");
					sb.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
					sb.append("$.messager.alert('提示','没有["+funName+"]的操作权限！','warning')");
					sb.append("</script>");
					sb.append("</div>");
					sb.append("</body>");
					sb.append("</html>");
					out.print(sb.toString());
					out.close();
				}
				return false;
			}
			return true;
		}
    }    
    
    /** 
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作    
     * 可在modelAndView中加入数据，比如当前时间 
     */  
    @Override    
    public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {  
    	
    }    
    
    /**  
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等   
     *   
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()  
     */    
    @Override    
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {    
    	
    }

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}    
}
