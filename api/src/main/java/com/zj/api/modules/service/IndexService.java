package com.zj.api.modules.service;

import java.util.List;
import java.util.Map;

public interface IndexService {

	/**
	 * 查询首页轮播图信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map> getCarouselFigure(Map params) throws Exception;
	/**
	 * 查询app_key和app_secret信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map> getSecretList(Map params) throws Exception;
	
	String getSecretByKey(Map params) throws Exception;
}
