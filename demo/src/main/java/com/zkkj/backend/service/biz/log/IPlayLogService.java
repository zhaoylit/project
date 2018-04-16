package com.zkkj.backend.service.biz.log;

import java.util.List;
import java.util.Map;


public interface IPlayLogService {
	//查询播放日志列表
	List<Map<String, String>> getLog(Map params) throws Exception;
	
	//查询总条数
	int getpalyLogListCount(Map params) throws Exception;
	
	//根据rowKey查询单条数据
	Map getLogById(String rowKey) throws Exception;
}
