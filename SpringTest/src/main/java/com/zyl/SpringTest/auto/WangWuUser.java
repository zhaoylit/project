package com.zyl.SpringTest.auto;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.zyl.SpringTest.auto.qualifier.Height;
import com.zyl.SpringTest.auto.qualifier.User;

@Component
//@Qualifier("ww")
@User
public class WangWuUser implements IUser{

	@Override
	public void show() {
		// TODO Auto-generated method stub
		System.out.println("-----------------我是王五");
	}

}
