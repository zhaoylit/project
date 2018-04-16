package com.zyl.scan.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>Description:加载配置文件类 </p>
 * @author zyl
 * @date 2017年12月13日
 * @version 1.0
 */
@Component
@ConfigurationProperties(prefix = "my")
public class MyConfig {
    private static String returnPath;
    private static String socketUrl;

    public static void setReturnPath(String returnPath) {
        MyConfig.returnPath = returnPath;
    }

    public static void setSocketUrl(String socketUrl) {
        MyConfig.socketUrl = socketUrl;
    }

    public static String getReturnPath() {
        return returnPath;
    }

    public static String getSocketUrl() {
        return socketUrl;
    }
}
