package com.hexbit.tetris;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Tetromino {
	int mId;
	Color mColor;
	int[][] mCoords;
	
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
	
	int[][][] tetrominos = {
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
		mCoords = tetrominos[mId];
	}
	
	void rotate(){
		int center;
		int width = mCoords[0].length;
		
		if(width == 3){
			center = 1;
		}else{
			center = 2;
		}
		for(int y = 0 ; y < mCoords.length; y++){
			for (int x = 0; x < mCoords[y].length; x++) {
				if(mCoords[y][x] != 0 && y != center && x != center){
					if(x > width/2){
						if(x < width){
							mCoords[y][x]++;
							//x++;
						}
					}else{
						if(x > 0){
							mCoords[y][x]--;
						//	x--;
						}
					}
					if(y < width/2){
						if(y < width){
							mCoords[y][x]++;
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
		for(int y = 0 ; y < mCoords.length; y++){
			for (int x = 0; x < mCoords[y].length; x++) {
				if(mCoords[y][x] != 0){
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
		for(int i = 0 ; i < mCoords.length; i++){
			for (int j = 0; j < mCoords[i].length; j++) {
				if(mCoords[i][j] != 0){
					sr.rect(mPos.x*Dimens.CELL+Dimens.CELL*j,mPos.y*Dimens.CELL+ Dimens.CELL*i, Dimens.CELL, Dimens.CELL);
				}
			}
		}
		sr.end();
		
	}
}
