package com.hexbit.tetris;

import com.hexbit.tetris.render2d.Tetromino2D;

public abstract class TetrominoStack{
	protected Tetromino[] mStack = new Tetromino[10];
	protected Tetromino mHeld = null;
	private String mImageFolderName;
	
	public abstract Tetromino getNextPiece();
	
	public Tetromino peekNextPiece(){
		return mStack[mStack.length-1];
	}
	
	public Tetromino swap(Tetromino tetromino){
		Tetromino tmp = mHeld;
		setHeld(tetromino);
		return tmp;
	}
	
	public Tetromino getHeld(){
		return mHeld;
	}
	public void setHeld(Tetromino h){
		h.reset();
		mHeld = h;
	}

}
