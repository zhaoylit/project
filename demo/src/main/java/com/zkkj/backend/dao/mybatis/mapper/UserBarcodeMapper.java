package com.zkkj.backend.dao.mybatis.mapper;

import java.util.Map;

public interface UserBarcodeMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(Map record);
    Map selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(Map params);
}