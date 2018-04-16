package com.zj.api.common.utils;

import java.util.Collections;
import java.util.Map;

/**
 * 接口参数和数据返回类
 * @author ZYL_PC
 */
public class JsonInput {
	private int     code = 200;
	private String  msg = "OK";
	private String  version = "1.0";
	private String  osType = "";
	private String  mobile = "";
	private String  ip;
	private Map     data = Collections.EMPTY_MAP;
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Map getData() {
		return data;
	}
	public void setData(Map data) {
		this.data = data;
	}
}
