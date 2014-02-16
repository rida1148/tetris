package com.hexbit.tetris.modes.glow;

import com.hexbit.tetris.Tetromino;
import com.hexbit.tetris.TetrominoStack;
import com.hexbit.tetris.modes.render2d.TetrominoStack2D;

public class TetrominoStackGlow extends TetrominoStack2D{

	@Override
	public Tetromino getNewTetromino() {
		return new TetrominoGlow();
	}
}
