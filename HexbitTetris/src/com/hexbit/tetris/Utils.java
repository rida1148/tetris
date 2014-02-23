package com.hexbit.tetris;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class Utils {
	public static String secondsToMins(int secs){
		return ((int)secs/60) + ":" +String.format("%02d", ((int)secs%60)); 
	}
	public static void setScreen(Screen screen){
		((Game) Gdx.app.getApplicationListener()).setScreen(screen);
	}
}
