package com.hexbit.tetris;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Tetromino {
	int mId;
	Color mColor;
	int[][] mShape;
	
	Point mPos = new Point(0, 0);
	
	public static Random random = new Random();
	
	enum Type{ 
		I(0,Color.CYAN), //Cyan
		O(1,Color.YELLOW), //yellow
		T(2,new Color(230, 230, 259, 0)), //purple
		S(3,Color.GREEN), //green
		Z(4,Color.RED), //red
		J(5,Color.BLUE), //blue
		L(6,Color.ORANGE);  //orange
		
		int id;
		Color color;
		
		private Type(int i, Color c) {
			id = i;
			color = c;
		}
	};
	
	final int[][][] SHAPES = {
			{
				{0,0,0,0},
				{1,1,1,1},
				{0,0,0,0},
				{0,0,0,0},
			},	
			{
				{1,1},
				{1,1},
			},	
			{
				{0,1,0},
				{1,1,1},
				{0,0,0},
			},
			{
				{0,1,1},
				{1,1,0},
				{0,0,0},
			},
			{
				{1,1,0},
				{0,1,1},
				{0,0,0},
			},
			{
				{1,0,0},
				{1,1,1},
				{0,0,0},
			},
			{
				{0,0,1},
				{1,1,1},
				{0,0,0},
			},
	};
	
	public Tetromino() {
		int id = random.nextInt(7);
		Type t = Type.values()[id];
		mColor = t.color;
		mId = t.id;
		mShape = SHAPES[mId];
		mPos = new Point(Dimens.WIDTH/2, Dimens.HEIGHT-2);
	}
	
	void rotate(){
		int center;
		int width = mShape[0].length;
		
		if(width == 3){
			center = 1;
		}else{
			center = 2;
		}
		for(int y = 0 ; y < mShape.length; y++){
			for (int x = 0; x < mShape[y].length; x++) {
				if(mShape[y][x] != 0 && y != center && x != center){
					if(x > width/2){
						if(x < width){
							mShape[y][x]++;
							//x++;
						}
					}else{
						if(x > 0){
							mShape[y][x]--;
						//	x--;
						}
					}
					if(y < width/2){
						if(y < width){
							mShape[y][x]++;
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
	
	public void move(int x, int y){
		mPos.x += x;
		mPos.y += y;
	}
	
	public void print() {		
		for(int y = 0 ; y < mShape.length; y++){
			for (int x = 0; x < mShape[y].length; x++) {
				if(mShape[y][x] != 0){
					System.out.print("[]");
				}else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}
	public void draw(ShapeRenderer sr){
		sr.setColor(mColor);
		sr.begin(ShapeType.Filled);
		for(int i = 0 ; i < mShape.length; i++){
			for (int j = 0; j < mShape[i].length; j++) {
				if(mShape[i][j] != 0){
					sr.rect(mPos.x*Dimens.CELL+Dimens.CELL*j,mPos.y*Dimens.CELL+ Dimens.CELL*i, Dimens.CELL, Dimens.CELL);
				}
			}
		}
		sr.end();
		
	}
}
