package com.zkkj.backend.service.biz.advertiser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.util.StringUtils;
import com.zkkj.backend.service.common.BaseService;
import com.zkkj.platform.advertiser.AdvertiserManageService;
import com.zkkj.platform.base.common.DateUtil;

@Service("advertiserService")
public class AdvertiserServiceImpl extends BaseService implements IAdvertiserService {

	@Override
	public List<Map<String, String>> getAdvertiserList(Map params) throws Exception {
		// TODO Auto-generated method stub
		AdvertiserManageService advertiserManageService = (AdvertiserManageService)getService("advertiserManageService");
		List<Map<String, String>> resultList = advertiserManageService.getAdvertiser(params);
		return resultList;
	}

	@Override
	public long getAdvertiserListCount(Map params) throws Exception {
		// TODO Auto-generated method stub
		AdvertiserManageService advertiserManageService = (AdvertiserManageService)getService("advertiserManageService");
		long rowsCount = advertiserManageService.getAdvertiserCount(params);
		return rowsCount;
	}

	@Override
	public Map addOrEditAdvertiser(Map params) throws Exception { 
		// TODO Auto-generated method stub]
		String rowKey = ParamsUtil.nullDeal(params, "rowKey", "");
		if("".equals(rowKey)){
			StringBuilder rowKeyBuilder = new StringBuilder();
			rowKeyBuilder.append(String.valueOf((Long.MAX_VALUE - new Date().getTime())));
			params.put("rowKey", rowKeyBuilder.toString());		
			//设置添加时间
			params.put("addedTime",DateUtil.getStringDate());
		}else{
			//设置添加时间
			params.put("updateTime",DateUtil.getStringDate());
		}	
		AdvertiserManageService advertiserManageService = (AdvertiserManageService)getService("advertiserManageService");
		Map resultMap = advertiserManageService.addOrUpdateAdvertiser(params);
		return resultMap;
	}

	@Override
	public Map deleteAdvertiser(Map params) throws Exception {
		// TODO Auto-generated method stub
		AdvertiserManageService advertiserManageService = (AdvertiserManageService)getService("advertiserManageService");
		Map resultMap = new HashMap();
		String ids  = ParamsUtil.nullDeal(params, "ids", "");
		String array[] = ids.split(",");
		if(StringUtils.isNotBlank(ids)){
			for(String str : array){
				advertiserManageService.deleteAdvertiser(str);
			}
		}
		resultMap.put("result", "1");
		resultMap.put("message", "操作成功");
		return resultMap;
	}
	
	@Override
	public List<Map> getAdvertiserTypeList(Map params) throws Exception {
		// TODO Auto-generated method stub
		List<Map> resultList = new ArrayList<Map>();
		Map map1 = new HashMap();
		map1.put("advertiserTypeId", "image");
		map1.put("advertiserTypeName", "图片广告");
		resultList.add(map1);
		Map map2 = new HashMap();
		map2.put("advertiserTypeId", "video");
		map2.put("advertiserTypeName", "视频广告");
		resultList.add(map2);
		return resultList;
	}
	
	@Override
	public List<Map<String, String>> getAirportList(Map params) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		AdvertiserManageService advertiserManageService = (AdvertiserManageService)getService("advertiserManageService");
		resultList = advertiserManageService.getAirportList(params);
		return resultList;
	}

	@Override
	public List<Map> getAdverFrequencyList(Map params) throws Exception {
		// TODO Auto-generated method stub
		List<Map> resultList = new ArrayList<Map>();
		Map map1 = new HashMap();
		map1.put("frequencyId", "1");
		map1.put("frequencyName", "1");
		resultList.add(map1);
		Map map2 = new HashMap();
		map2.put("frequencyId", "2");
		map2.put("frequencyName", "2");
		resultList.add(map2);
		return resultList;
	}

	@Override
	public List<Map> getAdverTypeList(Map params) throws Exception {
		// TODO Auto-generated method stub
		List<Map> resultList = new ArrayList<Map>();
		Map map1 = new HashMap();
		map1.put("typeId", "1");
		map1.put("typeName", "A类");
		resultList.add(map1);
		Map map2 = new HashMap();
		map2.put("typeId", "2");
		map2.put("typeName", "B类");
		resultList.add(map2);
		return resultList;
	}

	@Override
	public Map getAdvertiserById(String rowKey) throws Exception {
		// TODO Auto-generated method stub
		AdvertiserManageService advertiserManageService = (AdvertiserManageService)getService("advertiserManageService");
		Map resultMap = advertiserManageService.getAdvertiserById(rowKey);
		return resultMap;
	}


}
