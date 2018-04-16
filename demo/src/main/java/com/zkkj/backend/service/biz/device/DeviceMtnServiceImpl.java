package com.zkkj.backend.service.biz.device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.util.StringUtils;
import com.zkkj.backend.service.common.BaseService;
import com.zkkj.platform.device.DeviceMaintainService;


@Service
public class DeviceMtnServiceImpl extends BaseService implements IDeviceMtnService {
	
	@Override
	public List<Map<String, String>> getDeviceMtnList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		DeviceMaintainService deviceMaintainService = (DeviceMaintainService) getService("deviceMaintainService");
		List<Map<String, String>> deviceMtnList = deviceMaintainService.getDeviceMaintainList(map);
		if(deviceMtnList!=null&&deviceMtnList.size()>0){
			return deviceMtnList;
		}
		return null;
	}

	@Override
	public Map<String, Object> addOrEditDeviceMtn(Map<String, Object> params) throws Exception {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		DeviceMaintainService deviceMaintainService = (DeviceMaintainService) getService("deviceMaintainService");
		if(StringUtils.isBlank(String.valueOf(params.get("rowKey")))){
			deviceMaintainService.addDeviceMaintain(params);
			resultMap.put("result", "1");
			resultMap.put("message", "添加设备成功");
		}else {
			deviceMaintainService.updateDeviceMaintain(params);
			resultMap.put("result", "1");
			resultMap.put("message", "编辑设备成功");
		}
		return resultMap;
	}
	@Override
	public Map<String,Object> deleteDeviceMtn(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		DeviceMaintainService deviceMaintainService = (DeviceMaintainService) getService("deviceMaintainService");
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String ids  = ParamsUtil.nullDeal(params, "ids", "");
		String array[] = ids.split(",");
		if(StringUtils.isNotBlank(ids)){
			for(String str : array){
				deviceMaintainService.deleteDeviceMaintain(str);
			}
		}
		resultMap.put("result", "1");
		resultMap.put("message", "操作成功");
		return resultMap;
	}

	@Override
	public Map<String, String> getOneRow(String rowKey) throws Exception {
		// TODO Auto-generated method stub
		DeviceMaintainService deviceMaintainService = (DeviceMaintainService) getService("deviceMaintainService");
		return deviceMaintainService.getOneRow(rowKey);
	}
}
