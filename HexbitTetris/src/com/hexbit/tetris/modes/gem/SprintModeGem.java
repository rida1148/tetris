package com.hexbit.tetris.modes.gem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.hexbit.tetris.Matrix;
import com.hexbit.tetris.Tetromino;
import com.hexbit.tetris.TetrominoStack;
import com.hexbit.tetris.modes.render2d.SprintGame2D;

public class SprintModeGem extends SprintGame2D{
	Texture bg;
	
	@Override
	public void load() {
		super.load();
		bg = new Texture(Gdx.files.internal("bg2.png"));
		
	}

	@Override
	public Tetromino getNewTetromino() {
		return new TetrominoGem();
	}

	@Override
	public Matrix getNewMatrix() {
		return new MatrixGem();
	}

	@Override
	public TetrominoStack getNewTetrominoStack() {
		return new TetrominoStackGem();
	}
	
	@Override
	protected void drawBackground() {
		spriteBatch.begin();
		spriteBatch.draw(bg, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		spriteBatch.end();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		bg.dispose();
		
	}

}
