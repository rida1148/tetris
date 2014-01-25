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
				matrix[y][x] = Tetromino.random.nextInt(7);
			}
		}
	}

//	public boolean fits(Point movement,Tetromino t) {
//		int[][] coords = t.getShape();
//		//TODO optimise by only checking parts of shape you need to
//		
//		int count = 0;
//		
//		// moving through each part of shape
//		for (int h = 0; h < coords.length; h++) {
//			//System.out.println(i);
//			for (int w = 0; w < coords[h].length; w++) {
//				
//				//System.out.println(j);
//				if(coords[h][w] != 0){
//
//					int y = coords[h][w] + t.mPos.y + movement.y;
//					int x = coords[h][w] + t.mPos.x + movement.x;
//					
//					if(y < 0){
//						System.out.println("y: "+y);
//						return false;
//					}
//					if(x < 0 || x > (GRID_WIDTH-1)){
//						System.out.println("x: "+x);
//						return false;
//					}
//					//System.out.println(matrix[y][x]);
//					if(matrix[y][x] != 0){
//						return false;
//					}
//				}
//				
//			}
//		}
//		//System.out.println("count :"+count);
//		return true;
//	}
	
	boolean isValid(Tetromino t){
		int[][] shape = t.getShape();
		Point origin = t.getShapeOrigin();
		Point pos = t.getPos();
		
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				if(shape[i][j] == 1){
					int xPos = j+pos.x-origin.x;
					if(xPos < 0 || xPos >= GRID_WIDTH){
						System.out.println("x");
						return false;
					}
					int yPos = i+pos.y-origin.y;
					if(yPos < 0){
						System.out.println("y");
						return false;
					}
					if(matrix[yPos][xPos] != 0){
						System.out.println("grid @ : x: "+xPos+" y: "+yPos);
						return false;
					}
				}	
			}
		}
		
		return true;
	}
	
	boolean isValid(Tetromino t,Point move){
		Point cPos = t.getPos();
		t.setPos(new Point(cPos.x+move.x,cPos.y+move.y));
		return isValid(t);
	}
	
	public void draw(ShapeRenderer sr) {
		sr.translate(DESKTOP_MARGIN, DESKTOP_MARGIN,0);
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
		sr.translate(-DESKTOP_MARGIN,-DESKTOP_MARGIN, 0);
		/*
		for (int y = 0; y < Dimens.HEIGHT; y++) {
			sr.line(DESKTOP_MARGIN, CELL*y + DESKTOP_MARGIN, DESKTOP_WIDTH-DESKTOP_MARGIN, CELL*y + DESKTOP_MARGIN);
		}
		for (int x = 0; x < Dimens.WIDTH; x++) {
			sr.line(CELL*x,DESKTOP_MARGIN, CELL*x, CELL*HEIGHT);
		}*/
		sr.end();
	}

}
