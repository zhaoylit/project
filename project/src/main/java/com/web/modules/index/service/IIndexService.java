package com.web.modules.index.service;

import java.util.Map;

public interface IIndexService {

	//验证登录
	Map checkLogin(Map params) throws Exception;

}
