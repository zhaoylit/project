package com.zkkj.backend.dao.mybatis.mapper.biz;

import java.util.List;
import java.util.Map;

import com.zkkj.backend.entity.biz.NoticePerson;

import org.apache.ibatis.annotations.Param;

public interface NoticePersonMapper {
    int deleteByPrimaryKey(@Param("id") Integer id, @Param("noticeId") String noticeId);

    int insert(NoticePerson record);

    int insertSelective(NoticePerson record);

    NoticePerson selectByPrimaryKey(@Param("id") Integer id, @Param("noticeId") String noticeId);

    int updateByPrimaryKeySelective(NoticePerson record);

    int updateByPrimaryKey(NoticePerson record);
    
    List<Map<String, Object>> getAllPersonNoticeList(
			Map<String, Object> params);
    
    public Integer getNoticePersonListByTotal(Map<String, Object> persion);
}