package com.hexbit.tetris.modes.render2d;

import com.hexbit.tetris.TetrominoStack;
import com.hexbit.tetris.modes.glow.TetrominoGlow;

public abstract class TetrominoStack2D extends TetrominoStack{
	
	public void dispose() {
		for (int i = 0; i < mStack.length; i++) {
			((TetrominoGlow) mStack[i]).dispose();
		}
		((Tetromino2D) mHeld).dispose();

	}

}
