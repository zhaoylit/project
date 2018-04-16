package com.zkkj.backend.service.base.system;

import java.util.List;
import java.util.Map;
import com.zkkj.backend.entity.SysResourceInfo;


public interface SysResourceService {
	
	public int addSysResource(SysResourceInfo resource);

	public int editSysResource(SysResourceInfo resource);

	public int removeSysResource(Integer resourceId);

	public int getSysResourceListCount(Map<String, Object> params);

	public List<Map<String, Object>> getSysResourceList(Map<String, Object> params);

}
