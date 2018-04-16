package com.zyl.export.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Description: </p>
 * @author zyl
 * @date 2018年1月26日
 * @version 1.0
 */
public class StringUtils {
    public static String escapeExprSpecialWord(String keyword) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(keyword)) {
            String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|",};
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }    
    public static boolean isContainsChinese(String str)
    {
        Pattern pat = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find())    {
            flg = true;
        }
        return flg;
    }
}
