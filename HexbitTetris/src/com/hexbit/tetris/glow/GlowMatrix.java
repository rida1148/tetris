package com.hexbit.tetris.glow;

import static com.hexbit.tetris.Dimens.CELL;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hexbit.tetris.Matrix;

public class GlowMatrix extends Matrix{
	Texture[] mCellTextures = new Texture[7];
	
	public GlowMatrix(Texture[] cellTextures) {
		for (int i = 0; i < cellTextures.length; i++) {
			mCellTextures[i] = cellTextures[i];
		}
	}
	
	public GlowMatrix(){
		for (int i = 0; i < mCellTextures.length; i++) {
			mCellTextures[i] = new Texture(Gdx.files.internal("neon/"+i+".png"));
		}
	}
	
	public void draw(SpriteBatch sb) {
		//TODO draw grid here
		
//		//horizontal
//		for (int y = 0; y < Dimens.GRID_HEIGHT; y++) {
//			sr.line(0, CELL*y ,CELL*GRID_WIDTH, CELL*y);
//		}
//		//Vertical
//		for (int x = 0; x < Dimens.GRID_WIDTH; x++) {
//			sr.line(CELL*x, 0 , CELL*x, CELL*GRID_HEIGHT);
//		}
		
		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix[y].length; x++) {
				if(matrix[y][x] != 0){				
					sb.draw(mCellTextures[matrix[y][x]-1], x*CELL, y*CELL,CELL, CELL);
				}
			}
		}

	}
	public void dispose() {
		for (int i = 0; i < mCellTextures.length; i++) {
			mCellTextures[i].dispose();
		}

	}

}
