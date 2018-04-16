package com.zkkj.backend.dao.mybatis.mapper.biz;

import java.util.List;
import java.util.Map;

import com.zkkj.backend.entity.biz.FlightInfo;

import org.apache.ibatis.annotations.Param;

public interface FlightInfoMapper {
    int deleteByPrimaryKey(@Param("id") Integer id, @Param("flightNo") String flightNo);

    int insert(FlightInfo record);

    int insertSelective(FlightInfo record);

    FlightInfo selectByPrimaryKey(@Param("id") Integer id, @Param("flightNo") String flightNo);

    int updateByPrimaryKeySelective(FlightInfo record);

    int updateByPrimaryKey(FlightInfo record);
    
    List<Map<String, Object>> getAllFlightPlanList();
    
    Map<String, String> getSimpleFlightPlan(String flightNo);

}