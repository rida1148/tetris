package com.hexbit.tetris;

import static com.hexbit.tetris.Dimens.CELL;
import static com.hexbit.tetris.Dimens.GRID_HEIGHT;
import static com.hexbit.tetris.Dimens.GRID_WIDTH;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Matrix {
	int[][] matrix = new int[GRID_HEIGHT][GRID_WIDTH];
	
	public Matrix() {
	//	debugLoad();
	}
	
	void debugLoad(){
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < matrix[y].length - 1; x++) {
				if(Tetromino.random.nextInt(2) == 1){
					matrix[y][x] = Tetromino.random.nextInt(7);
				}
			}
		}
	}
	
	boolean isValid(final Tetromino t){
		final int[][] shape = t.getShape();
		Point origin = t.getOrigin();
		Point pos = t.getPos();
		
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				if(shape[i][j] == 1){
					int xPos = j+pos.x-origin.x;
					
					if(xPos < 0 || xPos >= GRID_WIDTH){
						return false;
					}
					int yPos = i+pos.y-origin.y;
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
	
	boolean isValid(Tetromino tetromino,Point move){
		Point cPos = tetromino.getPos();
		Tetromino tmp = new Tetromino(tetromino.getId());
		tmp.setCurrentRotationState(tetromino.getCurrentRotationState());
		tmp.setPos(new Point(cPos.x+move.x,cPos.y+move.y));
		return isValid(tmp);
	}
	
	public void draw(ShapeRenderer sr) {

		sr.begin(ShapeType.Filled);
		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix[y].length; x++) {
				if(matrix[y][x] != 0){				
					sr.setColor(Tetromino.Type.values()[matrix[y][x]-1].color);
					sr.rect(x*Dimens.CELL, y*Dimens.CELL, Dimens.CELL, Dimens.CELL);
					
				}
			}
		}
		sr.end();
		sr.setColor(Color.WHITE);
		sr.begin(ShapeType.Line);
		//horizontal
		for (int y = 0; y < Dimens.GRID_HEIGHT; y++) {
			sr.line(0, CELL*y ,CELL*GRID_WIDTH, CELL*y);
		}
		//Vertical
		for (int x = 0; x < Dimens.GRID_WIDTH; x++) {
			sr.line(CELL*x, 0 , CELL*x, CELL*GRID_HEIGHT);
		}
		
		sr.end();
	}
	
	boolean isGameOver(){
		for (int i = 1; i < 3; i++) {
			for(int j = 0; j < matrix[0].length; j++){
				if(matrix[GRID_HEIGHT-i][j] != 0){
					return true;
				}
			}
		}
		return false;
	}
	
	//TODO look into how line clears + gravity works
	//FIXME line clearing
	void checkClears(ShapeRenderer sr){
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
		draw(sr);
		
		for (int i = 0; i < fullLines.size(); i++) {
			for (int y = fullLines.get(i); y < matrix.length-1; y++) {
 
				shiftGridDownTo(y);
			}
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
	
	void setCell(Point pos,int num){
		matrix[pos.y][pos.x] = num;
	}
}

//added one tap tetromino move
