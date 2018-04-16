package com.zj.web.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

public class HttpUtil {
	
	private static final Logger logger = Logger.getLogger(HttpUtil.class);
	
	public static String httpPost(String postURL, List<NameValuePair> nameValuePairList, String requestBody) throws Exception {
		try {
			HttpClient httpclient = new HttpClient();
			NameValuePair[] param = null;
			if(nameValuePairList != null && nameValuePairList.size() > 0){
				param = new NameValuePair[nameValuePairList.size()];
				if(nameValuePairList != null && !nameValuePairList.isEmpty()){
					for(int i = 0;i < nameValuePairList.size();i++){
						param[i] = nameValuePairList.get(i);
					}
				}
			}
			PostMethod postMethod = new PostMethod(postURL);
			postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");  
			postMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8	");  
			postMethod.setRequestBody(param);
		
			int statusCode = httpclient.executeMethod(postMethod);
			String response = postMethod.getResponseBodyAsString();
			logger.info("statusCode:" + statusCode + " response:" + response);
			postMethod.releaseConnection();
			return response;
		} catch (HttpException e) {
			logger.error("HttpException error!", e);
			throw new HttpException("HttpException error!", e);
		} catch (IOException e) {
			logger.error("IOException error!", e);
			throw new IOException("IOException error!", e);
		}
	}
	
	public static String httpGet(String getURL,  List<NameValuePair> nameValuePairList) throws Exception{
				
		try {
			HttpClient httpclient = new HttpClient();
			
//			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
//			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
			
//			Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);   
//			Protocol.registerProtocol("https", myhttps);
			
			if(nameValuePairList != null && !nameValuePairList.isEmpty()){
				boolean isFirstFlag = true;
				for(NameValuePair nameValuePair : nameValuePairList){
					getURL = getURL +( isFirstFlag?"?":"&") + nameValuePair.getName() + "=" + nameValuePair.getValue();
					isFirstFlag = false;
				}
			}
			
			logger.info("getURL:" + getURL);
			
			GetMethod getMethod = new GetMethod(getURL); 
			getMethod.setRequestHeader( "Content-Type", "text/html;charset=utf-8" );
			
			int statusCode = httpclient.executeMethod(getMethod);
			String response = getMethod.getResponseBodyAsString();
			logger.info("statusCode:" + statusCode + " response:" + response);
			
			getMethod.releaseConnection();
			
			return response;
		} catch (HttpException e) {
			logger.error("HttpException error!", e);
			throw new HttpException("HttpException error!", e);
		} catch (IOException e) {
			logger.error("IOException error!", e);
			throw new IOException("IOException error!", e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
		NameValuePair avp = new NameValuePair();
		avp.setName("deviceIds");
		avp.setValue("1,2,3,4");
		nameValuePairList.add(avp);
		NameValuePair avp1 = new NameValuePair();
		avp1.setName("message");
		avp1.setValue("121212");
		nameValuePairList.add(avp1);
		String returnResq = httpPost("http://192.168.3.6:8080/zkkjweb/advert/pushProgramMessage.do", nameValuePairList, null);
		System.out.println(returnResq);
		
		
		
//		HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://localhost:9099/execute.do").openConnection(); 
//		httpURLConnection.setDoOutput(true);
//		httpURLConnection.getOutputStream().write(1);
	}
	
}
