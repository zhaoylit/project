package com.zyl.scan.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * <p>Description: </p>
 * @author zyl
 * @date 2017年12月13日
 * @version 1.0
 */
@Configuration
public class WebSocketConfig {
    @Bean  
    public ServerEndpointExporter serverEndpointExporter(){  
        return new ServerEndpointExporter();  
    }
}
