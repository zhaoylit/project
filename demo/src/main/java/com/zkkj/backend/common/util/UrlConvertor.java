package com.zkkj.backend.common.util;


public class UrlConvertor {

	/**
	 * @param arr
	 * @return
	 * @Description: 物理路径转换为http路径(使用原图片域名)
	 */
	public static String convert(String arr){
		
		String result = "";
		if (arr != null && !"".equals(arr)) {
			result = arr.replace(wuLiPath(),returnPath());//将物理路径转换为http路径
		}
		return result;
	}
	
	/**
	 * @param arr
	 * @return
	 * @Description: 物理路径转换为http路径(使用图片样式域名)
	 */
	public static String convertZ(String arr){
		
		String result = "";
		if (arr != null && !"".equals(arr)) {
			result = arr.replace(wuLiPath(),returnPathZ());//将物理路径转换为http路径
		}
		return result;
	}
	
	/**
	 * @return
	 * @Description:获取物理路径
	 */
	public static String wuLiPath(){
		
		String wuli_path = (String)CustomizedPropertyConfigurer.getContextProperty("wuli_path");
		
		return wuli_path;
	}
	
	/**
	 * @return
	 * @Description: 获取http路径
	 */
	public static String returnPath(){
		
		String return_path = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");
		
		return return_path;
	}
	
	/**
	 * @return
	 * @Description: 获取http路径
	 */
	public static String returnPathZ(){
		
		String return_path = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");
		
		return return_path;
	}
	
	/**
	 * @return
	 * @Description: 获取OSS上传路径
	 */
	public static String returnOSSPath(){
		
		String return_path = (String)CustomizedPropertyConfigurer.getContextProperty("file_oss_path");
		
		return return_path;
	}
	
	public static void main(String[] args) {
		System.out.println((String)CustomizedPropertyConfigurer.getContextProperty("file_oss_path"));
	}
	
	public static String getUri_qy(){
		
		String return_path = (String)CustomizedPropertyConfigurer.getContextProperty("uri_qy");
		
		return return_path;
	}
	public static String getPathByResourceType(String type) {
		if("4".equals(type.substring(0, 1))){
			return (String)CustomizedPropertyConfigurer.getContextProperty("file_oss_tour_path");
		}else{
			return (String)CustomizedPropertyConfigurer.getContextProperty("file_oss_path");
		} 
	}
}
