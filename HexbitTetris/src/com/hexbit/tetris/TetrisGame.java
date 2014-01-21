package com.hexbit.tetris;

import com.badlogic.gdx.Game;

public class TetrisGame extends Game {

	@Override
	public void create() {
		setScreen(new GameScreenVector());
		
	}
}
