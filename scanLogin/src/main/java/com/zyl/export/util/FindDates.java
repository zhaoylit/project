package com.zyl.export.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: </p>
 * @author zyl
 * @date 2018年1月26日
 * @version 1.0
 */
public class FindDates {
    public static List<String> findDates(String beginTime, String endTime) throws ParseException  
    {  
         SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
         Date dBegin = sdf.parse(beginTime);  
         Date dEnd = sdf.parse(endTime);  
         List<String> lDate = new ArrayList<>();  
         lDate.add(sdf.format(dBegin));  
         Calendar calBegin = Calendar.getInstance();  
         // 使用给定的 Date 设置此 Calendar 的时间  
         calBegin.setTime(dBegin);  
         Calendar calEnd = Calendar.getInstance();  
         // 使用给定的 Date 设置此 Calendar 的时间  
         calEnd.setTime(dEnd);  
         // 测试此日期是否在指定日期之后  
         while (dEnd.after(calBegin.getTime()))  
         {  
             // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
             calBegin.add(Calendar.DAY_OF_MONTH, 1);  
             lDate.add(sdf.format(calBegin.getTime()));  
         }
        return lDate;  
    }
    public static void main(String[] args) {
        try {
            System.out.println(findDates("20170101","20170930"));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
