package com.zyl.scan.mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: </p>
 * @author zyl
 * @date 2017年12月7日
 * @version 1.0
 */
public interface ScanMapper {
    List<Map<String,Object>> selectList(Map<String,Object> params);
}
