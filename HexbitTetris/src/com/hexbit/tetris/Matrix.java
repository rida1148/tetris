package com.hexbit.tetris;

import javax.swing.text.DefaultEditorKit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import static com.hexbit.tetris.Dimens.*;

public class Matrix {
	int[][] matrix = new int[GRID_HEIGHT][GRID_WIDTH];

	public boolean fits(Point movement, Tetromino t) {
		int[][] coords = t.getShape();

		// moving through each part of shape
		for (int i = 0; i < coords.length - 1; i++) {
			for (int j = 0; j < coords[i].length - 1; j++) {
				if(coords[i][j] != 0){
					
					
					int y = coords[i][j] + t.mPos.y + movement.y;
					int x = coords[i][j] + t.mPos.x + movement.x;
					
					if(y < 0){
						return false;
					}
					if(x < 0 || x > GRID_WIDTH){
						return false;
					}

					if(matrix[i][j] != 0){
						return false;
					}
				}
				
			}
		}
		return true;
	}

	public void draw(ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		for (int y = 0; y < matrix.length - 1; y++) {
			for (int x = 0; x < matrix[y].length - 1; x++) {
				if(matrix[y][x] != 0){				
					sr.setColor(Tetromino.Type.values()[matrix[y][x]-1].color);
					sr.rect(y*Dimens.CELL, x*Dimens.CELL, Dimens.CELL, Dimens.CELL);
					
				}
			}
		}
		sr.end();
		sr.setColor(Color.WHITE);
		sr.begin(ShapeType.Line);
		sr.translate(DESKTOP_MARGIN, DESKTOP_MARGIN,0);
		for (int y = 0; y < Dimens.GRID_HEIGHT; y++) {
			sr.line(0, CELL*y , DESKTOP_WIDTH-DESKTOP_MARGIN, CELL*y);
		}
		for (int x = 0; x < Dimens.GRID_WIDTH; x++) {
			sr.line(CELL*x,0, CELL*x, CELL*GRID_HEIGHT);
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
