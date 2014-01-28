package com.hexbit.tetris;

public class TetrominoStack {
	Tetromino[] stack = new Tetromino[10];
	Tetromino held = null;
	
	public TetrominoStack() {
		for (int i = 0; i < stack.length; i++) {
			stack[i] = new Tetromino();
		}
	}
	
	Tetromino getNextPiece(){
		Tetromino tmp = stack[stack.length-1];
		
		for (int i = stack.length-1; i > 0; i--) {
			stack[i] = stack[i-1];
		}	
		stack[0] = new Tetromino();
		
		return tmp;
	}
	Tetromino peekNextPiece(){
		return stack[stack.length-1];
	}
	
	Tetromino getHeld(){
		return held;
	}
	void setHeld(Tetromino h){
		held = h;
	}
}
