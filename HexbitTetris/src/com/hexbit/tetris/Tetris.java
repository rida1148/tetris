package com.hexbit.tetris;

import com.badlogic.gdx.Game;
import com.hexbit.tetris.glow.GlowScreen;
import com.hexbit.tetris.gui.MainMenu;
import com.hexbit.tetris.threed.Screen3D;
import com.hexbit.tetris.vector.VectorScreen;

public class Tetris extends Game {

	@Override
	public void create() {
		//setScreen(new VectorScreen());
		//setScreen(new GlowScreen());
		//setScreen(new Screen3D());
		setScreen(new MainMenu());
	}
}
