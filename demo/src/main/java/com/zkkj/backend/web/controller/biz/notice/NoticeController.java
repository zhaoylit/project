package  com.zkkj.backend.web.controller.biz.notice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zkkj.backend.common.constant.IConstant;
import com.zkkj.backend.common.exception.DataBaseException;
import com.zkkj.backend.common.exception.NoticeException;
import com.zkkj.backend.common.util.DateUtil;
import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.common.util.ReflectUtil;
import com.zkkj.backend.entity.biz.NoticeFlight;
import com.zkkj.backend.entity.biz.NoticePerson;
import com.zkkj.backend.entity.biz.ZkkjUser;
import com.zkkj.backend.service.base.authentication.Subject;
import com.zkkj.backend.service.biz.notice.INoticeService;
import com.zkkj.backend.service.biz.user.IUserService;
import com.zkkj.backend.web.controller.BaseController;
import com.zkkj.platform.base.common.ParamsUtil;

@Controller
@RequestMapping("notice")
public class NoticeController extends BaseController{

    private static final Logger log = LoggerFactory.getLogger(NoticeController.class);
	@Autowired
	private INoticeService noticeService;

	/*@Autowired
	private IUserService userService;*/
	/**
	 * 发布航班公告
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/pushFlightNotice")
	public void pushFlightNotice(HttpServletRequest request,NoticeFlight flightnotice,HttpServletResponse response) throws Exception{
		Map<String,String> resultMap = new HashMap<String,String>();	
		try {
			Map<String,Object> params = ReflectUtil.transToMAP(request.getParameterMap());
			
			flightnotice.setLocation((String)request.getSession().getAttribute("USER_LOCATION"));
			flightnotice.setOperator((String)request.getSession().getAttribute(Subject.AUTH_USERNAME));
			resultMap = noticeService.pushFlightNotice(flightnotice);			
		} catch (Exception e) {
			resultMap.put("result", IConstant.DEFAULT_CODE_FAIL);
			resultMap.put("message",IConstant.NOTICE_SEND_FAIL);
			log.error(e.getMessage());
			
			
			NoticeException noticeException=new NoticeException();
			noticeException.setExceptionCode("SE_NOTICE_PUSH_ERROR");
			noticeException.setStackTrace(e.getStackTrace());
			throw noticeException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 发布找人公告
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/pushPersonNotice")
	public void pushPersonNotice(HttpServletRequest request,NoticePerson persionNotice,HttpServletResponse response) throws Exception{
		Map<String,Object> resultMap = new HashMap<String,Object>();	
		try {
			Map<String,Object> params = ReflectUtil.transToMAP(request.getParameterMap());
			persionNotice.setOperator((String)request.getSession().getAttribute(Subject.AUTH_USERNAME));
			resultMap = noticeService.pushPersionNotice(persionNotice);			
		} catch (Exception e) {
			resultMap.put("result", IConstant.DEFAULT_CODE_FAIL);
			resultMap.put("message",IConstant.NOTICE_SEND_FAIL);
			log.error(e.getMessage());
			
			NoticeException noticeException=new NoticeException();
			noticeException.setExceptionCode("SE_NOTICE_PUSH_ERROR");
			noticeException.setStackTrace(e.getStackTrace());
			throw noticeException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	
	@RequestMapping("getNoticeFlightByParam")
	@ResponseBody
	public void getNoticeFlightByParam(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> map = ReflectUtil.transToMAP(request.getParameterMap());
		String org="";
		Integer page = Integer.parseInt(ParamsUtil.nullDeal(map, "page", "1"));
        Integer rows = Integer.parseInt(ParamsUtil.nullDeal(map, "rows", "10"));
		
		map.put("pages", (page-1)*rows);
		map.put("rows", rows);
		
		if(request.getSession().getAttribute("user")!=null){
			ZkkjUser userMap = (ZkkjUser) request.getSession().getAttribute("user");
			if(userMap!=null){
				org = userMap.getOrg()!=null?userMap.getOrg():"";
				map.put("orgId", org);
			}
		//	List<Map<String, String>> operatorlist = userService.getUserList(map);
			try {
				List<Map<String, Object>> flightList = null;
				List<String> accountList = new ArrayList<String>();
	/*			for (Map<String, String> operator : operatorlist) {
					if(StringUtils.isNotBlank(operator.get("account")))
						accountList.add(operator.get("account"));
				}
				map.put("operatorlist", accountList);*/
				Integer total = noticeService.getNoticeFlightListTotal(map);
				if(total!=null&&total>0)
					flightList = noticeService.getNoticeFlightListByParam(map);
			
				resultMap.put("result", IConstant.DEFAULT_CODE_SUCCESS);
				
				resultMap.put("total", total);
				resultMap.put("rows", flightList);
				resultMap.put("message", IConstant.DEFAULT_MESSAGE_SUCCESS);
			} catch (Exception e) {
				// TODO: handle exception
				resultMap.put("result", IConstant.DEFAULT_CODE_FAIL);
				resultMap.put("message", IConstant.DEFAULT_MESSAGE_FAIL);
				log.error(e.getMessage());
				DataBaseException dataBaseException=new DataBaseException();
				dataBaseException.setExceptionCode("SE_DB_ERROR");
				dataBaseException.setStackTrace(e.getStackTrace());
				throw dataBaseException;
			} finally {
				response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
			}
		}
	
	}
	
	@RequestMapping("getNoticeFlight")
	public void getNoticeFlightList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> map = ReflectUtil.transToMAP(request.getParameterMap());
		List<Map<String, String>> flightList = null;
		try {
				Integer page = Integer.parseInt(ParamsUtil.nullDeal(map, "page", "1"));
		        Integer rows = Integer.parseInt(ParamsUtil.nullDeal(map, "rows", "10"));
				
				map.put("pages", (page-1)*rows);
				map.put("rows", rows);
				ZkkjUser user = (ZkkjUser) request.getSession().getAttribute("user");
				map.put("operator", user.getAccount());
			    Integer total = noticeService.getNoticeFlightListByDateTotal(map);
				flightList = noticeService.getNoticeFlightList(map);
	            //判断是否过时
				String currentTime = DateUtil.DateToFormatStr(new Date(),DateUtil.FORMAT_FLIGHT_PLAN);
				if(flightList!=null && flightList.size()>0){
					for(Map<String,String> item:flightList){
						String takeoffTime = item.get("takeoffTime");
						if(takeoffTime!=null&&currentTime.compareTo(takeoffTime)>0){
							item.put("status", "0");
						}else{
							item.put("status", "1");
						}								
					}
				}
				
				resultMap.put("result", IConstant.DEFAULT_CODE_SUCCESS);
				resultMap.put("total", total);
				resultMap.put("rows", flightList);
				resultMap.put("message", IConstant.DEFAULT_MESSAGE_SUCCESS);
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("result", IConstant.DEFAULT_CODE_FAIL);
			resultMap.put("message", IConstant.NOTICE_SEND_FAIL);
			log.error(e.getMessage());
			
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		} finally {
			AjaxJsonWrite(resultMap, response);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("getNotePerson")
	public void getNoticePersonList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> map = ReflectUtil.transToMAP(request.getParameterMap());
		String org="";
		if(request.getSession().getAttribute("user")!=null){
			ZkkjUser userMap = (ZkkjUser) request.getSession().getAttribute("user");
			if(userMap!=null){
				org = userMap.getOrg()!=null?userMap.getOrg():"";
				map.put("orgId", org);
			}
		//	List<Map<String, String>> operatorlist = userService.getUserList(map);
			try {
				List<Map<String, Object>> personList = null;
/*				List<String> accountList = new ArrayList<String>();
				for (Map<String, String> operator : operatorlist) {
					accountList.add(operator.get("account"));
				}
		    map.put("operatorlist", accountList);
		   */
			Integer page = Integer.parseInt(ParamsUtil.nullDeal(map, "page", "1"));
	        Integer rows = Integer.parseInt(ParamsUtil.nullDeal(map, "rows", "10"));
			
			map.put("pages", (page-1)*rows);
			map.put("rows", rows);
			ZkkjUser user = (ZkkjUser) request.getSession().getAttribute("user");
			map.put("operator", user.getAccount());
			
		    Integer total = noticeService.getNoticePersonListByTotal(map);
			personList = noticeService.getNoticePersionList(map);
			
			resultMap.put("result", IConstant.DEFAULT_CODE_SUCCESS);
			resultMap.put("total", total);
			resultMap.put("rows", personList);
			resultMap.put("message", IConstant.DEFAULT_MESSAGE_SUCCESS);
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("result", IConstant.DEFAULT_CODE_FAIL);
			resultMap.put("message", IConstant.NOTICE_SEND_FAIL);
			log.error(e.getMessage());
			
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		} finally {
			AjaxJsonWrite(resultMap, response);
		}
	}
	}
	
	
	@RequestMapping(value="getFlightInfo",method=RequestMethod.POST)
	@ResponseBody
	public void getFlightInfoList(HttpServletRequest request,HttpServletResponse response) throws Exception{
	   List<Map<String, Object>> flightList = null;
	   Map map = ReflectUtil.transToMAP(request.getParameterMap());
		try {
			 flightList = noticeService.getAllFlightPlanList();
		} catch (Exception e) {
			e.printStackTrace();

			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		} finally {
			response.getOutputStream().write(GsonUtil.getGson().toJson(flightList).getBytes());
			
		}	    
	}

	@RequestMapping(value="getOneFlightPlan",method=RequestMethod.POST)
	@ResponseBody
	public void getOneFlightPlan(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, String> resultMap = null;
		try {
			Map<String, Object> map = ReflectUtil.transToMAP(request.getParameterMap());
			String flightNo = ParamsUtil.nullDeal(map, "flightNo", "");
			if(StringUtils.isNotBlank(flightNo))
				    resultMap = noticeService.getOneFlightPlan(flightNo);	
				else
					resultMap = Collections.emptyMap();	
		}catch(Exception e){
			log.error(e.getMessage());	
			
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally {
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes());
		}	
		
	}
	/**
	 * 初始化航班号下拉框
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="getFlightPlan",method=RequestMethod.POST)
	@ResponseBody
	public void getFlightPlan(HttpServletRequest request,HttpServletResponse response) throws Exception{
	    List<Map<String, Object>> flightPlanList = null;
		try {
			Map<String, Object> map = ReflectUtil.transToMAP(request.getParameterMap());
			String more = ParamsUtil.nullDeal(map, "more", "N");
			flightPlanList = noticeService.getAllFlightPlanList();
			String currentTime = DateUtil.DateToFormatStr(new Date(),DateUtil.FORMAT_FLIGHT_PLAN);
			
			Collections.sort(flightPlanList, new Comparator<Map<String,Object>>(){  
	            public int compare(Map<String,Object> o1,Map<String,Object> o2){
	                return String.valueOf(o1.get("takeoffTime")).compareTo(String.valueOf(o2.get("takeoffTime")));
	            }  
	        });
			if("N".equals(more)&&!flightPlanList.isEmpty()){
				List<Map<String,Object>> lessList = new ArrayList<Map<String,Object>>(10);
				int count=0;
				int size = flightPlanList.size();
				for(int i=0;i<size;i++){
					Map<String,Object> item = flightPlanList.get(i);
					String takeoffTime = String.valueOf(item.get("takeoffTime"));
					if(currentTime.compareTo(takeoffTime)<=0){
						count=i;
						break;
					}
				}
				if( count >=4 ){				
					for(int i=count-5,c=0;c<10 && i<size;c++,i++){
						lessList.add(flightPlanList.get(i));
					}
				}else if(count<4 ){
					for(int i=0;i<size;i++){
						if(i>=10)
							break;
						lessList.add(flightPlanList.get(i));					
					}
					
					
				}
				Map<String,Object> moreMap = new HashMap<String,Object>(1);
				moreMap.put("flightNo", "显示更多");
				if(lessList!=null&&!lessList.isEmpty())
					lessList.add(moreMap);
				flightPlanList = lessList;
			}else{
				Map<String,Object> lastMap = new HashMap<String,Object>(1);
				lastMap.put("flightNo", "最近十条");
				if(flightPlanList!=null&&!flightPlanList.isEmpty())
					flightPlanList.add(lastMap);		
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		} finally {
			response.getOutputStream().write(GsonUtil.getGson().toJson(flightPlanList).getBytes());
		}	    
	}

}
