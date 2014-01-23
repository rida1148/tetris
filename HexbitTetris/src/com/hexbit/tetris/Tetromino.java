package com.hexbit.tetris;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Tetromino {
	int mId;
	Color mColor;
	
	//int[][] mShape;
	
	int[][][] rotationStates;
	
	int currentRotationState;
	
	Point mPos = new Point(0, 0);
	
	public static Random random = new Random();
	
	enum Type{ 
		I(0,Color.CYAN), 
		O(1,Color.YELLOW), 
		T(2,new Color(230, 230, 259, 0)), 
		S(3,Color.GREEN), 
		Z(4,Color.RED), 
		J(5,Color.BLUE), 
		L(6,Color.ORANGE); 
		
		int id;
		Color color;
		
		private Type(int i, Color c) {
			id = i;
			color = c;
		}
	};
	
	public Tetromino() {
		int id = random.nextInt(7);
		Type t = Type.values()[id];
		mColor = t.color;
		mId = t.id;
		currentRotationState = 0;
		//mShape = SHAPES[mId];
		rotationStates = RotationStateList.values()[id].getRotationStates();
		
		mPos = new Point(Dimens.GRID_WIDTH/2, Dimens.GRID_HEIGHT-1);
	}
	/*
	void rotate(){
		int center;
		
		int[][] shape = getShape();
		
		int width = shape[0].length;
		
		if(width == 3){
			center = 1;
		}else{
			center = 2;
		}
		for(int y = 0 ; y < shape.length; y++){
			for (int x = 0; x < shape[y].length; x++) {
				if(shape[y][x] != 0 && y != center && x != center){
					if(x > width/2){
						if(x < width){
							shape[y][x]++;
							//x++;
						}
					}else{
						if(x > 0){
							shape[y][x]--;
						//	x--;
						}
					}
					if(y < width/2){
						if(y < width){
							shape[y][x]++;
							//y++;
						}
					}else{
						if(y > 0){
							//y--;
						}
					}
				}
			}
		}
	}
	*/
	
	void handleInput(Matrix matrix){
		if(Gdx.input.isKeyPressed(Keys.DOWN)){
			if(matrix.fits(new Point(0, -1), this)){
				move(0,-1);
			}
		}
		
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			if(matrix.fits(new Point(-1, 0), this)){
				move(-1,0);
			}
			
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			if(matrix.fits(new Point(1, 0), this)){
				move(1,0);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.UP)){
			
			if(rotationStates.length > 0){
				if(currentRotationState < rotationStates.length-1){
					currentRotationState++;
				}else{
					currentRotationState = 0;
				}
			}
		}
		if(Gdx.input.isKeyPressed(Keys.SPACE)){
			hardDrop(matrix);
		}
	}
	
	void move(Point p){
		move(p.x,p.y);
	}
	
	public void move(int x, int y){
		mPos.x += x;
		mPos.y += y;
	}
	//FIXME hard drop freezes
	void hardDrop(Matrix matrix){
		Point movement = new Point(0, 0);
		while(matrix.fits(movement, this)){
			movement.y++;
		}
		move(movement);		
	}
	
	public void print() {
		int[][] shape = getShape();
		for(int y = 0 ; y < shape.length; y++){
			for (int x = 0; x < shape[y].length; x++) {
				if(shape[y][x] != 0){
					System.out.print("[]");
				}else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}
	public void draw(ShapeRenderer sr){
		int[][] shape = getShape();
		sr.setColor(mColor);
		sr.begin(ShapeType.Filled);
		for(int i = 0 ; i < shape.length; i++){
			for (int j = 0; j < shape[i].length; j++) {
				if(shape[i][j] != 0){
					sr.rect(mPos.x*Dimens.CELL+Dimens.CELL*j,mPos.y*Dimens.CELL+ Dimens.CELL*i, Dimens.CELL, Dimens.CELL);
				}
			}
		}
		sr.end();
	}
	
//	int[][] getShape(){
//		return RotationStateList.values()[mId].getRotationState(rotationState);
//	}
	int[][] getShape(){
		return rotationStates[currentRotationState];
	}
}
