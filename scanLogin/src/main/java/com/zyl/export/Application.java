package com.zyl.export;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>Description: springboot 启动类</p>
 * @author zyl
 * @date 2017年12月7日
 * @version 1.0
 */
@SpringBootApplication
@MapperScan(basePackages = "com.zyl.export.mapper")
public class Application implements CommandLineRunner{
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    } 
    @Override
    public void run(String... arg0) throws Exception {
        // TODO Auto-generated method stub
        log.info(">>>>>>>>>>>>>>>服务启动完成<<<<<<<<<<<<<");
    }
}
