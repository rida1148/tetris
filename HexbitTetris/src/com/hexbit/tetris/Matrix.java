package com.hexbit.tetris;

import com.badlogic.gdx.math.Vector2;

public class Matrix {
	int[][] matrix = new int[22][10];
	
	boolean hasSpace(Vector2 movement,Tetromino t){
		int[][] coords = t.mCoords;
		for(int y = 0 ; y < coords.length; y++){
			for (int x = 0; x < coords[y].length; x++) {
				//if(coords[y][x]){
					
				//}
			}
		}
		return true;
	}
	
}
