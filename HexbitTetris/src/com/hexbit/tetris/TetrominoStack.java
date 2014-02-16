package com.hexbit.tetris;

public abstract class TetrominoStack {
	protected Tetromino[] mStack = new Tetromino[10];
	protected Tetromino mHeld = null;
	
	public abstract Tetromino getNewTetromino();
	
	public TetrominoStack() {
		for (int i = 0; i < mStack.length; i++) {
			mStack[i] = getNewTetromino();
		}
	}
	
	public Tetromino getNextPiece(){
		Tetromino tmp = mStack[mStack.length-1];
		
		for (int i = mStack.length-1; i > 0; i--) {
			mStack[i] = mStack[i-1];
		}	
		mStack[0] = getNewTetromino();
		
		return tmp;
	}
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
