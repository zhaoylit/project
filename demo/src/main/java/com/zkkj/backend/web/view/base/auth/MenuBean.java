package com.zkkj.backend.web.view.base.auth;

import java.util.List;
import java.util.Map;

public class MenuBean {
	
	private Integer id;    //节点的 id，它对于加载远程数据很重要
	private String text;  //要显示的节点文本
	private String state = MENU_STATE_OPEN; //节点状态，'open' 或 'closed'，默认是 'open'
	private boolean checked = false;  //指示节点是否被选中
	private Map<?,?> attributes; //给一个节点添加的自定义属性
	private List<MenuBean> children; //定义了一些子节点的节点数组
	
	public static String MENU_STATE_OPEN ="open";
	public static String MENU_STATE_CLOSED ="closed";
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isChecked() {
		return checked;
	}

	public Map<?, ?> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<?, ?> attributes) {
		this.attributes = attributes;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<MenuBean> getChildren() {
		return children;
	}
	public void setChildren(List<MenuBean> children) {
		this.children = children;
	}

}
