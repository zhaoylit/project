package com.zkkj.backend.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.zkkj.backend.entity.SysResourceInfo;

public interface SysResourceInfoMapper {
    int deleteByPrimaryKey(Integer resourceId);

    int insert(SysResourceInfo record);

    int insertSelective(SysResourceInfo record);

    SysResourceInfo selectByPrimaryKey(Integer resourceId);

    int updateByPrimaryKeySelective(SysResourceInfo record);

    int updateByPrimaryKeyWithBLOBs(SysResourceInfo record);

    int updateByPrimaryKey(SysResourceInfo record);
    
    int getSysResourceListCount(Map<String,Object> params);
    
    List<Map<String, Object>> getSysResourceList(Map<String, Object> params);
}