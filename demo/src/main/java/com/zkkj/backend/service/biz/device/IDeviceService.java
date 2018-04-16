package com.zkkj.backend.service.biz.device;

import java.util.List;
import java.util.Map;
/**
 * 设备信息管理接口
 * @author 刘志成
 *
 */
public interface IDeviceService {
	
	/**
	 * 查询设备列表/根据条件查询航显设备信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map<String,String>> getDeviceList(Map<String, Object> map) throws Exception;
	/**
	 * 查询设备列表数量
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int getDeviceListCount(Map<String,Object> params) throws Exception;
	
	/**
	 * 添加或者更新设备信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> addOrEditDevice(Map<String,Object> params) throws Exception;
	
	/**
	 * 删除设备
	 * @param params
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> deleteDevice(Map<String,Object> params) throws Exception;
	/**
	 * 查询一条设备数据
	 * @param rowKey
	 * @return
	 * @throws Exception
	 */
	Map getOneRow(String rowKey) throws Exception;
	/**
	 * 查询所有机场设备
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	List<Map<String, String>> getAirportByName(Map params)throws Exception;
	/**
	 * 查询所有机场设备
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	List<Map<String, String>> getViproomById(Map params)throws Exception;
	
	/**
	 * 查询
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<Map<String,String>> getAirlineByName(String orgId)throws Exception;
	/**
	 * 查询所有VIP厅
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> getViproomByName()throws Exception;
	/**
	 * 未知
	 * @param transToMAP
	 * @return
	 */
	//TODO
	List<Map<String, String>> getDeviceByList(Map<String, Object> transToMAP)throws Exception;
	
	/**
	 * 根据航空公司编号查找机场信息
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> getAirportByAirlineCode(String airlineCode)throws Exception;
	
	/**
	 * 根据机场编号查询VIP厅信息
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> getViproomByAirportCode(String airlineCode)throws Exception;
	/**
	 * 设备重新启动
	 * @return
	 * @throws Exception
	 */
	Map deviceReboot(Map params) throws Exception;
	
	//根据条件查询设备信息
	public List<Map<String, String>> getDeviceInfoByParams(Map params) throws Exception;
	
	//根据UUID查询设备信息
	public List<Map<String, String>> getDeviceInfoByUUID(String uuid) throws Exception;
	
	//审核广告为通过
	Map updateDeviceExaminePass(Map params) throws Exception;
	
}
