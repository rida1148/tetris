package com.hexbit.tetris.vector;

import com.hexbit.tetris.Tetromino;
import com.hexbit.tetris.TetrominoStack;

public class TetrominoStackVector extends TetrominoStack{

	@Override
	public Tetromino getNewTetromino() {
		return new TetrominoVector();
	}

}
