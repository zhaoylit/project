package com.zkkj.backend.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zkkj.backend.dao.mybatis.mapper.UserBarcodeMapper;

@Service("barcodeUtil")
public class BarcodeUtil {
	@Autowired(required = false)
	private UserBarcodeMapper userBarcodeMapper;
	public void saveBarcode(String barcode){
		System.out.println("----------");
	}
}
