package com.zj.api.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
public class SFTPUtil {
	/** 
	* 连接sftp服务器 
	* @param host 主机 
	* @param port 端口 
	* @param username 用户名 
	* @param password 密码 
	* @return 
	*/  
	public ChannelSftp connect(String host, int port, String username,  
	String password) {  
	ChannelSftp sftp = null;  
	try {  
		JSch jsch = new JSch();  
		jsch.getSession(username, host, port);  
		Session sshSession = jsch.getSession(username, host, port); 
		System.out.println("**********************Session created.");  
		sshSession.setPassword(password);  
		Properties sshConfig = new Properties();  
		sshConfig.put("StrictHostKeyChecking", "no");
		sshSession.setConfig(sshConfig);  
		sshSession.setTimeout(300000);
		sshSession.connect();  
		System.out.println("**********************Session connected.");  
		System.out.println("**********************Opening Channel.");  
		Channel channel = sshSession.openChannel("sftp");  
		channel.connect(30000);  
		sftp = (ChannelSftp) channel;  
		System.out.println("**********************Connected to " + host + ".");  
	} catch (Exception e) {  
	  
	}  
	return sftp;  
	}  
	  
	/** 
	* 上传文件 
	* @param directory 上传的目录 
	* @param uploadFile 要上传的文件 
	* @param sftp 
	*/  
	public boolean upload(String directory, String uploadFile, ChannelSftp sftp) {  
	try {  
//		checkDir(directory,sftp); 
		String fileName = uploadFile.substring(uploadFile.lastIndexOf("/") + 1);
		 URL url = new URL(uploadFile);
		 HttpURLConnection connection=(HttpURLConnection)url.openConnection();
		 sftp.put(connection.getInputStream(),fileName); 
		 return true;
	} catch (Exception e) {  
		e.printStackTrace();  
	}  
		return false;
	}  
	  
	/** 
	* 下载文件 
	* @param directory 下载目录 
	* @param downloadFile 下载的文件 
	* @param saveFile 存在本地的路径 
	* @param sftp 
	*/  
	public void download(String directory, String downloadFile,String saveFile, ChannelSftp sftp) {  
	try {  
	sftp.cd(directory);  
	File file=new File(saveFile);  
	sftp.get(downloadFile, new FileOutputStream(file));  
	} catch (Exception e) {  
	e.printStackTrace();  
	}  
	}  
	  
	/** 
	* 删除文件 
	* @param directory 要删除文件所在目录 
	* @param deleteFile 要删除的文件 
	* @param sftp 
	*/  
	public void delete(String directory, String deleteFile, ChannelSftp sftp) {  
	try {  
	sftp.cd(directory);  
	sftp.rm(deleteFile);  
	} catch (Exception e) {  
	e.printStackTrace();  
	}  
	}  
	
	/** 
	* 列出目录下的文件 
	* @param directory 要列出的目录 
	* @param sftp 
	* @return 
	* @throws SftpException 
	*/  
	public Vector listFiles(String directory, ChannelSftp sftp) throws SftpException{  
	return sftp.ls(directory);  
	}  
	//判断目录是否存在不存在创建目录
    /*public static void checkDir(String directory, ChannelSftp sftp){
    	try{ 
        	int i = directory.indexOf(Constant.webName)+Constant.webName.length();
        	StringBuilder from = new StringBuilder(directory.substring(0,i));
        	String remain = directory.substring(i+1);
        	String[] array = remain.split("/");
        	if(array != null && array.length > 0){
        		for(String str : array){
        			from.append("/").append(str);
        			System.out.println(sftp+"***************************"+from.toString());
        		    try{ 
        		        Vector content = sftp.ls(from.toString()); 
        		        if(content == null) { 
        	    			sftp.mkdir(from.toString());
        		        } 
        		    } catch (SftpException e) { 
            			sftp.mkdir(from.toString());
        		    } 
        		}
        	}
        	sftp.cd(directory);
        } catch (SftpException e) { 
        	System.out.println(e.getMessage());
        } 
    }*/
	public static void main(String[] args) {  
	SFTPUtil sf = new SFTPUtil();   
	String host = "192.168.200.251";  
	int port = 22;  
	String username = "root";  
	String password = "-zkkj999999-";  
	String directory = "/usr/local/apache-tomcat-7.0.72/webapps/zkkjweb/";  
	String uploadFile = "http://img002.21cnimg.com/photos/album/20160326/m600/B920004B5414AE4C7D6F2BAB2966491E.jpeg";  
//	String downloadFile = "upload.txt";  
//	String saveFile = "D:\\tmp\\download.txt";  
//	String deleteFile = "delete.txt";  
	ChannelSftp sftp=sf.connect(host, 22, username, password);  
//	sf.download(directory, downloadFile, saveFile, sftp);  
//	sf.delete(directory, deleteFile, sftp);  
//	checkDir(directory,sftp);
	sf.upload(directory, uploadFile, sftp);  
	/*try{
		sftp.cd(directory);      
		System.out.println("finished");
	}catch(Exception e){  
		e.printStackTrace();  
	}   */
	}  
}
