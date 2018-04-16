package com.zkkj.backend.service.biz.exceptionInfo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.exceptionInfo.service.IExceptionRecordService;
import com.zkkj.exceptionInfo.service.impl.ExceptionRecordServiceImpl;
import com.zkkj.program.service.IDeviceService;
import com.zkkj.program.service.impl.DeviceServiceImpl;

@Service
public class ExceptionInfoServiceImpl implements ExceptionInfoService{
	
	private IExceptionRecordService exceptionRecordService = new ExceptionRecordServiceImpl();
	private IDeviceService deviceService= new DeviceServiceImpl();

	@Override
	public int addExceptionInfo(Map params) {
		int count=0;
		Map exceptionRecordMap=new HashMap();
		String uuid = ParamsUtil.nullDeal(params, "uuid", "");
		
		if(uuid.equals("")){
			try {
				exceptionRecordMap.put("exceptionCode", params.get("exceptionCode"));
		    	exceptionRecordMap.put("message", params.get("message"));
		    	exceptionRecordMap.put("module", params.get("module"));
		    	exceptionRecordMap.put("airlineCode","");
		    	exceptionRecordMap.put("airlineName","");
		    	exceptionRecordMap.put("airportCode","");	
		    	exceptionRecordMap.put("airportName","");
		    	exceptionRecordMap.put("viproomId","");
		    	exceptionRecordMap.put("viproom","");
		    	exceptionRecordMap.put("devIp","");
		    	exceptionRecordMap.put("deviceId","");
				count=exceptionRecordService.addOrEditExceptionRecord(exceptionRecordMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return count;
		}
		Map deviceParameterMap=new HashMap();
    	deviceParameterMap.put("uuid", uuid);
    	//根据传递设备ID查询设备信息
    	Map deviceMap;
		try {
			deviceMap = deviceService.getDeviceOne(deviceParameterMap);
	    	
	    	exceptionRecordMap.put("exceptionCode", params.get("exceptionCode"));
	    	exceptionRecordMap.put("message", params.get("message"));
	    	exceptionRecordMap.put("module", params.get("module"));
	    	exceptionRecordMap.put("airlineCode",deviceMap.get("airlineCode"));
	    	exceptionRecordMap.put("airlineName",deviceMap.get("airlineName"));
	    	exceptionRecordMap.put("airportCode",deviceMap.get("airportCode"));	
	    	exceptionRecordMap.put("airportName",deviceMap.get("airportName"));
	    	exceptionRecordMap.put("viproomId",deviceMap.get("viproomId"));
	    	exceptionRecordMap.put("viproom",deviceMap.get("viproomName"));
	    	exceptionRecordMap.put("devIp",deviceMap.get("deviceIp"));
	    	exceptionRecordMap.put("deviceId",deviceMap.get("uuid"));
	    	//添加
			count = exceptionRecordService.addOrEditExceptionRecord(exceptionRecordMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
}
