package com.hexbit.tetris.modes.gem;

import com.hexbit.tetris.Tetromino;
import com.hexbit.tetris.TetrominoStack;

public class TetrominoStackGem extends TetrominoStack {

	@Override
	public Tetromino getNewTetromino() {
		return new TetrominoGem();
	}

}
