package com.hexbit.tetris.glow;

import static com.hexbit.tetris.Dimens.CELL;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hexbit.tetris.Matrix;
import com.hexbit.tetris.Point;
import com.hexbit.tetris.Tetromino;

public class TetrominoGlow extends Tetromino {
	
	final float GHOST_ALPHA = 0.45f;
	
	private Texture mCellTexture;

	// for image grathics
	public TetrominoGlow(int id) {
		super(id);
		mCellTexture = new Texture(Gdx.files.internal("neon/"+id+".png"));
	}

	public TetrominoGlow() {
		this(random.nextInt(7));
	}

	// image graphics mode
	public void draw(SpriteBatch sb, Matrix matrix) {
		int[][] shape = getShape();
		for (int i = shape.length - 1; i >= 0; i--) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j] != 0) {
					int x = mPos.x + j;
					int y = mPos.y + i;
					sb.draw(mCellTexture, x * CELL, y * CELL, CELL, CELL);
				}
			}
		}
		if (ghost) {
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			com.badlogic.gdx.graphics.Color c = sb.getColor();
            sb.setColor(c.r, c.g, c.b, GHOST_ALPHA);
			Point pos = getHardDropPos(matrix);
			// TODO set alpha
			for (int i = 0; i < shape.length; i++) {
				for (int j = 0; j < shape[i].length; j++) {
					if (shape[i][j] != 0) {
						int x = pos.x + j;
						int y = pos.y + i;

						sb.draw(mCellTexture, x * CELL, y * CELL, CELL, CELL);
					}
				}
			}
			Gdx.gl.glDisable(GL10.GL_BLEND);
			 sb.setColor(c.r, c.g, c.b, 1f);
		}
	}

	public void draw(SpriteBatch sb, Point pos) {
		int[][] shape = getShape();
		for (int i = shape.length - 1; i >= 0; i--) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j] != 0) {
					int x = pos.x + j;
					int y = pos.y + i;
					sb.draw(mCellTexture, x * CELL, y * CELL, CELL, CELL);
				}
			}
		}
	}

	public void dispose() {
		mCellTexture.dispose();

	}
}
