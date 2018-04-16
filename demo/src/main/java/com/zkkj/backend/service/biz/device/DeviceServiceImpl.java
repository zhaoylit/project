
package com.zkkj.backend.service.biz.device;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.httpclient.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zkkj.backend.common.socketio.BinaryEventLauncher;
import com.zkkj.backend.common.util.DESUtils;
import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.common.util.HttpUtil;
import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.util.StringUtils;
import com.zkkj.backend.service.common.BaseService;
import com.zkkj.platform.advert.AdvertManageService;
import com.zkkj.platform.device.DeviceManageService;
/**
 * 航显设备信息管理接口实现类
 * @author 刘志成
 */
@Service
public class DeviceServiceImpl extends BaseService implements IDeviceService {
	/*@Autowired
    private BinaryEventLauncher service;*/
	
	
	@Override
	public List<Map<String,String>> getDeviceList(Map<String,Object> params) throws Exception {
		DeviceManageService deviceManageService = (DeviceManageService) getService("deviceManageService");
		List<Map<String,String>> deviceList = null;
		    deviceList = deviceManageService.getDevice(params);
			if(deviceList!=null&&deviceList.size()>0){
				return deviceList;
			}
		return null;
	}
	
	@Override
	public int getDeviceListCount(Map<String,Object> params) throws Exception {
		// TODO Auto-generated method stub
		return 100;
	}
	
	@Override
	public Map<String,Object> addOrEditDevice(Map<String,Object> params) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> resultMap = new HashMap<String,Object>();
		DeviceManageService deviceManageService = (DeviceManageService)getService("deviceManageService");
		String devType = ParamsUtil.nullDeal(params, "devType", "");
		String deviceId = ParamsUtil.nullDeal(params, "deviceId", "");
		String devIp = ParamsUtil.nullDeal(params, "devIp", "");
		String airportCode = ParamsUtil.nullDeal(params, "airportCode", "");
		String viproomId = ParamsUtil.nullDeal(params, "viproomId", "");
		List<Map<String, String>> deviceIdlist = deviceManageService.getOneRowByDeviceId(deviceId);
		List<Map<String, String>> devIplist = deviceManageService.getOneRowByDevIP(devIp, airportCode, viproomId);
		List<Map<String, String>> list = deviceManageService.getViproomById(params);
		if(StringUtils.isBlank(String.valueOf(params.get("rowKey")))){
			/*if("1".equals(devType)){
				if (list!=null&&list.size()>0) {
					resultMap.put("message", "不能重复添加厅服务器，请重新选择");
					return resultMap;
				}
			} 
			if(deviceIdlist!=null&&deviceIdlist.size()>0){
				for (int i = 0; i < deviceIdlist.size(); i++) {
					if(!deviceIdlist.get(i).get("deviceId").equals("")){
						resultMap.put("message", "设备Id不能重复，请重新输入");
						return resultMap;
					}
				}
			}
			if(devIplist!=null&&devIplist.size()>0){
				for (int i = 0; i < devIplist.size(); i++) {
					if(!devIplist.get(i).get("devIp").equals("")){
						resultMap.put("message", "设备IP不能重复，请重新输入");
						return resultMap;
					}
				}
			}*/
			deviceManageService.addDevice(params);
			resultMap.put("result", "1");
			resultMap.put("message", "添加设备成功");
		}else {
			int count1 = 0,count3=0;
			String devId=null,deviceIP=null;
			/*if(deviceIdlist!=null&&deviceIdlist.size()>0){
				for (int i = 0; i < deviceIdlist.size(); i++) {
					if(!deviceIdlist.get(i).get("rowKey").equals(params.get("rowKey"))){
						count1++;
						devId = deviceIdlist.get(i).get("deviceId");
					}
				}
				if(count1 > 0){
					if(!"".equals(devId)&&devId!=null){
						resultMap.put("message", "设备Id不能重复，请重新输入");
						return resultMap;
					}
				}
			}
			if(devIplist!=null&&devIplist.size()>0){
				for (int i = 0; i < devIplist.size(); i++) {
					if(!devIplist.get(i).get("rowKey").equals(params.get("rowKey"))){
						count3++;
						deviceIP = devIplist.get(i).get("devIp");
					}
				}
				if(count3 > 0){
					if(!"".equals(deviceIP)){
						resultMap.put("message", "设备IP不能重复，请重新输入");
						return resultMap;
					}
				}
			}
			if("1".equals(devType)){
				if (list!=null&&list.size()>0) {
					resultMap.put("message", "厅服务器不能重复，请重新选择");
					return resultMap;
			 }
		}*/
			deviceManageService.updateDevice(params);
			resultMap.put("result", "1");
			resultMap.put("message", "编辑设备成功");
		}
		return resultMap;
	}

	@Override
	public Map<String,Object> deleteDevice(Map<String,Object> params) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> resultMap = new HashMap<String,Object>();
		DeviceManageService deviceManageService = (DeviceManageService) getService("deviceManageService");
		String ids  = ParamsUtil.nullDeal(params, "ids", "");
		String array[] = ids.split(",");
		if(StringUtils.isNotBlank(ids)){
			for(String str : array){
				deviceManageService.deleteDevice(str);
			}
		}
		resultMap.put("result", "1");
		resultMap.put("message", "操作成功");
		return resultMap;
	}

	@Override
	public Map<String,String> getOneRow(String rowKey) throws Exception {
		// TODO Auto-generated method stub
		DeviceManageService deviceManageService = (DeviceManageService) getService("deviceManageService");
		Map<String, String> map = deviceManageService.getOneRow(rowKey);
		if(map!=null&&map.size()>0){
			return map;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map<String, String>> getAirportByName(Map params) throws Exception {
		// TODO Auto-generated method stub
		DeviceManageService deviceManageService = (DeviceManageService) getService("deviceManageService");
		List<Map<String, String>> airList = deviceManageService.getAriportByName(params);
		if(airList!=null&&airList.size()>0){
			return airList;
		}
		return airList;
	}
	
	@Override
	public List<Map<String, String>> getViproomById(Map params)throws Exception{
		DeviceManageService deviceManageService = (DeviceManageService) getService("deviceManageService");
		List<Map<String, String>> viproomIdlist = deviceManageService.getViproomById(params);
		if(viproomIdlist!=null&&viproomIdlist.size()>0){
			return viproomIdlist;
		}
		return viproomIdlist;
	}
//	@Override
//	public List<Map<String, String>> getAirportByName() throws Exception {
//		// TODO Auto-generated method stub
//		DeviceManageService deviceManageService = (DeviceManageService) getService("deviceManageService");
//		List<Map<String, String>> airList = deviceManageService.getAriportByName();
//		if(airList!=null&&airList.size()>0){
//			return airList;
//		}
//		return airList;
//	}

	@Override
	public List<Map<String, String>> getAirlineByName(String orgId) throws Exception {
		DeviceManageService deviceManageService = (DeviceManageService) getService("deviceManageService");
		List<Map<String, String>> byList = deviceManageService.getAirlineByName(orgId);
		if(byList!=null&&byList.size()>0){
			return byList;
		}
		return Collections.emptyList();
	}
	
	@Override
	public List<Map<String, String>> getViproomByName()throws Exception{
		DeviceManageService deviceManageService = (DeviceManageService) getService("deviceManageService");
		List<Map<String, String>> byList = deviceManageService.getViproomByName();
		if(byList!=null&&byList.size()>0){
			return byList;
		}
		return Collections.emptyList();
	}
	
	@Override
	public List<Map<String, String>> getDeviceByList(
			Map<String, Object> transToMAP) throws Exception {
		DeviceManageService deviceManageService = (DeviceManageService) getService("deviceManageService");
		List<Map<String, String>> byList = deviceManageService.getDeviceByList(transToMAP);
		if(byList!=null&&byList.size()>0){
			return byList;
		}
		return Collections.emptyList();
	}

	@Override
	public List<Map<String, String>> getAirportByAirlineCode(String airlineCode)
			throws Exception {
		DeviceManageService deviceManageService = (DeviceManageService) getService("deviceManageService");
		List<Map<String, String>> airlineList = deviceManageService.getAirportByAirlineCode(airlineCode);
		if(airlineList!=null&&airlineList.size()>0){
			return airlineList;
		}
		return Collections.emptyList();
	}

	@Override
	public List<Map<String, String>> getViproomByAirportCode(String airportCode)
			throws Exception {
		DeviceManageService deviceManageService = (DeviceManageService) getService("deviceManageService");
		List<Map<String, String>> airportList = deviceManageService.getViproomByAirportCode(airportCode);
		if(airportList!=null&&airportList.size()>0){
			return airportList;
		}
		return Collections.emptyList();
	}

	@Override
	public Map deviceReboot(Map params) throws Exception {
		// TODO Auto-generated method stub
				List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
				int success = 0,error = 0;
				Map resultMap = new HashMap();
				AdvertManageService advertManageService = (AdvertManageService)getService("advertManageService");
				String deviceId1s = ParamsUtil.nullDeal(params, "deviceId1", "");//uuid
				List<Map<String, String>> deviceList=new ArrayList<Map<String,String>>();
				DeviceManageService deviceManageService = (DeviceManageService) getService("deviceManageService");
				//判断是用户是单选还是多选
				if(deviceId1s.indexOf(",")>-1){
					String deviceId1One =deviceId1s.substring(0,deviceId1s.indexOf(","));
					deviceList=deviceManageService.getOneDeviceByDeviceId1(deviceId1One);
				}else{
					deviceList=deviceManageService.getOneDeviceByDeviceId1(deviceId1s);
				}
				
				String airportCode = deviceList.get(0).get("airportCode");//机场代码
				String airlineCode = deviceList.get(0).get("airlineCode");//航空公司代码
				String viprooms =  deviceList.get(0).get("viproomId");//vip厅
							
				if(!"".equals(viprooms)){
					String [] viproomArrray = viprooms.split(",");
					int index = 0;
					for(String viproomId : viproomArrray){
						//根据vip厅信息
						Map viproomParams = new HashMap();
						viproomParams.put("airlineCode", airlineCode);
						viproomParams.put("airportCode", airportCode);
						viproomParams.put("viproomId", viproomId);
						viproomParams.put("deviceType", "1");
						//获得VIP信息
						List<Map<String, String>> viproomList =  advertManageService.getDeviceInfoByParams(viproomParams);
						
						if(viproomList != null && viproomList.size() > 0){
							Map<String, String> viproomMap = viproomList.get(0);
							//取出当前设备的IP
							String devIp = viproomMap.get("devIp");
							if(StringUtils.isNotBlank(devIp)){
								try{	
									//启动socketio 给对应的厅推送数据
									String result1 = HttpUtil.httpPost("http://"+devIp+":30000/zkkjweb/device/startServer.do", null, null);
									//设置推送的参数
									List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
									NameValuePair avp = new NameValuePair();
									avp.setName("deviceId1s");
									avp.setValue(deviceId1s);
									nameValuePairList.add(avp);
									//推送数据
									String result2 = HttpUtil.httpPost("http://"+devIp+":30000/zkkjweb/device/deviceReboot.do", nameValuePairList, null);
									//取出推送返回的信息
									Map<String, String> rm = GsonUtil.getGson().fromJson(result2, Map.class);
									resultMap.put("result", rm.get("result"));
									resultMap.put("message", rm.get("message"));
								}catch(Exception e){	
									System.out.println(e.getMessage());
									resultMap.put("result", "2");
									resultMap.put("message", e.getMessage());
								}
							}
						}
					}
				}
				//resultMap.put("result", resultList);
				return resultMap;
	}

	@Override
	public List<Map<String, String>> getDeviceInfoByParams(Map params)
			throws Exception {
		DeviceManageService deviceManageService = (DeviceManageService) getService("deviceManageService");
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>(); 
		resultList = deviceManageService.getDeviceInfoByParams(params);
		return resultList;
	}

	@Override
	public List<Map<String, String>> getDeviceInfoByUUID(String uuid)
			throws Exception {
		DeviceManageService deviceManageService = (DeviceManageService) getService("deviceManageService");
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>(); 
		resultList = deviceManageService.getOneDeviceByDeviceId1(uuid);
		return resultList;
	}

	@Override
	public Map updateDeviceExaminePass(Map params) throws Exception {
		// TODO Auto-generated method stub
		DeviceManageService deviceManageService = (DeviceManageService) getService("deviceManageService");
				
				Map resultMap = new HashMap();
				String ids  = ParamsUtil.nullDeal(params, "ids", "");
				String array[] = ids.split(",");
				if(StringUtils.isNotBlank(ids)){
					for(String str : array){
						deviceManageService.updateDeviceExaminePass(str);
					}
				}
				resultMap.put("result", "1");
				resultMap.put("message", "操作成功");
				return resultMap;
	}

}
