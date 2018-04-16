package com.zkkj.backend.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import com.zkkj.backend.common.util.DateUtil;
import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.websocket.WebsocketEndPoint;
import com.zkkj.backend.entity.biz.NoticeFlight;
import com.zkkj.backend.entity.biz.NoticePerson;
import com.zkkj.backend.service.biz.notice.INoticeService;
import com.zkkj.program.service.IUserBarcodeService;
import com.zkkj.program.service.impl.UserBarcodeServiceImpl;

@Component
public class ContextInitListener implements ApplicationListener<ContextRefreshedEvent> {
	private final long delay = 1000;
	private final long intevalPeriod = 1000 * 30;
	IUserBarcodeService userBarcodeService = new UserBarcodeServiceImpl();
	@Autowired
	private INoticeService noticeService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			TimerTask task = new TimerTask() {  
	            @Override  
	            public void run() {  
	                // task to run goes here  
	            	//定时查询 扫码进站的乘客 看是否在规定的时间内 未出站
					Map params = new HashMap();
					params.put("state", "0");
					List<Map> selectBarcodeList = null;
					try {
						selectBarcodeList = userBarcodeService.selectBarcodeList(params);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
					if(selectBarcodeList != null && selectBarcodeList.size() > 0){
						//推送公告通知用户应该离场
						System.out.println("--------------------" + selectBarcodeList);
						for(Map mm : selectBarcodeList){ 
							String flightNo  =  ParamsUtil.nullDeal(mm, "flightNo", "");
							String userName = ParamsUtil.nullDeal(mm, "name", "");
							//根据航班号 查询航班 信息  ，看是否 开始等级,如果开始登记  发送航班提醒
							try {
								Map flightMap = noticeService.getOneFlightPlan(flightNo);
								if(flightMap != null){
									//获取航班信息， 航班出发地和目的地
									String depCity = ParamsUtil.nullDeal(flightMap , "depCity", "");//起飞城市
									String arrCity = ParamsUtil.nullDeal(flightMap , "arrCity", "");//到达城市
									String takeoffTime = ParamsUtil.nullDeal(flightMap , "takeoffTime", "");//起飞时间
									if(takeoffTime != null && !"".equals(takeoffTime)){
										int leftTime = getLeftMinute(takeoffTime);
										//判断起飞时间是否到达
										if(leftTime > 0 && leftTime <= 10){
											//航班将要在10分钟后起飞
											//发送公告
											String msg = "飞往" + arrCity  +"的航班" + leftTime + "分钟后登机";
											NoticePerson persionNotice = new NoticePerson();
											persionNotice.setName(userName);
											persionNotice.setFlightNo(flightNo);
											persionNotice.setMsg(msg);
											
											//发送
											noticeService.pushPersionNotice(persionNotice);
											
											//发送全局提醒
											Map dataMap = new HashMap();
											dataMap.put("type", "overall_info");//socket事件类型
											dataMap.put("content","乘客：" + userName +"<br/>" + "内容：" + msg);
						            		
						            		String json1 = JSONObject.fromObject(dataMap).toString();
						            		//发送消息给前端
						            		new WebsocketEndPoint().sendMessageToUsers(new TextMessage(json1));
										}
									}
									
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out.println( "---------------" + e.getMessage());
							}
						}
					}
	            }  
	        };  
	        Timer timer = new Timer();  
	        // schedules the task to be run in an interval  
	        timer.scheduleAtFixedRate(task, delay, intevalPeriod);  
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	public int getLeftMinute(String takeoffTime){
		StringBuffer sb = new StringBuffer(takeoffTime);
		sb.insert(2, ":").insert(5, ":00");
		String startTime = DateUtil.getNowStrDate() + " " + sb.toString();
		Long start = DateUtil.strToDateLong(startTime).getTime();
		long now = new Date().getTime();
		return (int) ((start - now) /(1000 * 60));
	}
}
