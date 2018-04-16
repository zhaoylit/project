package com.zj.api.common.utils;

/**
 * 
 * 接口代码枚举类
 * @author ZYL_PC
 */
public enum CodeEnum {
	OK(200,"OK"),
	SYSTEM_ERROR(500,"系统错误"),
	ILLEGAL_REQUEST(201,"非法请求"),
	ILLEGAL_PARAM(202,"缺少必要参数"),
	ILLEGAL_SIGN(203,"非法签名"),
	REQUEST_TIMEOUT(204,"请求超时"),
	APP_KEY_ERRROR(200,"app_key错误"),
	;  
    // 成员变量  
    private int code;  
    private String msg;  
    // 构造方法  
    private CodeEnum(int code, String msg) {  
        this.code = code;  
        this.msg = msg;  
    }
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}	
