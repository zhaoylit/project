package com.zyl.SpringTest.auto;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.zyl.SpringTest.auto.qualifier.User;

@Component
//@Qualifier("zs")
@User
public class ZhangSanUser implements IUser{

	@Override
	public void show() {
		// TODO Auto-generated method stub
		System.out.println("----------------我是用户张三");
	}

}
