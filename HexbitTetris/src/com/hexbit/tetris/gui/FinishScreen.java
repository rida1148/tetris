package com.hexbit.tetris.gui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.hexbit.tetris.TetrisScreen;
import com.hexbit.tetris.Utils;

//screen to show after a game is finshed
//eg show a summary like stats, score time etc

public class FinishScreen extends TextScreen {
	/** 
	 * if destination is set to null it will default to home screen 
	 *
	 * */
	public FinishScreen(TetrisScreen screen,Screen destination,String title) {
		super(title,destination, new String[] {
				"Score: " + screen.getMatrix().getScore(),
				"Lines: " + screen.getMatrix().getLineCount(),
				"Time: " + Utils.secondsToMins((int) screen.getGameTime()) });
	}

}
