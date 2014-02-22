package com.hexbit.tetris;

public class Utils {
	public static String secondsToMins(int secs){
		return ((int)secs/60) + ":" +String.format("%02d", ((int)secs%60)); 
	}
}
