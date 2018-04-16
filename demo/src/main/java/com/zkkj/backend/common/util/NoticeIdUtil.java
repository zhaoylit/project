package com.zkkj.backend.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoticeIdUtil {
	public NoticeIdUtil() {
	}

	public static String generateNoticeId(String prefix) {
		
		StringBuilder sb = new StringBuilder(prefix);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		String date = sdf.format(new Date()); 
		Double random = Math.random()*(1000);
		
		return sb.append(date).append(new Integer(random.intValue()).toString()).toString();
	}

}