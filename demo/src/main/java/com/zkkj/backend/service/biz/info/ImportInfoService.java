package com.zkkj.backend.service.biz.info;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 导入航班信息接口
 * @author 
 */
public interface ImportInfoService {
	/**
	 * 导入航空公司信息
	 * @return
	 * @throws Exception
	 */
	public boolean upLoadAirlinePlan(String name,String pathStr,MultipartFile file)throws Exception;
	
	/**
	 * 导入VIP厅信息
	 * @return
	 * @throws Exception
	 */
	public boolean upLoadViproomPlan(String name,String pathStr,MultipartFile file)throws Exception;
	/**
	 * 导入机场信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public boolean upLoadAirport(String name,String pathStr,MultipartFile file)throws Exception;
	
	/**
	 * 导入起飞信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public boolean upLoadflight(String name,String pathStr,MultipartFile file)throws Exception;
	
	
	
}
