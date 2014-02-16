package com.hexbit.tetris.modes.glow;

import com.hexbit.tetris.modes.render2d.Matrix2D;

public class GlowMatrix extends Matrix2D{

	@Override
	protected String getImageFolderName() {
		return TetrominoGlow.NAME;
	}

}
