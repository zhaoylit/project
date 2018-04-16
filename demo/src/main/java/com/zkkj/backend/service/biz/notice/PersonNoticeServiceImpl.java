package com.zkkj.backend.service.biz.notice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zkkj.backend.dao.mybatis.mapper.biz.FlightInfoMapper;
import com.zkkj.backend.dao.mybatis.mapper.biz.NoticePersonMapper;
import com.zkkj.backend.entity.biz.NoticePerson;

@Service("personNoticeService")
public class PersonNoticeServiceImpl implements PersonNoticeService {

	@Autowired
	NoticePersonMapper noticePersonMapper;	
	@Autowired
	FlightInfoMapper flightInfoMapper;
	
	@Override
	public List<Map<String, Object>> getAllPersonNoticeList(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return noticePersonMapper.getAllPersonNoticeList(params);
	}

	@Override
	public List<Map<String, String>> getPersonNoticeListByRegexRowFilter(
			String regex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> getPersonNoticeListBySubRowFilter(
			String substr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getPersonNoticeByKey(String rowKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addPersonNotice(NoticePerson notice) {
		// TODO Auto-generated method stub
		return noticePersonMapper.insertSelective(notice);
	}

	@Override
	public int updatePersonNotice(Map<String, Object> notice) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deletePersonNotice(String rowKey) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer getNoticePersonListByTotal(Map<String, Object> persion) {
		// TODO Auto-generated method stub
		return noticePersonMapper.getNoticePersonListByTotal(persion);
	}

	
	
}
