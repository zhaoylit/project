package com.zyl.SpringTest.aop.javaconfig;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TrackCounter {
	private Map<Integer, Integer> trackCounts = new HashMap<Integer, Integer>();
	@Pointcut("execution(* om.zyl.SpringTest.aop.javaconfig.IMediaPlayer.play(int)) && args(trackNumber)")
	public void trackPlayed(int trackNumber){}
	
	@Before("trackPlayed(trackNumber)")
	public void countTrack(int trackNumber){
		int currentCount = getPlayCount(trackNumber); 
		trackCounts.put(trackNumber, currentCount + 1);
	}
	
	public int getPlayCount(int trackNumber){
		return trackCounts.containsKey(trackNumber) ? trackCounts.get(trackNumber) : 0;
	}

	public Map<Integer, Integer> getTrackCounts() {
		return trackCounts;
	}

	public void setTrackCounts(Map<Integer, Integer> trackCounts) {
		this.trackCounts = trackCounts;
	}
	
} 	
