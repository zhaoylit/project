package com.zkkj.backend.common.util;


public class ExecuteShellUtil {
    //关闭监听9092端口的进程
	private static String KILL_BY_PORT = "lsof -i:#prot#|cut -c 9-15|uniq| xargs kill -9";
	//关闭包含特定字符串的进程，例如关闭service是"with-dependencies.jar"
	private static String KILL_BY_NAME = "ps aux | grep #name# | cut -c 9-15 | xargs kill -9";
	
	public static boolean stopProcessByPort(int port) {
		try{
		   String[] cmds = {KILL_BY_PORT.replace("#prot#", String.valueOf(port))};  
		   Process pro = Runtime.getRuntime().exec(cmds);  
		   return pro.waitFor()==0;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	public static boolean stopProcessByName(String name) {
		try{
		   String[] cmds = {"/bin/sh","-c",KILL_BY_NAME.replace("#name#", name)};  
		   Process pro = Runtime.getRuntime().exec(cmds);  
		   return pro.waitFor()==0;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	 public static void main(String[] args) throws Exception{  
	        
		 ExecuteShellUtil.stopProcessByPort(9092);
		 
		 
	    }  
}
