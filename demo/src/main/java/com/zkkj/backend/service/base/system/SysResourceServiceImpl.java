package com.zkkj.backend.service.base.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zkkj.backend.dao.mybatis.mapper.SysResourceInfoMapper;
import com.zkkj.backend.entity.SysResourceInfo;
@Service("sysResourceService")
public class SysResourceServiceImpl implements SysResourceService {

	@Override
	public int addSysResource(SysResourceInfo resource) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int editSysResource(SysResourceInfo resource) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removeSysResource(Integer resourceId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSysResourceListCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Map<String, Object>> getSysResourceList(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
