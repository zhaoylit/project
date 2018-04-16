package com.zkkj.backend.service.biz.device;

import java.util.List;
import java.util.Map;
/**
 * 设备维护接口
 * @author 刘志成
 */
public interface IDeviceMtnService {
	/**
	 * 设备维护信息列表
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> getDeviceMtnList(Map<String, Object> map)throws Exception;
	/**
	 * 添加/更行维护设备日志信息
	 * @param map
	 * @throws Exception
	 */
	public Map<String, Object> addOrEditDeviceMtn(Map<String, Object> map)throws Exception;
	/**
	 * 删除设备维护记录
	 * @param map
	 * @throws Exception
	 */
	public Map<String,Object> deleteDeviceMtn(Map<String, Object> map)throws Exception;
	/**
	 * 查询单条设备维护记录
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getOneRow(String rowKey)throws Exception;
}
