package com.zkkj.backend.service.biz.info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zkkj.backend.service.common.BaseService;
import com.zkkj.platform.importinfo.ImportFlightInfoService;
import com.zkkj.program.service.IDeviceService;
import com.zkkj.program.service.impl.DeviceServiceImpl;

@Service
public class ImportInfoServiceImpl extends BaseService implements ImportInfoService {
	IDeviceService deviceService = new DeviceServiceImpl();
	
	@Override
	public boolean upLoadAirlinePlan(String name, String pathStr,
			MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		//ImportFlightInfoService flightInfoService = (ImportFlightInfoService) getService("importFlightInfoService");
		   //创建处理EXCEL
	       ReadExcel readExcel=new ReadExcel();
	       boolean flag = false;
	       int count=0;
	       List<Map<String, Object>> airlineList = readExcel.getExcelInfo(name,pathStr,file);
	       System.out.println(airlineList);
	       for (int i = 0; i < airlineList.size(); i++) {
	    	   Map airlineMap =new HashMap();
	    	   airlineMap.put("airlineCode", airlineList.get(i).get("airlineCode"));
	    	   List<Map> deviceList=deviceService.getAirlineList(airlineMap);
	    	   if(deviceList.size()>0){
	    		   count++;
	    		   continue;
	    	   }
	    	   Map aiMap=new HashMap();
	    	   aiMap.put("airlineCode", airlineList.get(i).get("airlineCode"));
	    	   aiMap.put("airlineName", airlineList.get(i).get("airlineName"));
	    	   deviceService.addAirline(aiMap);
	    	   count++;
	       }
	       if(count>0)flag=true; 
		   //flightInfoService.upLoadAirlinePlan(airlineList);
		   if(flag){
			   return flag;
		   }
		   return flag;
	}
	
	@Override
	public boolean upLoadViproomPlan(String name, String pathStr,
			MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		//ImportFlightInfoService flightInfoService = (ImportFlightInfoService) getService("importFlightInfoService");
		   //创建处理EXCEL
	       ReadExcel readExcel=new ReadExcel();
	       boolean flag = false;
		    int count=0;
	       List<Map<String, Object>> viproomList = readExcel.getExcelInfo(name,pathStr,file);
		   //boolean flag = flightInfoService.upLoadViproomPlan(viproomList);
	       System.out.println(viproomList);
	       //循环每行
	       for (int i = 0; i < viproomList.size(); i++) {
	    	   //根据“，”去打断放到数组
	    	   String[] aiportCodes=viproomList.get(i).get("airportCode").toString().split(",");
	    	   //循环出每个机场编码去查对应的机场信息
	    	   for (int j = 0; j < aiportCodes.length; j++) {
				String aString=aiportCodes[j];
				Map deviceMap=new HashMap();
				deviceMap.put("airportCode", aString);
				//查询机场信息
				List<Map> list=deviceService.getAirportList(deviceMap);
				//System.out.println(list);
				//循环每条机场信息
				for (int k = 0; k < list.size(); k++) {
					Map arMap=new HashMap();
					if(viproomList.get(i).get("viproom").equals(""))break;
					if(!viproomList.get(i).get("viproom").equals("") || viproomList.get(i).get("viproom")!=null )arMap.put("viproomName", viproomList.get(i).get("viproom"));
					if (!list.get(k).get("id").equals("") || list.get(k).get("id") !=null ) arMap.put("apId",list.get(k).get("id")); 
					System.out.println(arMap);
					//调用addViproom
					count=deviceService.addViproom(arMap);
				}
	    	   }
	       }
	       if(count>0)flag=true; 
		   //flightInfoService.upLoadAirlinePlan(airlineList);
		   if(flag){
			   return flag;
		   }
		   return flag;
	}

	@Override
	public boolean upLoadAirport(String name, String pathStr, MultipartFile file)throws Exception {
		// TODO Auto-generated method stub
		//ImportFlightInfoService flightInfoService = (ImportFlightInfoService) getService("importFlightInfoService");
		//创建处理EXCEL
	    ReadExcel readExcel=new ReadExcel();
	    //解析excel，获取航班计划信息集合。
	    List<Map<String, Object>> ariportList = readExcel.getExcelInfo(name,pathStr,file);
	    boolean flag = false;
	    int count=0;
	    System.out.println(ariportList);
	    for (int i = 0; i < ariportList.size(); i++) {
	    	 	Map ariportMap =new HashMap();
	    	 	ariportMap.put("airportCode", ariportList.get(i).get("airportCode"));
	    	   List<Map> ariportList2=deviceService.getAirportList(ariportMap);
	    	   if(ariportList2.size()>0){
	    		   count++;
	    		   continue;
	    	   }
	    	
	    	   String[] airlineCodes=ariportList.get(i).get("airlineCode").toString().split(",");
	    	   for (int j = 0; j < airlineCodes.length; j++) {
	    		   Map arMap=new HashMap();
	    		   if(!ariportList.get(i).get("airportCode").equals("")){
	    			   arMap.put("airportCode", ariportList.get(i).get("airportCode"));
	    		   }
	    		   if(!ariportList.get(i).get("airportName").equals("")){
	    			   arMap.put("airportName", ariportList.get(i).get("airportName"));
	    		   }
		    	   Map aiMap=new HashMap();
		    	   aiMap.put("airlineCode", airlineCodes[j]);
		    	   //根据airlineCode去查航空公司，取出航空公司ID
		    	   List<Map> airlineList = deviceService.getAirlineList(aiMap);
		    	   if(!airlineList.get(0).get("id").equals("")){
		    		   arMap.put("alId",airlineList.get(0).get("id")); 
		    	   }
		    	   System.out.println(arMap);
		    	   count=deviceService.addAirport(arMap);
		    	   count++; 
	    	   }
	       }
	       if(count>0)flag=true; 
		   //flightInfoService.upLoadAirlinePlan(airlineList);
		   if(flag){
			   return flag;
		   }
		   return flag;
	}

	@Override
	public boolean upLoadflight(String name, String pathStr, MultipartFile file)
			throws Exception {
		 ReadExcel readExcel=new ReadExcel();
	       boolean flag = false;
	       int count=0;
	       List<Map<String, Object>> flightList = readExcel.getExcelInfo(name,pathStr,file);
	       System.out.println(flightList);
	       //导入之前先清空表
	       List<Map> fligthLists=deviceService.getFlightList(null);
	       List fligthId=new ArrayList();
	       for (Map map : fligthLists) {
			fligthId.add(map.get("id"));
	       }
	       deviceService.deleteFlight(fligthId);
	       
	       for (int i = 0; i < flightList.size(); i++) {
	    	   /*Map flightMap =new HashMap();
	    	   flightMap.put("arrCity", airlineList.get(i).get("arrCity"));
	    	   List<Map> deviceList=deviceService.getAirlineList(flightMap);
	    	   if(deviceList.size()>0){
	    		   count++;
	    		   continue;
	    	   }*/
	    	   Map fgMap=new HashMap();
	    	   fgMap.put("arrCity", flightList.get(i).get("arrCity"));
	    	   fgMap.put("flightNo", flightList.get(i).get("flightNo"));
	    	   fgMap.put("passCity", flightList.get(i).get("passCity"));
	    	   fgMap.put("depCity", flightList.get(i).get("depCity"));
	    	   fgMap.put("takeoffTime", flightList.get(i).get("takeoffTime"));
	    	   deviceService.addflight(fgMap);
	    	   count++;
	       }
	       if(count>0)flag=true; 
		   //flightInfoService.upLoadAirlinePlan(airlineList);
		   if(flag){
			   return flag;
		   }
		   return flag;
	}

}
