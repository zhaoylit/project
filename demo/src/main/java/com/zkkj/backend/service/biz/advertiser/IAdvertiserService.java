package com.zkkj.backend.service.biz.advertiser;

import java.util.List;
import java.util.Map;

public interface IAdvertiserService {
	//查询广告商列表数量
	List<Map<String, String>> getAdvertiserList(Map params) throws Exception;
	//查询广告商列表数量
	long getAdvertiserListCount(Map params) throws Exception;
	//添加或者更新广告信息
	Map addOrEditAdvertiser(Map params) throws Exception;
	//删除广告商
	Map deleteAdvertiser(Map params) throws Exception;
	//查询广告类型列表
	List<Map> getAdvertiserTypeList(Map params) throws Exception;
	//查询机场列表
	List<Map<String, String>> getAirportList(Map params) throws Exception;
	//查询广告频次列表	
	List<Map> getAdverFrequencyList(Map params) throws Exception;
	//查询广告类型列表
	List<Map> getAdverTypeList(Map params) throws Exception;
	//查询单条数据
	Map getAdvertiserById(String rowKey) throws Exception;
}
