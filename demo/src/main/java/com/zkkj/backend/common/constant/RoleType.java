package com.zkkj.backend.common.constant;

public enum RoleType {

	DF("df","机场地服"),
	ADMIN("admin","管理员");
	
	public final String name;
	public final String zhName;
	
	private RoleType(String name,String zhName){
		this.name = name;
		this.zhName = zhName;	
	}
}
