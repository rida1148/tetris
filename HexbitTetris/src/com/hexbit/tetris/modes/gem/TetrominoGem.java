package com.hexbit.tetris.modes.gem;

import com.hexbit.tetris.modes.render2d.Tetromino2D;

public class TetrominoGem extends Tetromino2D {

	public final static String NAME = "gem";
	
	@Override
	protected String getImageFolderName() {
		return NAME;
	}
}
