package com.zyl.SpringTest.auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.zyl.SpringTest.auto.qualifier.Height;
import com.zyl.SpringTest.auto.qualifier.User;

@Component
public class Student {
	@Autowired
//	@Qualifier("ls")   //注入标识符为ls的IUser实现类
	@User
	@Height
	private IUser user;
	public void show(){
		user.show();
	}
	
}
