package com.hexbit.tetris;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.hexbit.tetris.gui.MainMenu;
import com.hexbit.tetris.modes.gem.GemScreen;

public class Tetris extends Game {

	@Override
	public void create() {
		
		Texture.setEnforcePotImages(false);
		//setScreen(new VectorScreen());
		//setScreen(new GlowScreen());
		//setScreen(new Screen3D());
		//setScreen(new MainMenu());
		setScreen(new GemScreen());
	}
}
