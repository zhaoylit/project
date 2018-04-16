package com.zkkj.backend.dao.mybatis.mapper.biz;

import com.zkkj.backend.entity.biz.GateInfo;

public interface GateInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GateInfo record);

    int insertSelective(GateInfo record);

    GateInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GateInfo record);

    int updateByPrimaryKey(GateInfo record);
}