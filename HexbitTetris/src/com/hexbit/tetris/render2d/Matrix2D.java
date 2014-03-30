package com.hexbit.tetris.render2d;

import static com.hexbit.tetris.Dimens.CELL;
import static com.hexbit.tetris.Dimens.GRID_HEIGHT;
import static com.hexbit.tetris.Dimens.GRID_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.hexbit.tetris.Matrix;

public class Matrix2D extends Matrix{
	
	Texture[] mCellTextures = new Texture[7];
	private Notifys notifys;
	
	
	public Matrix2D(String imageFolderName){
		for (int i = 0; i < mCellTextures.length; i++) {
			mCellTextures[i] = new Texture(Gdx.files.internal(imageFolderName+"/"+i+".png"));
		}
		notifys = new Notifys();
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
		GraphicUtils.drawBGBox(shapeRenderer, new Rectangle(xOffset, yOffset, CELL*GRID_WIDTH, CELL*GRID_HEIGHT));
		
		sb.begin();
		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix[y].length; x++) {
				if(matrix[y][x] != 0){				
					sb.draw(mCellTextures[matrix[y][x]-1], x*CELL + xOffset, y*CELL + yOffset,CELL, CELL);
				}
			}
		}
		sb.end();
		
		for (int i = 0; i < linesToBeAnimated.size(); i++) {
			linesToBeAnimated.get(i).animate(shapeRenderer,xOffset,yOffset);
		}
		
	}
	

	public int checkClears(ShapeRenderer shapeRenderer , int level) {
		int lines = checkClears(level);
		if(lines == 3){
			notifys.addNotification("Triple!", 3);
		}else if(lines == 4){
			notifys.addNotification("Tetris !", 3);
		}
		return lines;
	}
	
	public void dispose() {
		for (int i = 0; i < mCellTextures.length; i++) {
			mCellTextures[i].dispose();
		}
		notifys.dispose();

	}

}
