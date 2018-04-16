package com.zyl.export.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Description: </p>
 * @author zyl
 * @date 2018年1月30日
 * @version 1.0
 */
public class DateUtil {
    /**
       * 将短时间格式字符串转换为时间 yyyy-MM-dd 
       * 
       * @param strDate
       * @return
     * @throws ParseException 
       */
    public static Date strToDate1(String strDate) throws ParseException {
       SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
       Date strtodate = formatter.parse(strDate);
       return strtodate;
    }
    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd 
     * 
     * @param strDate
     * @return
   * @throws ParseException 
     */
  public static Date strToDate(String strDate) throws ParseException {
     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
     Date strtodate = formatter.parse(strDate);
     return strtodate;
  }
}
