package com.zkkj.backend.common.constant;

public enum ResourceType {

	IMAGE("image","图片"),
	VEDIO("vedio","视频");
	
	public final String name;
	public final String zhName;
	
	private ResourceType(String name,String zhName){
		this.name = name;
		this.zhName = zhName;	
	}
}
