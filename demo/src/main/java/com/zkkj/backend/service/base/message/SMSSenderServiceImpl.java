package com.zkkj.backend.service.base.message;

import org.springframework.stereotype.Service;

import com.zkkj.backend.common.util.CustomizedPropertyConfigurer;
import com.zkkj.backend.common.util.HttpUtil;
import com.zkkj.backend.common.util.SMSSender;
import com.zkkj.backend.entity.MtLogInfo;

@Service("sMSSenderService")
public class SMSSenderServiceImpl implements ISMSSenderService {

	
	@Override
	public boolean sendSMS(String smsMob, String content, String sendType,String code,String uuid) throws Exception{
		// TODO Auto-generated method stub
		Object[] obj=SMSSender.getInstance().sendSMS(smsMob, content, sendType);
		
		if(null!=obj&&null!=obj[0]&&null!=obj[1]&&(Boolean)obj[0]){
			MtLogInfo record = new MtLogInfo();
			record.setUserMSISDN(smsMob);
			record.setSendType(sendType);
			record.setAuthCode(code);
			record.setTerminalUUID(uuid);
			record.setAuthResult("1");
			record.setContent(content);
			record.setSendResult(obj[1].toString());
			record.setSendStatus(((String)obj[1]).split(",")[0]);
			
		}
		return true;
	}

	@Override
	public double queryBalance() {
		// TODO Auto-generated method stub
		String send_url = (String)CustomizedPropertyConfigurer.getContextProperty("sms_send_url");
		String smsUser = (String)CustomizedPropertyConfigurer.getContextProperty("sms_user");
		String smsPasswd = (String)CustomizedPropertyConfigurer.getContextProperty("sms_passwd");
		double banlance = 0;
		try {
			String result = HttpUtil.httpPost(send_url+"?name="+smsUser+"&pwd="+smsPasswd+"&type=balance", null, null);
			if("0".equals(result.substring(0,result.lastIndexOf(",")))){
				return Double.parseDouble(result.substring(result.lastIndexOf(",")+1));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return banlance;
	}
	
}
