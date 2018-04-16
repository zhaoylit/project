package com.zkkj.backend.dao.mybatis.mapper;

import java.util.List;

import com.zkkj.backend.entity.BackendDepartment;
import com.zkkj.backend.entity.BackendUser;

public interface BackendDepartmentMapper {
    int deleteByPrimaryKey(Integer departmentId);

    int insert(BackendDepartment record);

    int insertSelective(BackendDepartment record);

    BackendDepartment selectByPrimaryKey(Integer departmentId);

    int updateByPrimaryKeySelective(BackendDepartment record);

    int updateByPrimaryKey(BackendDepartment record);
    
    public List<BackendDepartment> selectDepartmentList(BackendDepartment department);
    
    public List<BackendUser> getDepartmentUser(Integer departmentId);
}