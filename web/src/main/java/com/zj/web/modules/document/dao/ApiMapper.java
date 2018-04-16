package com.zj.web.modules.document.dao;

import java.util.List;
import java.util.Map;

public interface ApiMapper {
   // 查询api 类目类比较
   List<Map> getApiCategoryList(Map params ) throws Exception;
}