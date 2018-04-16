package com.zkkj.backend.service.biz.notice;

import java.util.List;
import java.util.Map;

import com.zkkj.backend.entity.biz.NoticeFlight;
import com.zkkj.backend.entity.biz.NoticePerson;

public interface INoticeService {
	/**
	 * 发布航班提醒
	 * @param notice
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> pushFlightNotice(NoticeFlight notice) throws Exception;
	/**
	 * 发布找人公告
	 * @param notice
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> pushPersionNotice(NoticePerson notice) throws Exception;
	/**
	 * 查询航班公告历史
	 * @param notice
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> getNoticeFlightList(Map<String, Object> notice)throws Exception;
	/**
	 * 查询找人公告历史
	 * @param notice
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getNoticePersionList(Map<String, Object> notice)throws Exception;
	
	/**
	 * 查询单个航班计划
	 * @param flightNo
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> getOneFlightPlan(String flightNo) throws Exception;
	/**
	 * 查询所有航班计划
	 */
	public List<Map<String, Object>> getAllFlightPlanList() throws Exception;
	/**
	 * 按条件查询公告历史
	 * @param notice
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getNoticeFlightListByParam(Map<String, Object> notice) throws Exception;
	
	public Integer getNoticeFlightListTotal(Map<String, Object> notice) throws Exception;
	
	public Integer getNoticeFlightListByDateTotal(Map<String, Object> notice) throws Exception;
	
	public Integer getNoticePersonListByTotal(Map<String, Object> notice)throws Exception;
	
}
