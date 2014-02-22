package com.hexbit.tetris.modes.glow;

import com.hexbit.tetris.Matrix;
import com.hexbit.tetris.Tetromino;
import com.hexbit.tetris.TetrominoStack;
import com.hexbit.tetris.modes.render2d.TetrisScreen2D;

public class GlowScreen extends TetrisScreen2D{

	@Override
	public Tetromino getNewTetromino() {
		return new TetrominoGlow();
	}

	@Override
	public Matrix getNewMatrix() {
		return new GlowMatrix();
	}

	@Override
	public TetrominoStack getNewTetrominoStack() {
		return new TetrominoStackGlow();
	}

}
