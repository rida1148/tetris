package com.hexbit.tetris;

import static com.hexbit.tetris.Dimens.GRID_HEIGHT;
import static com.hexbit.tetris.Dimens.GRID_WIDTH;

import java.util.ArrayList;

import com.hexbit.tetris.modes.vector.TetrominoVector;

public abstract class Matrix {
	
	protected int[][] matrix = new int[GRID_HEIGHT][GRID_WIDTH];
	
	private int score = 0;
	
	void debugLoad(){
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < matrix[y].length - 1; x++) {
				if(Tetromino.random.nextInt(2) == 1){
					matrix[y][x] = Tetromino.random.nextInt(7);
				}
			}
		}
	}
	
	boolean isValidOld(final Tetromino t){
		final int[][] shape = t.getShape();
		Point pos = t.getPos();

		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				if(shape[i][j] == 1){
					int xPos = j+pos.x;

					if(xPos < 0 || xPos >= GRID_WIDTH){
						return false;
					}
					int yPos = i+pos.y;
					if(yPos < 0 || yPos >= GRID_HEIGHT){
						return false;
					}
					if(matrix[yPos][xPos] != 0){
						return false;
					}
				}	
			}
		}

		return true;
	}

	boolean isValidOld(Tetromino tetromino,Point move){
		Point cPos = tetromino.getPos();
		Tetromino tmp = new TetrominoVector(tetromino.getId());
		tmp.setCurrentRotationState(tetromino.getCurrentRotationState());
		tmp.setPos(new Point(cPos.x+move.x,cPos.y+move.y));
		return isValidOld(tmp);
	}
	
	public boolean isValid(final Tetromino t){
		final int[][] shape = t.getShape();
		Point pos = t.getPos();
		
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				if(shape[i][j] == 1){
					int xPos = j+pos.x;
					
					if(xPos < 0 || xPos >= GRID_WIDTH){
						return false;
					}
					int yPos = i+pos.y;
					if(yPos < 0 || yPos >= GRID_HEIGHT){
						return false;
					}
					if(matrix[yPos][xPos] != 0){
						return false;
					}
				}	
			}
		}
		
		return true;
	}
	
	public boolean isValid(int[][] shape,Point pos){
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				if(shape[i][j] == 1){
					int xPos = j+pos.x;
					
					if(xPos < 0 || xPos >= GRID_WIDTH){
						return false;
					}
					int yPos = i+pos.y;
					if(yPos < 0 || yPos >= GRID_HEIGHT){
						return false;
					}
					if(matrix[yPos][xPos] != 0){
						return false;
					}
				}	
			}
		}
		
		return true;
	}
	
	public boolean isValid(int[][] shape,Point cPos,Point move){
		Point newPos = new Point(cPos.x+move.x,cPos.y+move.y);
		return isValid(shape,newPos);
	}
	
	
	public boolean isGameOver(){
		for (int i = 1; i < 3; i++) {
			for(int j = 0; j < matrix[0].length; j++){
				if(matrix[GRID_HEIGHT-i][j] != 0){
					return true;
				}
			}
		}
		return false;
	}
	public void checkClears(){
		//find all lines that are full
		ArrayList<Integer> fullLines = new ArrayList<Integer>();
		for(int i = matrix.length-1 ; i >= 0; i --){
			boolean isFull = true;
			for (int j = 0; j < matrix[0].length; j++) {
				if(matrix[i][j] == 0){
					isFull = false;
					break;
				}
			}
			if(isFull){
				fullLines.add(i);
			}
		}
		for (int i = 0; i < fullLines.size(); i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[fullLines.get(i)][j] = 0;
			}
		}
		//draw(sr);
		
		for (int i = 0; i < fullLines.size(); i++) {
			for (int y = fullLines.get(i); y < matrix.length-1; y++) {
				shiftGridDownTo(y);
			}
			setScore(getScore() + 1);
			System.out.println("cleared @ "+fullLines.get(i));
		}
		//starting from the top, see if they are next to each other
		//for optimise and squeeze those groups as one

	}
	void shiftGridDownTo(int overwritePos){
		for(int i = 0 ; i < matrix[0].length; i++){
			matrix[overwritePos][i] = matrix[overwritePos+1][i];
		}
	}
	
	public void setCell(Point pos,int num){
		matrix[pos.y][pos.x] = num;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
