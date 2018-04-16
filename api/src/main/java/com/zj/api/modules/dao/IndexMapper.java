package com.zj.api.modules.dao;

import java.util.List;
import java.util.Map;

public interface IndexMapper {
	List<Map> getSecretList(Map params) throws Exception;
	List<Map> getCarouselFigure(Map paramsMap) throws Exception;
	String getSecretByKey(String app_key) throws Exception;
}
