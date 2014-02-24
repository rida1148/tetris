package com.hexbit.tetris.modes.gem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.hexbit.tetris.Matrix;
import com.hexbit.tetris.Tetromino;
import com.hexbit.tetris.TetrominoStack;
import com.hexbit.tetris.render2d.MarathonScreen2D;

public class MarathonModeGem extends MarathonScreen2D {
	
	public MarathonModeGem(String imageFolderName) {
		super(imageFolderName);
	}

	Texture bg;

	@Override
	public void load() {
		super.load();
		bg = new Texture(Gdx.files.internal("bg2.png"));

	}

	@Override
	protected void drawBackground() {
		spriteBatch.begin();
		spriteBatch.draw(bg, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		spriteBatch.end();
	}

	@Override
	public void dispose() {
		super.dispose();
		bg.dispose();

	}
}
