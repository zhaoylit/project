package com.zkkj.backend.service.biz.notice;

import java.util.List;
import java.util.Map;

import com.zkkj.backend.entity.biz.NoticeFlight;

public interface FlightNoticeService {
	/**
	 * 获得所有的航班公告
	 * @return
	 */
	public List<Map<String, Object>> getAllFlightNoticeList(Map<String, Object> params);
    /**
     * 根据正则表达式行健过滤查询
     * @param filterList
     * @return
     */
	public List<Map<String, String>> getFlightNoticeListByRegexRowFilter(String regex);
	/**
	 * 根据子字符串行健过滤查询
	 * @param regex
	 * @return
	 */
	public List<Map<String, String>> getFlightNoticeListBySubRowFilter(String substr);
   /**
    * 获得某天的航班公告
    * @param date
    * @return
    */
	public List<Map<String, String>> getFlightNoticeListByDate(Map<String, Object> params);
   /**
    * 根据行健精确查询
    * @param rowKey
    * @return
    */
	public Map<String, String> getFlightNoticeByKey(String rowKey);
   /**
    * 增加航班公告
    * @param notice
    * @return
    */
	public int addFlightNotice(NoticeFlight notice);
    /**
     * 修改航班公告
     * @param notice
     * @return
     */
	public int updateFlightNotice(Map<String, Object> notice);
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public Map<String, String> getSimpleFlightPlan(String flightNo);
	
	/**
	 * 获得所有航班计划
	 * @param substr
	 * @return
	 */
	public List<Map<String, Object>> getAllFlightPlanList();
	
	public int deleteFlightNotice(String rowKey);
	
	public Integer getNoticeFlightListTotal(Map<String, Object> notice);
	

	public Integer getNoticeFlightListByDateTotal(Map<String, Object> notice);
   
}
