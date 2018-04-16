package com.zkkj.backend.dao.mybatis.mapper.biz;

import java.util.List;
import java.util.Map;

import com.zkkj.backend.entity.biz.NoticeFlight;

import org.apache.ibatis.annotations.Param;

public interface NoticeFlightMapper {
    int deleteByPrimaryKey(@Param("id") Integer id, @Param("noticeId") String noticeId);

    int insert(NoticeFlight record);

    int insertSelective(NoticeFlight record);

    NoticeFlight selectByPrimaryKey(@Param("id") Integer id, @Param("noticeId") String noticeId);

    int updateByPrimaryKeySelective(NoticeFlight record);

    int updateByPrimaryKey(NoticeFlight record);

    
    List<Map<String, String>> getFlightNoticeListByDate(Map<String, Object> createTime);
    
    List<Map<String, Object>> getAllFlightNoticeList(Map<String, Object> params);
    
    Integer getNoticeFlightListTotal(Map<String, Object> notice);
    
    Integer getFlightNoticeListByDateTotal(Map<String, Object> notice);
    
    
}