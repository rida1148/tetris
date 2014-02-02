package com.hexbit.tetris.glow;

import static com.hexbit.tetris.Dimens.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.hexbit.tetris.Matrix;
import com.hexbit.tetris.Point;
import com.hexbit.tetris.TetrisScreen;
import com.hexbit.tetris.Tetromino;

public class GlowScreen extends TetrisScreen {

	SpriteBatch spriteBatch;
	BitmapFont font;
	
	//TODO delete shape renderer
	ShapeRenderer sr;

	@Override
	public void load() {
		spriteBatch = new SpriteBatch();
		sr = new ShapeRenderer();
		font = new BitmapFont(Gdx.files.internal("font/ubuntu.fnt"));
	}
	
	@Override
	public void resetGame() {
		mMatrix = new GlowMatrix();
		mTetrominoStack = new TetrominoStackGlow();
		mCurrentTetromino = mTetrominoStack.getNextPiece();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		gameLogic(delta);
		// render -------------------------------
		
		//((GlowMatrix) mMatrix).draw(sr);
		
		
		spriteBatch.begin();
		((GlowMatrix) mMatrix).draw(spriteBatch);
		((TetrominoGlow) mCurrentTetromino).draw(spriteBatch, mMatrix);
		((TetrominoGlow) mTetrominoStack.peekNextPiece()).draw(spriteBatch,
				new Point(GRID_WIDTH, GRID_HEIGHT - 2));

		
		//font.draw(spriteBatch, "NEXT", GRID_WIDTH * CELL, GRID_HEIGHT * CELL);
		
		//text("FPS: " + Gdx.graphics.getFramesPerSecond());
		spriteBatch.end();

	}

	void text(String... strings) {
		for (int i = 0; i < strings.length; i++) {
			font.draw(spriteBatch, strings[i], 0, Gdx.graphics.getHeight()
					- (font.getCapHeight() * (i + 1)));
		}
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		font.dispose();
		((GlowMatrix) mMatrix).dispose();
		((TetrominoGlow) mCurrentTetromino).dispose();
		((TetrominoStackGlow) mTetrominoStack).dispose();
	}

}
