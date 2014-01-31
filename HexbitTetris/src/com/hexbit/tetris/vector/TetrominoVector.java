package com.hexbit.tetris.vector;

import static com.hexbit.tetris.Dimens.CELL;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.hexbit.tetris.Dimens;
import com.hexbit.tetris.Matrix;
import com.hexbit.tetris.Point;
import com.hexbit.tetris.Tetromino;

public class TetrominoVector extends Tetromino{
	
	final float GHOST_ALPHA = 0.2f;
	
	private Color mColor;
	private Color mGhostColor;
	
	public TetrominoVector() {
		super();
		mColor = Tetromino.Type.values()[mId].color;
		mGhostColor = new Color(mColor.r, mColor.g, mColor.b, GHOST_ALPHA);
	}
	
	public TetrominoVector(int id){
		super(id);
	}
	
	public void draw(ShapeRenderer sr,Point pos) {
		int[][] shape = getShape();
		sr.setColor(mColor);
		sr.begin(ShapeType.Filled);
		for (int i = shape.length - 1; i >= 0; i--) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j] != 0) {
					int x = pos.x - j;
					int y = pos.y - i;
					sr.rect(x * Dimens.CELL, y * Dimens.CELL, Dimens.CELL,
							Dimens.CELL);
				}
			}
		}
		sr.end();
	}

	public void draw(ShapeRenderer sr, Matrix matrix) {
		int[][] shape = getShape();
		sr.setColor(mColor);
		sr.begin(ShapeType.Filled);
		for (int i = shape.length - 1; i >= 0; i--) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j] != 0) {
					int x = mPos.x + j;
					int y = mPos.y + i;
					sr.rect(x * Dimens.CELL, y * Dimens.CELL, Dimens.CELL,
							Dimens.CELL);
				}
			}
		}
		sr.end();
		if (ghost) {
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			sr.begin(ShapeType.Filled);
			sr.setColor(mGhostColor);
			Point pos = getHardDropPos(matrix);

			for (int i = 0; i < shape.length; i++) {
				for (int j = 0; j < shape[i].length; j++) {
					if (shape[i][j] != 0) {
						int x = pos.x + j;
						int y = pos.y + i;
						sr.rect(x * Dimens.CELL, y * Dimens.CELL, Dimens.CELL,
								Dimens.CELL);
					}
				}
			}
			sr.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);
		}

		// debugDraw(sr);
	}
	
	void debugDraw(ShapeRenderer sr) {
		int[][] shape = getShape();
		sr.begin(ShapeType.Line);
		sr.setColor(Color.RED);
		sr.rect(mPos.x * CELL, mPos.y* CELL, shape[0].length * CELL, shape.length * CELL);
		sr.end();
	}
	
	public Color getColor() {
		return mColor;
	}

	public void setColor(Color color) {
		this.mColor = color;
	}

	public Color getGhostColor() {
		return mGhostColor;
	}

	public void setGhostColor(Color mGhostColor) {
		this.mGhostColor = mGhostColor;
	}
	
}
