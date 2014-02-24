package com.hexbit.tetris.render2d;

import static com.hexbit.tetris.Dimens.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.hexbit.tetris.Matrix;

public class Matrix2D extends Matrix{
	Texture[] mCellTextures = new Texture[7];
	
//	public Matrix2D(Texture[] cellTextures) {
//		for (int i = 0; i < cellTextures.length; i++) {
//			mCellTextures[i] = cellTextures[i];
//		}
//	}
	
	public Matrix2D(String imageFolderName){
		for (int i = 0; i < mCellTextures.length; i++) {
			mCellTextures[i] = new Texture(Gdx.files.internal(imageFolderName+"/"+i+".png"));
		}
	}
	
	public void draw(SpriteBatch sb) {
		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix[y].length; x++) {
				if(matrix[y][x] != 0){				
					sb.draw(mCellTextures[matrix[y][x]-1], x*CELL, y*CELL,CELL, CELL);
				}
			}
		}
	}
	
	public void draw(SpriteBatch sb,ShapeRenderer shapeRenderer,int xOffset,int yOffset) {	
		GraphicUtils.drawBox(shapeRenderer, new Rectangle(xOffset, yOffset, CELL*GRID_WIDTH, CELL*GRID_HEIGHT));
		
		sb.begin();
		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix[y].length; x++) {
				if(matrix[y][x] != 0){				
					sb.draw(mCellTextures[matrix[y][x]-1], x*CELL + xOffset, y*CELL + yOffset,CELL, CELL);
				}
			}
		}
		sb.end();
		
	}
	
	public void dispose() {
		for (int i = 0; i < mCellTextures.length; i++) {
			mCellTextures[i].dispose();
		}

	}

}
