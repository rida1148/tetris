package com.hexbit.tetris;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Matrix {
	int[][] matrix = new int[Dimens.HEIGHT][Dimens.WIDTH];

	public boolean fits(Point movement, Tetromino t) {
		int[][] coords = t.mShape;

		// moving through each part of shape
		for (int i = 0; i < coords.length - 1; i++) {
			for (int j = 0; j < coords[i].length - 1; j++) {
				int y = coords[i][j] + t.mPos.y + movement.y;
				int x = coords[i][j] + t.mPos.x + movement.x;
				
				if(y <= 0){
					return false;
				}
				if(x >= 0 || x <= Dimens.WIDTH){
					return false;
				}

				if(matrix[y][x] != 0){
					
				}
			}
		}
		return true;
	}

	public void draw(ShapeRenderer sr) {
		for (int y = 0; y < matrix.length - 1; y++) {
			for (int x = 0; x < matrix[y].length - 1; x++) {
				if(matrix[y][x] != 0){
					sr.begin(ShapeType.Filled);
					sr.setColor(Tetromino.Type.values()[matrix[y][x]-1].color);
					sr.rect(y*Dimens.CELL, x*Dimens.CELL, Dimens.CELL, Dimens.CELL);
					sr.end();
				}
			}
		}
	}

}
