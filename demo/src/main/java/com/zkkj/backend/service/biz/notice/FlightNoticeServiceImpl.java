package com.zkkj.backend.service.biz.notice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zkkj.backend.dao.mybatis.mapper.biz.FlightInfoMapper;
import com.zkkj.backend.dao.mybatis.mapper.biz.NoticeFlightMapper;
import com.zkkj.backend.entity.biz.NoticeFlight;

@Service("flightNoticeService")
public class FlightNoticeServiceImpl implements FlightNoticeService {

	
	@Autowired
	NoticeFlightMapper noticeFlightMapper;
	
	@Autowired
	FlightInfoMapper flightInfoMapper;
	@Override
	public int addFlightNotice(NoticeFlight arg0) {
		// TODO Auto-generated method stub
		
		noticeFlightMapper.insertSelective(arg0);
		return 0;
	}

	@Override
	public int deleteFlightNotice(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Map<String, Object>> getAllFlightNoticeList(Map<String, Object> params){
		// TODO Auto-generated method stub
		return noticeFlightMapper.getAllFlightNoticeList(params);
	}

	@Override
	public List<Map<String, Object>> getAllFlightPlanList() {
		// TODO Auto-generated method stub
		return flightInfoMapper.getAllFlightPlanList();
	}

	@Override
	public Map<String, String> getFlightNoticeByKey(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> getFlightNoticeListByDate(
			Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		return noticeFlightMapper.getFlightNoticeListByDate(arg0);
	}

	@Override
	public List<Map<String, String>> getFlightNoticeListByRegexRowFilter(
			String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> getFlightNoticeListBySubRowFilter(
			String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getSimpleFlightPlan(String arg0) {
		// TODO Auto-generated method stub
		return flightInfoMapper.getSimpleFlightPlan(arg0);
	}

	@Override
	public int updateFlightNotice(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer getNoticeFlightListTotal(Map<String, Object> notice) {
		// TODO Auto-generated method stub
		return noticeFlightMapper.getNoticeFlightListTotal(notice);
	}

	@Override
	public Integer getNoticeFlightListByDateTotal(Map<String, Object> notice) {
		// TODO Auto-generated method stub
		return noticeFlightMapper.getFlightNoticeListByDateTotal(notice);
	}

}
