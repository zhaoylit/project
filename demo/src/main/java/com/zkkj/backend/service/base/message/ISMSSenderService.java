package com.zkkj.backend.service.base.message;

public interface ISMSSenderService {
	public boolean sendSMS(String smsMob, String content, String sendType,String code,String uuid) throws Exception;
	public double queryBalance();
}
