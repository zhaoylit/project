package com.zkkj.backend.common.util;

import java.net.URLEncoder;

import org.apache.log4j.Logger;
/**
 * 
 * 1、注册：您在申请来去互联平台上的验证码为：@。（@分钟内有效，如非本人操作请忽略或咨询@，本消息免费）
 *
 */
public class SMSSender {
	
	private static final Logger logger = Logger.getLogger(SMSSender.class);
	
	private static SMSSender smsSender;
	
	private SMSSender(){	
	} 
	
	public static SMSSender getInstance(){
		if(smsSender  == null){
			smsSender = new SMSSender();
		}
		return smsSender;
	}

	public synchronized Object[] sendSMS(String smsMob, String content, String sendType) {
		
		logger.info("smsMob:" + smsMob + " content" + content);

		try {
			//mtLogInfoDao = (IMtLogInfoDao)AppContext.getInstance().getApplicationContext().getBean("mtLogInfoDao");
			
			String smsSendURL = (String)CustomizedPropertyConfigurer.getContextProperty("sms_send_url");
			String smsUser = (String)CustomizedPropertyConfigurer.getContextProperty("sms_user");
			String smsPasswd = (String)CustomizedPropertyConfigurer.getContextProperty("sms_passwd");
			String smsSign = (String)CustomizedPropertyConfigurer.getContextProperty("sms_sign");
			String smsSuccessResult = (String)CustomizedPropertyConfigurer.getContextProperty("sms_success_result");
			
			logger.info("smsSendURL:" + smsSendURL + 
							  " smsUser:" + smsUser +
							  " smsPasswd:" + smsPasswd + 
							  " smsSign:" + smsSign + 
							  " smsSuccessResult:" + smsSuccessResult);
			
			StringBuffer sendURL = new StringBuffer(smsSendURL);
			sendURL.append("?name=").append(smsUser);
			sendURL.append("&pwd=").append(smsPasswd);
			sendURL.append("&mobile=").append(smsMob);
			sendURL.append("&content="+URLEncoder.encode(content,"UTF-8"));
			sendURL.append("&stime=");
			sendURL.append("&sign="+URLEncoder.encode(smsSign,"UTF-8"));
			sendURL.append("&type=pt&extno=");
			
			logger.info("sendURL:"+sendURL.toString());

			String result = HttpUtil.httpPost(sendURL.toString(), null, null);
			logger.info("result:" + result);
			
			Object[] resultObj=new Object[2];
			
			resultObj[0]=(result != null && result.trim().length() > smsSuccessResult.length() && result.trim().substring(0,  smsSuccessResult.length()) .equals( smsSuccessResult))? true:false;
			resultObj[1]=result;
			return  resultObj;

		} catch (Exception ex) {
			logger.error("sendSMS error!", ex);
			//throw new Exception("sendSMS error!", ex);
			 //return false;
		}
		
		return null;

	}
	
	public static void main(String[] args) throws Exception{
		String smsSendURL 		= "http://web.cr6868.com/asmx/smsservice.aspx";
		String smsUser 			= "18614069319";
		String smsPasswd 		= "410AA51289B8D871A7311DCDC1BF";
		String smsSign 			= "\u6765\u53BB\u4E92\u8054";
		String smsSuccessResult = "0";
		
		logger.info("smsSendURL:" + smsSendURL + 
						  " smsUser:" + smsUser +
						  " smsPasswd:" + smsPasswd + 
						  " smsSign:" + smsSign + 
						  " smsSuccessResult:" + smsSuccessResult);
		
		StringBuffer sendURL = new StringBuffer(smsSendURL);
		sendURL.append("?name=").append(smsUser);
		sendURL.append("&pwd=").append(smsPasswd);
		sendURL.append("&mobile=").append("18518471051");
		sendURL.append("&content="+URLEncoder.encode("您的优惠送机订单，已安排司机赵师傅（联系电话18518471051，车牌桂CNV226）为您服务，请保持电话通畅，静候联系，预祝旅途愉快。","UTF-8"));
		sendURL.append("&stime=");
		sendURL.append("&sign="+URLEncoder.encode(smsSign,"UTF-8"));
		sendURL.append("&type=pt&extno=");
		
		logger.info("sendURL:"+sendURL.toString());

		String result = HttpUtil.httpPost(sendURL.toString(), null, null);
		logger.info("result:" + result);
		
		Object[] resultObj=new Object[2];
		
	}
}
