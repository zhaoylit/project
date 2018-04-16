package com.zyl.scan.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.zyl.scan.mapper.ScanMapper;

/**
 * <p>Description: </p>
 * @author zyl
 * @date 2017年12月7日
 * @version 1.0
 */
@Service
public class ScanService {
    @Autowired
    private ScanMapper testMaper;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String,Object>> selectList(Map<String,Object> params){
        return testMaper.selectList(params);
    }
}
