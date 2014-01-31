package com.hexbit.tetris;

import com.badlogic.gdx.Game;
import com.hexbit.tetris.glow.GlowScreen;
import com.hexbit.tetris.vector.VectorScreen;

public class TetrisGame extends Game {

	@Override
	public void create() {
		//setScreen(new VectorScreen());
		setScreen(new GlowScreen());
	}
}