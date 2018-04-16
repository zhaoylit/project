package com.web.util;

import java.io.File;

public class CssParserUtil {
	public static void main(String[] args) {
		
		String cssPath = "C:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp4/wtpwebapps/project/src/main/webapp/resources/easyui/themes/icon.css";
		File file = new File(cssPath);
		System.out.println(file.getName());
	}
}
