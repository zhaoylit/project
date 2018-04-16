package com.zkkj.backend.dao.mybatis.mapper;

import com.zkkj.backend.dao.dataSource.MultDS;
import com.zkkj.backend.entity.MtLogInfo;
@MultDS("LAIQUDB")
public interface MtLogInfoMapper {
    int deleteByPrimaryKey(Integer mtLogId);

    int insert(MtLogInfo record);

    int insertSelective(MtLogInfo record);

    MtLogInfo selectByPrimaryKey(Integer mtLogId);

    int updateByPrimaryKeySelective(MtLogInfo record);

    int updateByPrimaryKeyWithBLOBs(MtLogInfo record);

    int updateByPrimaryKey(MtLogInfo record);
}