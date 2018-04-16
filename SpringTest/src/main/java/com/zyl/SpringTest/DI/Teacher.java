package com.zyl.SpringTest.DI;

public class Teacher {
	private String name;
	public void show(){
		System.out.println("-----------我是教师");
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
