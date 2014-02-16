package com.hexbit.tetris.modes.threed;

import com.hexbit.tetris.Tetromino;
import com.hexbit.tetris.TetrominoStack;

public class TetrominoStack3D extends TetrominoStack{

	@Override
	public Tetromino getNewTetromino() {
		return new Tetromino3D();
	}

}
