package com.zkkj.backend.common.constant;

public interface IConstant {

	public static final String RESULT_CODE_SUCCESS = "SUCCESS";//服务端返回的成功结果
	public static final String RESULT_CODE_FAIL = "FAIL";//服务端返回的成功结果
	public static final String DEFAULT_CODE_SUCCESS = "1";//服务端返回的成功结果
	public static final String DEFAULT_CODE_FAIL = "0";//服务端返回的成功结果
	public static final String DEFAULT_MESSAGE_SUCCESS = "操作成功";//服务端返回的成功结果
	public static final String DEFAULT_MESSAGE_FAIL = "操作失败";//服务端返回的成功结果
	public static final String MENU_NODE_TYPE_SIBLING = "sibling"; // 兄弟菜单
	public static final String MENU_NODE_TYPE_CHILD = "child"; // 子菜单
	public static final String MENU_ADDNODE_MESSAGE_SUCCESS = "添加菜单成功！"; 
	public static final String MENU_ADDNODE_MESSAGE_FAILURE = "添加菜单失败！"; 
	public static final String MENU_EDITNODE_MESSAGE_SUCCESS = "编辑菜单成功！"; 
	public static final String MENU_EDITNODE_MESSAGE_FAILURE = "编辑菜单失败！"; 
	public static final String MENU_REMOVENODE_MESSAGE_SUCCESS = "删除菜单成功！"; 
	public static final String MENU_REMOVENODE_MESSAGE_FAILURE = "删除菜单失败！"; 
	public final static String VALIDATE_CODE = "validateCode"; //Session保存的验证码信息
	public final static String MENU_VISIBLE = "1";
	public final static String MENU_UNVISIBLE = "0";
	public final static String ADMIN_ACCOUNT = "admin";
	public static final String STATUS_PARM_IS_EMPTY="参数不能为空";
	public static final String STATUS_OK="上传成功";
	public static final String STATUS_EXECPTION ="上传失败";
	public static final String SESSION_ACCOUNT_INFO ="SESSION_ACCOUNT_INFO";
	public static final String PASSWORD_NOT_CORRECT ="旧密码不正确";
	public static final String SESSION_NOT_LOGIN ="您还未登录或会话超时，请重新登录";
	public static final String SESSION_REURL ="reUrl";
	public static final String CITY_NOT_ONLY ="填写的城市不唯一，请重新输入";
	public static final String CITY_NOT_EXIST ="填写的城市不存在，请重新输入";
	
	public static final String NOTICE_PARAM_EMPTY = "发送失败，缺少参数";
	public static final String NOTICE_SEND_FAIL = "发送失败";
	public static final String AUTH_NOT_ALLOW = "操作失败，您没有该权限！";
	public static final String PUASH_NO_CLIENT = "发送失败，没有设备处于连接状态！";
	public static final String PUASH_EORROR = "发送失败，请确认网络连接正常！";
	
	
}
