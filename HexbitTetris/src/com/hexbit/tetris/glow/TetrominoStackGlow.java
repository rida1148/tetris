package com.hexbit.tetris.glow;

import com.hexbit.tetris.Tetromino;
import com.hexbit.tetris.TetrominoStack;

public class TetrominoStackGlow extends TetrominoStack{

	@Override
	public Tetromino getNewTetromino() {
		return new TetrominoGlow();
	}

}
