package com.zkkj.backend.service.biz.notice;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.zkkj.backend.common.constant.IConstant;
import com.zkkj.backend.common.socketio.BinaryEventLauncher;
import com.zkkj.backend.common.util.DateUtil;
import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.common.websocket.WebSocketLauncher;
import com.zkkj.backend.entity.biz.NoticeFlight;
import com.zkkj.backend.entity.biz.NoticePerson;
import com.zkkj.backend.service.common.BaseService;

@Service("noticeService")
public class NoticeServiceImpl extends BaseService implements INoticeService {

//	BinaryEventLauncher socketService;
	WebSocketLauncher socketService;
	@Autowired
	FlightNoticeService flightNoticeService;
	@Autowired
	PersonNoticeService personNoticeService;
	/**
	 * 发布航班公告公告
	 */
	@Override
	public Map<String, String> pushFlightNotice(NoticeFlight notice)
			throws Exception {

		Map<String, String> resultMap = new HashMap<String, String>();
		
		if (StringUtils.isBlank(notice.getFlightNo())
				|| StringUtils.isBlank(notice.getFs()) 
				|| StringUtils.isBlank(notice.getDepCity())
				|| StringUtils.isBlank(notice.getArrCity())) {
			resultMap.put("result", IConstant.DEFAULT_CODE_FAIL);
			resultMap.put("message", IConstant.NOTICE_PARAM_EMPTY);
			return resultMap;
		}
		notice.setType( "flight");
		if(StringUtils.isBlank(notice.getCount()))
				notice.setCount("15");	
		if(StringUtils.isBlank(notice.getResult()))
			notice.setResult("successed");
		if(StringUtils.isBlank(notice.getResultCode()))
			notice.setResultCode("200");
		if(StringUtils.isBlank(notice.getErrorCode()))
			notice.setErrorCode("0");
		if(StringUtils.isBlank(notice.getLocation()))
			notice.setLocation("BEIJING");
		if(StringUtils.isBlank(notice.getNoticeId()))
			notice.setNoticeId(String.valueOf(new Date().getTime()));
		
		 //1已发送，2发送成功，3公告已被删除
		String json = GsonUtil.getGson().toJson(notice);
        Map<String,String> result = socketService.sendFlightNotice(json);
        result.put("result", result.get("result"));
        result.put("message", result.get("message"));
        
        notice.setResult(result.get("result"));
        notice.setSendState(result.get("result"));
        notice.setStatus("1");
        notice.setCreateTime(new Date());
 
        flightNoticeService.addFlightNotice(notice);


		return result;
	}
	/**
	 * 发布找人公告
	 */
	@Override
	public Map<String, Object> pushPersionNotice(NoticePerson notice)
			throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(notice.getFlightNo())
				|| StringUtils.isBlank(notice.getName()) 
				|| StringUtils.isBlank(notice.getMsg())) {
			resultMap.put("result", IConstant.DEFAULT_CODE_FAIL);
			resultMap.put("message", IConstant.NOTICE_PARAM_EMPTY);
			return resultMap;
		}
		notice.setType("person");
		notice.setErrorCode(IConstant.DEFAULT_CODE_FAIL);
		notice.setResult("successed");
		notice.setResultCode("200");
		if(StringUtils.isBlank(notice.getNoticeId()))
			notice.setNoticeId(String.valueOf(new Date().getTime()));
		 //1已发送，2发送成功，3公告已被删除
		 //待修改
		// Map<String,String> result = BinaryEventLauncher.sendPersonNotice(null,notice);
		 String json = GsonUtil.getGson().toJson(notice);
	        Map<String,String> result = socketService.sendFlightNotice(json);
		 notice.setSendState(result.get("result"));
		 notice.setStatus(result.get("result"));
		 notice.setCreateTime(new Date());
		 
		personNoticeService.addPersonNotice(notice);
		resultMap.put("result", result.get("result"));
		resultMap.put("message", result.get("message"));

		return resultMap;
	}

	
	@Override
	public List<Map<String, String>> getNoticeFlightList(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		//FlightNoticeService flightNoticeService = (FlightNoticeService) getService("flightNoticeService");
		params.put("createTime",DateUtil.getUserDate(DateUtil.FORMAT_GENERAL1));
		List<Map<String,String>> flightList = flightNoticeService.getFlightNoticeListByDate(params);
		if(flightList!=null&&flightList.size()>0){
			return flightList;
		}
		return Collections.emptyList();
	}
	
	@Override
	public List<Map<String, Object>> getNoticeFlightListByParam(Map<String, Object> notice) throws Exception {
		// TODO Auto-generated method stub
		//FlightNoticeService flightNoticeService = (FlightNoticeService) getService("flightNoticeService");
		List<Map<String,Object>> flightList = flightNoticeService.getAllFlightNoticeList(notice);
		if(flightList!=null&&flightList.size()>0){
			return flightList;
		}
		return Collections.emptyList();
	}
	@Override
	public Integer getNoticeFlightListTotal(Map<String, Object> notice) throws Exception {
		// TODO Auto-generated method stub
		//FlightNoticeService flightNoticeService = (FlightNoticeService) getService("flightNoticeService");
		return  flightNoticeService.getNoticeFlightListTotal(notice);
	
	}
	
	
	@Override
	public Integer getNoticeFlightListByDateTotal(Map<String, Object> notice)
			throws Exception {
		// TODO Auto-generated method stub
		notice.put("createTime",DateUtil.getUserDate(DateUtil.FORMAT_GENERAL1));
		return  flightNoticeService.getNoticeFlightListByDateTotal(notice);
	}
	
	@Override
	public List<Map<String, Object>> getNoticePersionList(Map<String, Object> person) throws Exception {
		// TODO Auto-generated method stub
		//PersonNoticeService personNoticeService = (PersonNoticeService) getService("personNoticeService");
		List<Map<String, Object>> personList = personNoticeService.getAllPersonNoticeList(person);
		if(personList!=null&&personList.size()>0){
			return personList;
		}
		return  Collections.emptyList();
	}
	
	@Override
	public Map<String, String> getOneFlightPlan(String flightNo)
			throws Exception {
		return flightNoticeService.getSimpleFlightPlan(flightNo);
	}
	
	@Override
	public List<Map<String, Object>> getAllFlightPlanList() throws Exception {
		// TODO Auto-generated method stub
		//FlightNoticeService flightNoticeService = (FlightNoticeService) getService("flightNoticeService");
		return flightNoticeService.getAllFlightPlanList();
	}
	

	@Override
	public Integer getNoticePersonListByTotal(Map<String, Object> notice){	
		
		return personNoticeService.getNoticePersonListByTotal(notice);
	}
	
	
}
