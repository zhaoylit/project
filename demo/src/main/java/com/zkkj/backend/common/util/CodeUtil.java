package com.zkkj.backend.common.util;

import java.util.Random;

import org.springframework.stereotype.Service;

public class CodeUtil {
	
	/**
	 * @param num
	 * @return
	 * @Description:生成制定位数的数字验证码
	 */
	public static String getNumCode(int num){
		Random random = new Random(); 
        String result="";
        for(int i=0;i<num;i++){
            result+=random.nextInt(10);    
        }
        return result;
	}


	public static void main(String[] args) {

	}
}
