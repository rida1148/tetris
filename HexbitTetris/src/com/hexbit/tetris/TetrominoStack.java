package com.hexbit.tetris;

public abstract class TetrominoStack {
	Tetromino[] stack = new Tetromino[10];
	Tetromino held = null;
	
	public abstract Tetromino getNewTetromino();
	
	public TetrominoStack() {
		for (int i = 0; i < stack.length; i++) {
			stack[i] = getNewTetromino();
		}
	}
	
	public Tetromino getNextPiece(){
		Tetromino tmp = stack[stack.length-1];
		
		for (int i = stack.length-1; i > 0; i--) {
			stack[i] = stack[i-1];
		}	
		stack[0] = getNewTetromino();
		
		return tmp;
	}
	public Tetromino peekNextPiece(){
		return stack[stack.length-1];
	}
	
	public Tetromino getHeld(){
		return held;
	}
	public void setHeld(Tetromino h){
		held = h;
	}
	
	public void dispose() {
		for (int i = 0; i < stack.length; i++) {
			stack[i].dispose();
		}
		held.dispose();

	}
}
