package com.zkkj.backend.web.controller.biz.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zkkj.backend.common.exception.DataBaseException;
import com.zkkj.backend.common.exception.DeviceException;
import com.zkkj.backend.common.socketio.BinaryEventLauncher;
import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.common.util.MD5Util;
import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.util.ReflectUtil;
import com.zkkj.backend.dao.mybatis.mapper.SysResourceInfoMapper;
import com.zkkj.backend.dao.mybatis.mapper.UserBarcodeMapper;
import com.zkkj.backend.entity.biz.ZkkjUser;
import com.zkkj.backend.service.base.authentication.AuthenticationService;
import com.zkkj.backend.service.base.authentication.Subject;
import com.zkkj.backend.service.base.authentication.WebAuthenticationServiceImpl;
import com.zkkj.backend.service.biz.user.IUserGroupService;
import com.zkkj.backend.service.biz.user.IUserService;
import com.zkkj.program.service.IDeviceService;
import com.zkkj.program.service.impl.DeviceServiceImpl;
import com.zkkj.userManager.service.IUserManagerService;
import com.zkkj.userManager.service.impl.UserManagerServiceImpl;
import com.zkkj.userManager.util.Page;

/**
 *
 */
@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private IUserService userService;
	@Autowired
	private IUserGroupService userGroupService;
	//记录用户错误登陆次数
	static Map<String,Integer> loginCount=new ConcurrentHashMap<String, Integer>();
	
	IUserManagerService userManagerService=new UserManagerServiceImpl();
	IDeviceService deviceService = new DeviceServiceImpl();
	
	public static String REDIECT_URL ="/menu/index.do";

	@Autowired
	private AuthenticationService authenticationService;
//	@Autowired
	BinaryEventLauncher launacher;

	/*
	 * @Autowired private DeviceService deviceService;
	 */
	/**
	 * 用户管理列表页面初始化
	 */
	@RequestMapping("userListInit")
	public ModelAndView userListInit(ModelAndView model,
			HttpServletRequest request, HttpServletResponse response) {

		model.setViewName("user/userList");
		return model;
	}
	
	@RequestMapping("loginPage")
	public ModelAndView loginPage(ModelAndView model,
			HttpServletRequest request, HttpServletResponse response) {

		model.setViewName("login");
		return model;
	}
	/**
	 * 查询用户列表json
	 * @throws DataBaseException 
	 */
	@RequestMapping("userList")
	@ResponseBody
	public void userList(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException, DataBaseException {
		Map resultMap = new HashMap();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			int  pageNo = Integer.parseInt(ParamsUtil.nullDeal(params, "page", "1"));
			int  pageSize = Integer.parseInt(ParamsUtil.nullDeal(params, "rows", "10"));
			Page page = new Page();
			page.setPageNum(pageNo);
			page.setNumPerPage(pageSize);
			
			String orgId = "",airport= "";
			/*if(request.getSession().getAttribute("user") != null){
				Map userMap = (Map) request.getSession().getAttribute("user");
				if(userMap != null){	
					orgId = userMap.get("org") != null ? String.valueOf(userMap.get("org")) : "";
					airport = userMap.get("airport") != null ? String.valueOf(userMap.get("airport")) : "";
				}
			}*/
			params.put("org", orgId);
			params.put("airport", airport);
			List<Map> resultList = userManagerService.getUserManagerList(params, page);
			resultMap.put("rows", resultList != null && resultList.size() > 0 ? resultList : Collections.EMPTY_LIST);
			resultMap.put("total", userManagerService.getUserManagerListCount(params));
		} catch (Exception e) {
			resultMap.put("result", "0");
			resultMap.put("message", e.getMessage());
			System.out.println(e.getMessage());
			//发生异常存储到异常表
			DataBaseException dataBaseException=new DataBaseException();	
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		} finally {
			response.getOutputStream().write(
					GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}

	/**
	 * 添加或者修改用户信息初始化
	 */
	@RequestMapping("addOrEditInit")
	public ModelAndView addOrEditInit(ModelAndView model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String id=ParamsUtil.nullDeal(params, "id", "");
		Map userMap=new HashMap();
		userMap.put("id", id);
		if (!"".equals(id)) {
			// 查询单条用户的数据 
			Map Map = userManagerService.getUserManagerById(userMap);
			model.addObject("resultInfo", Map);
		}else{
			model.addObject("panduan", true);
		}
		model.setViewName("user/userEdit");
		return model;
	}
	/**
	 * 添加或修改用户
	 * @throws DataBaseException 
	 */
	@RequestMapping("addOrEditUser")
	@ResponseBody
	public void addOrEditUser(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException, DataBaseException {
		Map resultMap=new HashMap();
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		params.put("airport",ParamsUtil.nullDeal(params, "airportCode", ""));
		String[] airportCodes=request.getParameterValues("airportCode");
		if(airportCodes!=null&&airportCodes.length>1){
			params.put("airportCode", StringUtils.join(airportCodes,","));
		}
		try {
			int count=userManagerService.addOrEditUserManager(params);
			resultMap.put("result", count > 0 ? 1 : 0);
			//resultMap = userService.addOrEditUser(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result", "0");
			resultMap.put("message", e.getMessage());
			//发生异常存储到异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		} finally {
			response.getOutputStream().write(
					GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}

	/**
	 * 删除用户
	 * @throws DataBaseException 
	 */
	@RequestMapping("deleteUser")
	@ResponseBody
	public void deleteUser(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException, DataBaseException {
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String ids = ParamsUtil.nullDeal(params, "ids", "");
		Map resultMap = new HashMap();
		try {
			if(!"".equals(ids)){
				List<String> list = new ArrayList<String>();
				String []array = ids.split(",");
				for(String ss : array){
					list.add(ss);
				}
				int count = userManagerService.deleteUserManager(list);
				resultMap.put("result",count > 0 ? 1 : 0);
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
			//发生异常存储到异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}

	/**
	 * 账户冻结
	 */
	@RequestMapping("updateUserFrozen")
	public void updateUserFrozen(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String ids = ParamsUtil.nullDeal(params, "ids", "");
		int count=0;
		
		Map resultMap = new HashMap();
		try {
			if(ids!=null&&!ids.equals("")){
				String[] idsStrings=ids.split(",");
				for (int i = 0; i < idsStrings.length; i++) {	
					Map idsMap=new HashMap();
					idsMap.put("id", idsStrings[i]);
					idsMap.put("userStatus", "2");
					userManagerService.addOrEditUserManager(idsMap);
					count++;
				}
			}
			resultMap.put("result", count>0?1 : 0);
			//resultMap = userService.updateUserFrozen(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result", "0");
			resultMap.put("message", e.getMessage());
			//发生异常存储到异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
		} finally {
			response.getOutputStream().write(
					GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}

	/**
	 * 账户解冻
	 */
	@RequestMapping("updateAccountThaw")
	@ResponseBody
	public void updateAccountThaw(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String ids = ParamsUtil.nullDeal(params, "ids", "");
		int count=0;
		
		Map resultMap = new HashMap();
		try {
			if(ids!=null&&!ids.equals("")){
				String[] idsStrings=ids.split(",");
				for (int i = 0; i < idsStrings.length; i++) {	
					Map idsMap=new HashMap();
					idsMap.put("id", idsStrings[i]);
					idsMap.put("userStatus", "1");
					userManagerService.addOrEditUserManager(idsMap);
					count++;
				}
			}
			resultMap.put("result", count>0?1 : 0);
			//resultMap = userService.updateUserFrozen(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result", "0");
			resultMap.put("message", e.getMessage());
			//发生异常存储到异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
		} finally {
			response.getOutputStream().write(
					GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}

	/**
	 * 修改密码初始化
	 */
	@RequestMapping("updatepwdInit")
	public ModelAndView updatepwdInit(ModelAndView model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String id = ParamsUtil.nullDeal(params, "id", "");
		model.addObject("resultInfo",id);
		model.setViewName("user/userpassword");
		return model;
	}

	/**
	 * 修改密码
	 */
	@RequestMapping("updatepwd")
	public void updatepwd(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		try {
			Map userMap=new HashMap();
			userMap.put("id", ParamsUtil.nullDeal(params, "id", ""));
			userMap.put("passWord", MD5Util.string2MD5(ParamsUtil.nullDeal(params, "password", "")));
		    int count=userManagerService.addOrEditUserManager(userMap);
		    resultMap.put("result", count>0?1:0);
			//resultMap = userService.updatapwd(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result", "0");
			resultMap.put("message", e.getMessage());
			//发生异常存储到异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
		} finally {
			response.getOutputStream().write(
					GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}

	@RequestMapping("gerAreaJson")
	@ResponseBody
	public void getAreaJson(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
		List<Map> resultList = new ArrayList<Map>();
		try {
			//
			Map mm = new HashMap();
			mm.put("areaId", "-1");
			mm.put("areaName", "全部");
			resultList.add(mm);
			Map mm1 = new HashMap();
			mm.put("areaId", "1");
			mm.put("areaName", "北京市");
			resultList.add(mm1);
			Map mm2 = new HashMap();
			mm.put("areaId", "2");
			mm.put("areaName", "上海市");
			resultList.add(mm2);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result", "0");
			resultMap.put("message", e.getMessage());
			//发生异常存储到异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
		} finally {
			response.getOutputStream().write(
					GsonUtil.getGson().toJson(resultList).getBytes("UTF-8"));
		}
	}

	/**
	 * 用户登录
	 */
	@RequestMapping("login")
	@ResponseBody
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map resultMap = new HashMap();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			
			String account = ParamsUtil.nullDeal(params, "account", "");
			String password = MD5Util.string2MD5(ParamsUtil.nullDeal(params, "password", ""));
			
			//List<Map<String, String>> resultList = userService.loginOne(params);
			//验证用户并查找改用户拥有的权限
			List<ZkkjUser>  resultList = ((WebAuthenticationServiceImpl) authenticationService)
					.login(account,password, request);
			System.out.println(resultList);
			if(resultList==null||resultList.equals("")||resultList.size()==0){//返回空登陆失败
				resultMap.put("msg", "cuowu");
				if(loginCount.size()<=0){
					loginCount.put(account, 0);
				}
				//记录用户登录次数，超过次数冻结账号
				for (Map.Entry<String,Integer> entry: loginCount.entrySet()
			             ) {
			            String key = entry.getKey();
			            Integer value=entry.getValue();
			            
			            if(account.equals(key)){//账户和上次相同count+1
			            	loginCount.put(key, value+1);
			            	
						}else{//账户不相同清除map的值键，重新生成当前name和key
							loginCount.clear();
							loginCount.put(account, 1);
						}
			        }
				//List<Map<String, String>> userMap = userService.UserFrozen(params);
				
				Map userMap=new HashMap();
				userMap.put("account", account);
				//查询一条用户
				List<Map> getUserMap = userManagerService.getUserManagerList(userMap);
				Integer count=loginCount.get(account);
				
				if(getUserMap.get(0).get("userStatus").equals("2")){
					resultMap.put("msg", "freeze");
				}
				else if(count==1){
					resultMap.put("count", "1");
				}
				else if(count==2){
					resultMap.put("count", "2");
				}
				else if(count==3){
					resultMap.put("count", "3");
				}
				else if(count==4){
					resultMap.put("count", "4");
				}
				else if(count==5){
					resultMap.put("count", "5");
				}
				else if(count==6){
					resultMap.put("count", "6");
				}
				else if(count==7){
					resultMap.put("count", "7");
				}
				else if(count==8){
					resultMap.put("count", "8");
				}
				else if(count==9){
					resultMap.put("count", "9");
				}
				else if(count==10){
					//Map map1=new HashMap();
					//resultMap = userService.UserFrozen(userMap.get(0).get("rowKey"));
					Map UserFrozen=new HashMap();
					UserFrozen.put("id", getUserMap.get(0).get("id"));
					UserFrozen.put("userStatus", "2");
					userManagerService.addOrEditUserManager(UserFrozen);
					loginCount.put(account,0);
					resultMap.put("count", "10");
				}
				
			}else {
				ZkkjUser user= resultList.get(0);
				if(user.getUserStatus().equals("1")){//用户状态正常返回成功
					resultMap.put("msg", "success");
					request.getSession().setAttribute("user",user);
					request.getSession().setAttribute("userDeviceId", user.getUserDeviceId());
					
					request.getSession().setAttribute("username", user.getAccount());
					request.getSession().setAttribute("passWord", user.getPassWord());
					request.getSession().setAttribute("id", user.getId());
					//启动socket监听
					/*try{
						if(launacher.getServer() == null){
							new Thread(new Runnable() {
								@Override
								public void run() {
									try {
										launacher.startServer();
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}).start();
						}
					}catch(Exception e){
					}*/
				}else if(user.getUserStatus().equals("2")){//用户状态冻结返回冻结
					resultMap.put("msg", "freeze");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("result", "0");
			resultMap.put("message", e.getMessage());
			System.out.println(e.getMessage());
			
			//发生异常存储到异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
		} finally {
			response.getOutputStream().write(
					GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}

	/**
	 * 用户注销
	 */
	@RequestMapping("cancellation")
	public String cancellation(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		loginCount.clear();
		session.removeAttribute("username");
		session.removeAttribute(Subject.AUTH_USERNAME);
		session.removeAttribute(Subject.AUTH_SUBJECT);
		return "redirect:../pages/main/login.jsp";
	}
	

	/**
	 * 本地用户修改密码
	 */
	@RequestMapping("updatePWD")
	public void updatePWD(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		Map resultMap = new HashMap();
		
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String yuanpwd=ParamsUtil.nullDeal(params, "yuanpwd", "");
		String passwordtwo=ParamsUtil.nullDeal(params, "passwordtwo", "");
		if(passwordtwo.equals("")||passwordtwo==null){
			resultMap.put("result", "3");
		}
		Map mapPWD=new HashMap();
		mapPWD.put("passWord", MD5Util.string2MD5(passwordtwo));
		String PWD=MD5Util.string2MD5(yuanpwd);
		//String rowKey=(String) request.getSession().getAttribute("rowKey");
		Map userMap=(Map) request.getSession().getAttribute("user");
		//mapPWD.put("rowKey", rowKey);
		mapPWD.put("id", userMap.get("id"));
		
		try {
			if(PWD.equals(userMap.get("passWord"))){
				
				userManagerService.addOrEditUserManager(mapPWD);
				//userService.updatapwd(mapPWD);
				resultMap.put("result", "1");
			}else{
				resultMap.put("result", "2");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result", "0");
			resultMap.put("message", e.getMessage());
			//发生异常存储到异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
		} finally {
			response.getOutputStream().write(
					GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	
	/**
	 * 用户赋予设备页面初始化
	 */
	@RequestMapping("userDeviceInit")
	public ModelAndView userDeviceInit(ModelAndView model,
			HttpServletRequest request, HttpServletResponse response) {
		model.setViewName("user/userAssociatedDevice");
		return model;
	}
	
	
	/**
	 * 查询设备列表树形json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws DeviceException 
	 */
	@RequestMapping(value="getDeviceTreeJson")
	@ResponseBody
	public void getViproomTreeJson(HttpServletRequest request,HttpServletResponse response) throws IOException, DeviceException{
		List<Map> resultList = new ArrayList<Map>();	
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			
			ZkkjUser user= (ZkkjUser) request.getSession().getAttribute("user");
			params.put("airlineId", user.getOrg());
			
			List<Map> list = deviceService.getDeviceDataJson(params);
			resultList = list != null && list.size() > 0 ? list : Collections.EMPTY_LIST;
		} catch (Exception e) {
			// TODO: handle exception
			resultList = Collections.EMPTY_LIST;
			System.out.println(e.getMessage());
			
			DeviceException deviceException=new DeviceException();
			deviceException.setExceptionCode("SE_DEV_ERROR");
			deviceException.setStackTrace(e.getStackTrace());
			throw deviceException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultList).getBytes("UTF-8"));
		}
	}
	
	/**
	 * 给用户添加设备
	 * @throws DataBaseException 
	 */
	@RequestMapping("addUserDevice")
	@ResponseBody
	public void addUserDevice(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException, DataBaseException {

		Map resultMap=new HashMap();
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String deviceId=ParamsUtil.nullDeal(params, "deviceId", "");
		String userId=ParamsUtil.nullDeal(params, "userId", "");
		
		try {
			Map userDeviceMap=new HashMap();
			userDeviceMap.put("deviceId", deviceId);
			userDeviceMap.put("id", userId);
			int count=userManagerService.addOrEditUserManager(userDeviceMap);
			resultMap.put("result", count > 0 ? 1 : 0);
			} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", "0");
			resultMap.put("message", e.getMessage());
			//发生异常存储到异常表
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		} finally {
			response.getOutputStream().write(
					GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
}
