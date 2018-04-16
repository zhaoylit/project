package com.zkkj.backend.common.bean;

import java.util.Map;

import com.zkkj.backend.common.constant.IConstant;
import com.zkkj.backend.common.util.ReflectUtil;

public class JsonInput {
  private String method;//方法名
  private String ver = "1.0";//版本号
  private String ip;
  private String randStr;
  private String uuid;
  private String osType;
  private String modelType;
  private String sessionId;
  private String operatorId;//用户id
  private String sign;
  private String resultCode=IConstant.RESULT_CODE_SUCCESS;//业务结果，"SUCCESS"或"FAIL"。默认成功
  private String resultMsg;//结果信息,例如："验证码错误"
  private Map params;//其它参数数据
  
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public Map getParams() {
		return params;
	}
	public void setParams(Map params) {
		//将map中为null的值转为空字符
		this.params = ReflectUtil.transNullMap(params);
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRandStr() {
		return randStr;
	}
	public void setRandStr(String randStr) {
		this.randStr = randStr;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
  
}
