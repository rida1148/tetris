package com.hexbit.tetris.glow;

import com.hexbit.tetris.Tetromino;
import com.hexbit.tetris.TetrominoStack;

public class TetrominoStackGlow extends TetrominoStack{

	@Override
	public Tetromino getNewTetromino() {
		return new TetrominoGlow();
	}
	
	public void dispose() {
		for (int i = 0; i < stack.length; i++) {
			((TetrominoGlow) stack[i]).dispose();
		}
		((TetrominoGlow) held).dispose();

	}

}
