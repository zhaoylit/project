package com.zyl.export.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * <p>Description: </p>
 * @author zyl
 * @date 2018年1月25日
 * @version 1.0
 */
public class HttpClientTest {
   public static void main(String[] args) throws UnsupportedEncodingException {
       String userId = "46019";
       String dateStr = "20170101";
       String fetch_all = "1";
       String instance = "20170101";
       String sql = "select substr(url,instr(url,'://') + 3, instr(replace(url,'://',''),'/') - instr(url,'://'))  AS domain,count(0) as count from info WHERE info_flag = '02' group by substr(url,instr(url,'://') + 3, instr(replace(url,'://',''),'/') - instr(url,'://')) order by count desc;";
       StringBuilder sb = new StringBuilder("http://192.168.224.1:8888/open_db");
       sb.append("?user_id=").append(userId);
       sb.append("&date_s=").append(dateStr);
       sb.append("&fetch_all=").append(fetch_all);
       sb.append("&instance=").append(instance);
       sb.append("&sql=").append(URLEncoder.encode(sql, "UTF-8" ));
       String result = HttpUtil.sendGet(sb.toString());
       FormatUtil.printJson(result);
   }
}
