package com.hexbit.tetris.render2d;

import com.hexbit.tetris.TetrominoStack;

public class TetrominoStack2D extends TetrominoStack{
	private String mImageFolderName;
	
	public Tetromino2D getNextPiece(){ //y u null
		Tetromino2D tmp = (Tetromino2D) mStack[mStack.length-1];
		
		
		for (int i = mStack.length-1; i > 0; i--) {
			mStack[i] = mStack[i-1];
		}	
		mStack[0] = new Tetromino2D(mImageFolderName);
		
		return tmp;
	}
	
	public TetrominoStack2D(String imageFolderName) {
		mImageFolderName = imageFolderName;
		for (int i = 0; i < mStack.length; i++) {
			mStack[i] = new Tetromino2D(mImageFolderName);
		}
	}
	
	public void dispose() {
		for (int i = 0; i < mStack.length; i++) {
			((Tetromino2D) mStack[i]).dispose();
		}
		((Tetromino2D) mHeld).dispose();

	}

}
