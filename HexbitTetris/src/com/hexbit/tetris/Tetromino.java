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
	Color mGhostColor;
	
	int[][][] rotationStates;
	
	int currentRotationState;
	
	Point mPos = new Point(0, 0);
	
	public static Random random = new Random();
	
	static final boolean ghost = false;
	
	
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
		mGhostColor = new Color(mColor.r,mColor.g,mColor.b,10);
		mId = t.id;
		currentRotationState = 0;
		//mShape = SHAPES[mId];
		rotationStates = RotationStateList.values()[id].getRotationStates();
		
		mPos = new Point(Dimens.GRID_WIDTH/2, Dimens.GRID_HEIGHT-1);
	}
	final Point DOWN = new Point(0, -1);
	final Point LEFT = new Point(-1, 0);
	final Point RIGHT = new Point(1, 0);
	
	void handleInput(Matrix matrix){
		if(Gdx.input.isKeyPressed(Keys.DOWN)){
			if(matrix.isValid(this, DOWN)){
				move(DOWN);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			if(matrix.isValid(this, LEFT)){
				move(LEFT);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			if(matrix.isValid(this, RIGHT)){
				move(RIGHT);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.UP)){
			rotateClockwise();
		}
		if(Gdx.input.isKeyPressed(Keys.SPACE)){
			hardDrop(matrix);
		}
	}
	void rotateClockwise(){
		if(rotationStates.length > 0){
			if(currentRotationState < rotationStates.length-1){
				currentRotationState++;
			}else{
				currentRotationState = 0;
			}
		}
	}
	
	public void move(Point move){
		mPos.x += move.x;
		mPos.y += move.y;
	}

	void hardDrop(Matrix matrix){
		Point movement = new Point(0, 0);
		while(matrix.isValid(this,movement)){
			movement.y--;
		}
		move(movement);		
	}
	Point getHardDropPos(Matrix matrix){
		Point movement = new Point(0, 0);
		while(matrix.isValid(this,movement)){
			movement.y--;
		}
		Point pos = mPos;
		pos.sub(movement);
		return pos;
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
	public void draw(ShapeRenderer sr, Matrix matrix){
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
		if(ghost){
			sr.setColor(mGhostColor);
			Point pos = getHardDropPos(matrix);
			
			for(int i = 0 ; i < shape.length; i++){
				for (int j = 0; j < shape[i].length; j++) {
					if(shape[i][j] != 0){
						sr.rect(pos.x*Dimens.CELL+Dimens.CELL*j,pos.y*Dimens.CELL+ Dimens.CELL*i, Dimens.CELL, Dimens.CELL);
					}
				}
			}
		}
		sr.end();
		
		
	}
	
	int[][] getShape(){
		return rotationStates[currentRotationState];
	}
	
	Point getShapeOrigin(){
		Point origin = new Point(-1, 0);
		int[][] shape = getShape();
		
		for(int y = 0 ; y < shape.length; y++){
			boolean hasCell = false;
			for (int x = 0; x < shape[y].length; x++) {
				if(shape[y][x] == 1){
					hasCell = true;
					if(x < origin.x || origin.x == -1){
						origin.x = x;
					}
				}
			}
			if(hasCell && y > origin.y){
				origin.y = y;
			}
		}
		origin.y = shape.length - origin.y;
		
		return origin;
	}
	
	Point getPos(){
		return mPos;
	}
	void setPos(Point pos){
		mPos = pos;
	}
}
