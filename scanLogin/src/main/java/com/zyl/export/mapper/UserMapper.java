package com.zyl.export.mapper;

import java.util.Map;

/**
 * <p>Description: </p>
 * @author zyl
 * @date 2018年1月30日
 * @version 1.0
 */
public interface UserMapper {
    Map<String,String> getUserInfo(String userId);
}
