package com.zkkj.backend.common.aop;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.zkkj.backend.common.exception.AdvertException;
import com.zkkj.backend.common.exception.ApkException;
import com.zkkj.backend.common.exception.AuthException;
import com.zkkj.backend.common.exception.BaseDaoException;
import com.zkkj.backend.common.exception.BaseException;
import com.zkkj.backend.common.exception.DataBaseException;
import com.zkkj.backend.common.exception.DeviceException;
import com.zkkj.backend.common.exception.FileException;
import com.zkkj.backend.common.exception.ImportkException;
import com.zkkj.backend.common.exception.NetworkException;
import com.zkkj.backend.common.exception.NoticeException;
import com.zkkj.backend.common.exception.ServerException;
import com.zkkj.backend.common.exception.ServiceException;
import com.zkkj.backend.common.mail.MailUse;
import com.zkkj.backend.service.biz.exceptionInfo.ExceptionInfoService;
import com.zkkj.backend.service.biz.exceptionInfo.ExceptionInfoServiceImpl;
import com.zkkj.exceptionInfo.service.IExceptionTypeService;
import com.zkkj.exceptionInfo.service.impl.ExceptionTypeServiceImpl;



/**
 * 切面
 * @author liuliyu
 *
 */

@Aspect
public class MyInterceptor {
		private ExceptionInfoService exceptionService=new ExceptionInfoServiceImpl();
		private IExceptionTypeService service=new ExceptionTypeServiceImpl();
//		private IExceptionLevelService exceptionLevelService=new ExceptionLevelServiceImpl();
		
		
		/*@Pointcut("execution(* execution(* com.zkkj.backend.web.controller.biz..*.*(..))") 
		private void anyMethodController(){}//定义一个切入点  
	    @AfterReturning("anyMethod()")  
	    public void doAfter(JoinPoint jPoint){
	    	String name2 = jPoint.getTarget().getClass().getName();
	    	Object[] args = jPoint.getArgs();
	    	String name = jPoint.getSignature().getName();
	        System.out.println("后置通知");
	        System.out.println("用户点击前台功能进入了"+name2+"类,进入"+name+"方法+携带参数"+args[0]);
	    }  */
	    
	    @AfterThrowing(throwing="ex" , pointcut="execution(* com.zkkj.backend..*.*(..))") 
	    public void doAfterThrow(JoinPoint joinPoint,Exception  ex) throws Exception{
	    	Map exceptionMap=new HashMap();
	    	
	    	exceptionMap.put("exceptionCode", "SE_ERROR");
	    	
	    	if(ex instanceof DeviceException){
	    		exceptionMap.put("exceptionCode", ((DeviceException) ex).getExceptionCode());
	    	}
	    	if(ex instanceof AdvertException){
	    		exceptionMap.put("exceptionCode", "SE_AD_ERROR");
	    	}
	    	if(ex instanceof ApkException){
	    		exceptionMap.put("exceptionCode", ((ApkException) ex).getExceptionCode());
	    	}
	    	if(ex instanceof AuthException){
	    		exceptionMap.put("exceptionCode", "SE_AUTH_ERROR");
	    	}
	    	if(ex instanceof DataBaseException){
	    		exceptionMap.put("exceptionCode", ((DataBaseException) ex).getExceptionCode());
	    	}
	    	if(ex instanceof ImportkException){
	    		exceptionMap.put("exceptionCode", "SE_IMPORT_ERROR");
	    	}
	    	if(ex instanceof FileException){
	    		exceptionMap.put("exceptionCode", ((FileException) ex).getExceptionCode());
	    	}
	    	if(ex instanceof NetworkException){
	    		exceptionMap.put("exceptionCode", "SE_CONNECT_ERROR");
	    	}
	    	if(ex instanceof NoticeException){
	    		exceptionMap.put("exceptionCode", ((NoticeException) ex).getExceptionCode());                 
	    	}
	    	if(ex instanceof ServerException){
	    		exceptionMap.put("exceptionCode", ((ServerException) ex).getExceptionCode());
	    	}
	    	if(ex instanceof ServiceException){
	    		exceptionMap.put("exceptionCode", "SE_SERVICE_CALL_ERROR");
	    	}
	    	exceptionMap.put("uuid", null);
	    	StringWriter sw = new StringWriter();
	    	PrintWriter pw = new PrintWriter(sw);
	    	ex.printStackTrace(pw);
	    	String exceptionMessage=sw.toString().replaceAll(" ", "");
//	    	exceptionMessage.substring(0,100);
	    	exceptionMap.put("message",exceptionMessage);
	    	exceptionMap.put("module", "类名:"+joinPoint.getTarget().getClass().getName()+"，方法名:"+joinPoint.getSignature().getName());
	    	exceptionService.addExceptionInfo(exceptionMap);
	    	System.out.println("************异常代码************  发生异常的类名:"+joinPoint.getTarget().getClass().getName()+",发生异常的方法名称:"+joinPoint.getSignature().getName());
	    	
	    	Map exceptionTypeMap=new HashMap();
	    	exceptionTypeMap.put("exceptionCode", exceptionMap.get("exceptionCode"));
	    	List<Map> exceptionType = service.getExceptionInfo(exceptionTypeMap);
	    	Map levelMap=new HashMap();
	    	levelMap.put("id", exceptionType.get(0).get("grade"));
//	    	List<Map> levelMapInfoMap=exceptionLevelService.getExceptionRecordList(levelMap);
//	    	String solution=levelMapInfoMap.get(0).get("solution").toString();
//	    	if(solution.equals("2")){
//	    		MailUse mailUser=new MailUse();
//	    		mailUser.setEmailString(levelMapInfoMap.get(0).get("email").toString());
	    		//mailUser.setSubjectString("贵宾厅智能候机系统异常邮件");
//	    		mailUser.setBodyString(
//	    				"异常代码:"+exceptionMap.get("exceptionCode")+
//	    				"\n异常区域:"+exceptionType.get(0).get("scope").toString()+
//	    				"\n发生异常的类名:"+joinPoint.getTarget().getClass().getName()+
//	    				"\n,发生异常的方法名称:"+joinPoint.getSignature().getName()+
//	    				"\n,异常message:"+exceptionMessage);
//	    		int time=Integer.parseInt(levelMapInfoMap.get(0).get("sendTime").toString());
//	    		time=time*1000*60*60;
//	    		mailUser.setSendTime(time);
//	    		Thread t1 =new Thread(mailUser);
//	    		t1.start();
//	    	}
	    }  
}
