package com.zkkj.backend.common.socketio;

public class SocketConstant {
	public final static String RECEIVE_PROGRAM = "RECEIVE_PROGRAM";//接收到节目单
	public final static String RESOURCE_ALREADY_EXIST = "RESOURCE_ALREADY_EXIST";//存在的资源数量，也就是下载成功的数量
	public final static String AN_FILE_DOWNLOAD_SUCCESS = "AN_FILE_DOWNLOAD_SUCCESS";//资源下载成功
	public final static String AN_FILE_DOWNLOAD_FAIL = "AN_FILE_DOWNLOAD_FAIL";//资源下载失败
	public final static String RESOURCE_DOWNLOAD_ALL = "RESOURCE_DOWNLOAD_ALL";//资源全部下载完成
	public final static String RECEIVE_APK = "RECEIVE_APK";//接收到推送的apk
	public final static String APK_DOWNLOAD = "APK_DOWNLOAD";//apk已下载
	public final static String APK_INSTALL = "APK_INSTALL";//apk已安装
	public final static String REBOOT = "REBOOT";//设备重启
	public final static String AD_SYNCH = "AD_SYNCH";//推送时间同步
	
}
