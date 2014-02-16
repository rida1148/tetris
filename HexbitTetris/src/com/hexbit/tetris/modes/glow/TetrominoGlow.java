package com.hexbit.tetris.modes.glow;

import com.hexbit.tetris.modes.render2d.Tetromino2D;

public class TetrominoGlow extends Tetromino2D{
	
	public final static String NAME = "glow";
	
	@Override
	protected String getImageFolderName() {
		return NAME;
	}
}
