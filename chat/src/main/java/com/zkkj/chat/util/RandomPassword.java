package com.zkkj.chat.util;

import java.util.Random;

public class RandomPassword {
	public static void main(String[] args) {

		String password = makeRandomPassword();
		System.out.println(password);
	}
	
	public static String makeRandomPassword(){
		char charr[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		int a=8;
		for (int x = 0; x < a; ++x) {
			sb.append(charr[r.nextInt(charr.length)]);
		}
		String passWordString ="ZXHD"+sb;
		return passWordString.toString();
	}

}

