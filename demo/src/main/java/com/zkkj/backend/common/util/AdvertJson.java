package com.zkkj.backend.common.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONArray;

public class AdvertJson {
	private static String getWebsiteDatetime(String webUrl){
        try {
            URL url = new URL(webUrl);// 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            long ld = uc.getDate();// 读取网站日期时间
            Date date = new Date(ld);// 转换为标准时间对象
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 输出北京时间
            return sdf.format(date);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
	public static void main(String[] args) {
//		System.out.println(getWebsiteDatetime("http://www.baidu.com"));
		System.out.println(getWebsiteDatetime("http://www.taobao.com"));
//		System.out.println(getWebsiteDatetime("http://www.360.cn"));
		List<Map> list = new ArrayList<Map>();
		Map map1 = new HashMap();
		Map aMap = new HashMap();
		List<Map> countlist = new ArrayList<Map>();
		Map countMap1 = new HashMap();
		List<Map> countMap1List = new ArrayList<Map>();
		Map countMap1ListMap1 = new HashMap();
		countMap1ListMap1.put("path", "http://192.168.9.2:30000/advert/2323232333424.jpg");
		countMap1ListMap1.put("duration", "10");
		countMap1List.add(countMap1ListMap1);
		Map countMap1ListMap2 = new HashMap();
		countMap1ListMap2.put("path", "http://192.168.9.2:30000/advert/2323232333424.jpg");
		countMap1ListMap2.put("duration", "5");
		countMap1List.add(countMap1ListMap2);
		countMap1.put("frequency", "2");
		countMap1.put("list", countMap1List);
		List<Map> countMap2List = new ArrayList<Map>();
		Map countMap2ListMap1 = new HashMap();
		countMap2ListMap1.put("path", "http://192.168.9.2:30000/advert/2323232333424.jpg");
		countMap2ListMap1.put("duration", "5");
		countMap2List.add(countMap2ListMap1);
		Map countMap2ListMap2 = new HashMap();
		countMap2ListMap2.put("path", "http://192.168.9.2:30000/advert/2323232333424.jpg");
		countMap2ListMap2.put("duration", "5");
		countMap2List.add(countMap2ListMap2);
		Map countMap2ListMap3 = new HashMap();
		countMap2ListMap3.put("path", "http://192.168.9.2:30000/advert/2323232333424.jpg");
		countMap2ListMap3.put("duration", "5");
		countMap2List.add(countMap2ListMap3);
		countMap1.put("frequency", "2");
		countMap1.put("list", countMap2List);
		countlist.add(countMap1);
		aMap.put("advertiserName", "百事可乐");
		aMap.put("count", countlist);
		Map bMap = new HashMap();
		bMap.put("path", "http://192.168.9.2:30000/advert/2323232333424.jpg");
		bMap.put("duration", 15);
		map1.put("A",aMap);
		map1.put("B",bMap);
		list.add(map1);
//		System.out.println(JSONArray.fromObject(list).toString());
	}
}
