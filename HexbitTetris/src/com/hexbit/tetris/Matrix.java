package com.hexbit.tetris;

import static com.hexbit.tetris.Dimens.CELL;
import static com.hexbit.tetris.Dimens.DESKTOP_MARGIN;
import static com.hexbit.tetris.Dimens.DESKTOP_WIDTH;
import static com.hexbit.tetris.Dimens.GRID_HEIGHT;
import static com.hexbit.tetris.Dimens.GRID_WIDTH;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Matrix {
	int[][] matrix = new int[GRID_HEIGHT][GRID_WIDTH];
	
	public Matrix() {
		debugLoad();
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
					if(yPos < 0){
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
		for (int y = 0; y < matrix.length - 1; y++) {
			for (int x = 0; x < matrix[y].length - 1; x++) {
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
		
		/*
		for (int y = 0; y < Dimens.HEIGHT; y++) {
			sr.line(DESKTOP_MARGIN, CELL*y + DESKTOP_MARGIN, DESKTOP_WIDTH-DESKTOP_MARGIN, CELL*y + DESKTOP_MARGIN);
		}
		for (int x = 0; x < Dimens.WIDTH; x++) {
			sr.line(CELL*x,DESKTOP_MARGIN, CELL*x, CELL*HEIGHT);
		}*/
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
	
	void checkClears(){
		//find all lines that are full
		
		//starting from the top, see if they are next to each other
		//for optimise and squeeze those groups as one

	}
	void sqeeze(int fromY,int toY){
		//TODO clear space then redraw for effect
		while(fromY != toY){
			for(int i = 0 ; i < matrix[0].length; i++){
				
			}
			fromY --;
		}
		
	}
	
	void setCell(Point pos,int num){
		matrix[pos.y][pos.x] = num;
	}
}
