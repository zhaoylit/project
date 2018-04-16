package com.zyl.SpringTest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zyl.SpringTest.DI.User;

@Configuration
public class JavaConfig1 {
	@Bean
	public User getUser(){
		return new User();
	}
}
