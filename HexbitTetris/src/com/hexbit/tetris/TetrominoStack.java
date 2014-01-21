package com.hexbit.tetris;

public class TetrominoStack {
	Tetromino[] stack = new Tetromino[10];
	Tetromino held = new Tetromino();
	
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
}
