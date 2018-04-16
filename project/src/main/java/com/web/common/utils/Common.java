package com.web.common.utils;

import java.io.IOException;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class Common {
//	读取word文件的内容
	public static String getWordContentByPath(String path) {
		try {
			OPCPackage opcPackage = POIXMLDocument.openPackage(path);
	        POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
	        String text2007 = extractor.getText();
	        return text2007;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return null;
	}
	public static void main(String[] args) {
		//查询文档中所有的手机号码
		String text2007 = getWordContentByPath("D://aaa.docx");
		 for(int i=0;i < text2007.length();i++){
        	if("手机：".equals(text2007.substring(i,i + 3))){
	            System.out.print(text2007.substring(i+3,i + 15 > text2007.length() ? text2007.length() : i + 15));
        	}
        }
	}
}
