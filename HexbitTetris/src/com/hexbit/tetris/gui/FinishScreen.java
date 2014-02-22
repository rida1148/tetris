package com.hexbit.tetris.gui;

import com.hexbit.tetris.TetrisScreen;
import com.hexbit.tetris.Utils;

//screen to show after a game is finshed
//eg show a summary like stats, score time etc

public class FinishScreen extends TextScreen {

	public FinishScreen(int score, int lines, int timeInSecs) {
		super("GAME OVER", new String[] { "Score: " + score, "Lines: " + lines,
				"Time: ", Utils.secondsToMins(timeInSecs) });
	}

	public FinishScreen(TetrisScreen screen) {
		super("GAME OVER", new String[] {
				"Score: " + screen.getMatrix().getScore(),
				"Lines: " + screen.getMatrix().getLineCount(),
				"Time: " + Utils.secondsToMins((int) screen.getGameTime()) });
	}

}
