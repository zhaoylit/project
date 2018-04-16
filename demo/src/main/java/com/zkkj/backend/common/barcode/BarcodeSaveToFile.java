package com.zkkj.backend.common.barcode;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.web.socket.TextMessage;

import com.zkkj.backend.common.util.DateUtil;
import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.websocket.WebsocketEndPoint;
import com.zkkj.program.service.IUserBarcodeService;
import com.zkkj.program.service.impl.UserBarcodeServiceImpl;
 
/**
 *把条形码数据保存到文件的实现（参考实现）
 * 可根据自己的需求做一个自定义实现
 * 只要实现BarcodeSaveService接口的方法
 * 并在barcode.save.services文件中指定使用的实现类即可
 * @author ysc
 */
public class BarcodeSaveToFile implements BarcodeSaveService{
    private Writer writer;
	IUserBarcodeService userBarcodeService = new UserBarcodeServiceImpl();
    
    /**
     * 保存到文件
     * @param barcode CZ 3901 17235A072
     * 
     */
    @Override
    public void save(String barcode) {
        try {
        	String flightNo = "";
        	if(!"".equals(barcode) && barcode != null){
        		//从扫码信息中 获取航班号
        		flightNo = barcode.substring(0, barcode.lastIndexOf(" ")).replace(" ", "");
        		barcode = barcode.replace(" ", "");
        		Map params = new HashMap();
        		params.put("barcode", barcode);
        		
        		List<Map> passengerList = userBarcodeService.getPassengerList(params);
        		
        		if(passengerList == null || passengerList.size() == 0){
        			return;
        		}
        		Map  userInfo = null;
        		if(passengerList != null && passengerList.size() > 0){
        			userInfo = passengerList.get(0);
        		}
        		String userName = "";
        		String idcard = "";
        		String phone = "";
        		if(userInfo != null){ 
            		userName = (String) userInfo.get("name");
            		idcard = (String) userInfo.get("idcard");
            		phone = (String) userInfo.get("phone");
        		}
        		
        		//查询扫码信息是否存在
        		Map  selectMap = new HashMap();
        		selectMap.put("name", userName);
        		selectMap.put("idcard",idcard);
        		selectMap.put("flightNo", flightNo);
        		
        		List<Map> selectBarcodeList = userBarcodeService.selectBarcodeList(selectMap);
        		if(selectBarcodeList != null && selectBarcodeList.size() > 0){
        			//存在扫码信息 ，再次扫码 修改状态
        			Map barcodeMap = selectBarcodeList.get(0);
        			String id = ParamsUtil.nullDeal(barcodeMap, "id", "");
        			
        			//修改 状态
        			Map updateMap = new HashMap();
        			updateMap.put("id", id);
        			updateMap.put("state", "1");
        			updateMap.put("exitTime", DateUtil.getStringDate());//设置出站时间
        			int rowCount = userBarcodeService.updateByPrimaryKeySelective(updateMap);
        			if(rowCount > 0){
        				//查询 今天进出站人数
                		Map countMap = userBarcodeService.selectCount(null);
                		int inn = Integer.parseInt(ParamsUtil.nullDeal(countMap, "inn", "0"));
                		int outt = Integer.parseInt(ParamsUtil.nullDeal(countMap, "outt", "0"));
                		
                		//推送饼状图更新消息
                		Map dataMap = new HashMap();
                		dataMap.put("type", "vip_person_in_out");//socket事件类型
                		dataMap.put("inn", inn);
                		dataMap.put("out",  outt);
                		String json = JSONObject.fromObject(dataMap).toString();
                		new WebsocketEndPoint().sendMessageToUsers(new TextMessage(json));
                				
        				//乘客已扫码出站
        				Map dataMap1 = new HashMap();
        				dataMap1.put("type", "overall_info");//socket事件类型
        				dataMap1.put("content", userName + "已扫码出站（航班号： "+flightNo+"）" );
                		String json1 = JSONObject.fromObject(dataMap1).toString();
                		//发送消息给前端
                		new WebsocketEndPoint().sendMessageToUsers(new TextMessage(json1));
        			}
        		
        		}else {
        			//根据扫码信息查询乘客信息
            		Map barcodeMap = new HashMap();
            		barcodeMap.put("flightNo", flightNo);//设置航班信息
            		barcodeMap.put("name",  userName);//设置姓名
            		barcodeMap.put("idcard", idcard);//设置身份证号码
            		barcodeMap.put("phone", phone);//设置手机号码
            		barcodeMap.put("enterTime", DateUtil.getStringDate());//设置乘坐时间
            		barcodeMap.put("state", "0");
            		
                	int rowsCount = userBarcodeService.insert(barcodeMap);
                	if(rowsCount > 0){
                		//调用摄像头信息，并保存用户信息
                		
                		
                		//查询 今天进出站人数
                		Map countMap = userBarcodeService.selectCount(null);
                		int inn = Integer.parseInt(ParamsUtil.nullDeal(countMap, "inn", "0"));
                		int outt = Integer.parseInt(ParamsUtil.nullDeal(countMap, "outt", "0"));
                		
                		//推送饼状图更新消息
                		Map dataMap = new HashMap();
                		dataMap.put("type", "vip_person_in_out");//socket事件类型
                		dataMap.put("inn", inn);
                		dataMap.put("out",  outt);
                		String json = JSONObject.fromObject(dataMap).toString();
                		new WebsocketEndPoint().sendMessageToUsers(new TextMessage(json));
                		
                		//推送全局消息
                		Map dataMap1 = new HashMap();
                		dataMap1.put("type", "overall_info");//socket事件类型
                		dataMap1.put("content", userName + "已扫码进站（航班号： "+flightNo+"）" );
                		
                		String json1 = JSONObject.fromObject(dataMap1).toString();
                		//发送消息给前端
                		new WebsocketEndPoint().sendMessageToUsers(new TextMessage(json1));
                	}
        		}
        		
        	}
        	/*
        	//将扫信息存进数据库
        	Map params = new HashMap();
            if(writer==null){
                System.out.println("打开文件");
                writer=new OutputStreamWriter(new FileOutputStream("d:/barcode.txt",true));
            }
            writer.write(barcode+"\n");
            writer.flush();*/
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }
 
    /**
     * 关闭文件
     */
    @Override
    public void finish() {
        System.out.println("关闭文件");
        try {
            if(writer!=null){
                writer.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}