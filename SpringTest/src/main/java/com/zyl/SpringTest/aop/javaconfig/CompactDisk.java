package com.zyl.SpringTest.aop.javaconfig;

import org.springframework.stereotype.Component;

@Component
public class CompactDisk  implements IMediaPlayer{
	private int trackNumber = 10;

	@Override
	public void play(int trackNumber) {
		// TODO Auto-generated method stub
		System.out.println();
	}}
