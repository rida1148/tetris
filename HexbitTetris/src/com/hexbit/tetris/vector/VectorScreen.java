package com.hexbit.tetris.vector;

import static com.hexbit.tetris.Dimens.CELL;
import static com.hexbit.tetris.Dimens.DESKTOP_MARGIN;
import static com.hexbit.tetris.Dimens.GRID_HEIGHT;
import static com.hexbit.tetris.Dimens.GRID_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hexbit.tetris.Matrix;
import com.hexbit.tetris.Point;
import com.hexbit.tetris.TetrisScreen;
import com.hexbit.tetris.Tetromino;
import com.hexbit.tetris.TetrominoStack;

public class VectorScreen extends TetrisScreen{
	

	SpriteBatch spriteBatch;
	ShapeRenderer shapeRenderer;
	BitmapFont font;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		shapeRenderer.translate(DESKTOP_MARGIN, DESKTOP_MARGIN, 0);
		spriteBatch.getProjectionMatrix().translate(DESKTOP_MARGIN, DESKTOP_MARGIN, 0);

		if (gameTimer.isFinished()) {
			if (mMatrix.isValid(mCurrentTetromino, Tetromino.DOWN)) {
				mCurrentTetromino.move(Tetromino.DOWN);
			} else {
				mCurrentTetromino.addToMatrix(mMatrix);
			}

			gameTimer.reset();
		}
		gameTimer.tick(delta);

		mMatrix.checkClears();

		if (mMatrix.isGameOver()) {
			resetGame();
		}

		if (mCurrentTetromino.isDone()) {
			mCurrentTetromino = mTetrominoStack.getNextPiece();
		}

		mCurrentTetromino.update(mMatrix, delta);

		// render -------------------------------

		mMatrix.draw(shapeRenderer);
		mCurrentTetromino.draw(shapeRenderer, mMatrix);
		mTetrominoStack.peekNextPiece().draw(shapeRenderer,
				new Point(GRID_WIDTH, GRID_HEIGHT - 2));

		
		spriteBatch.begin();
		font.draw(spriteBatch, "NEXT", GRID_WIDTH * CELL, GRID_HEIGHT * CELL);
		text("FPS: " + Gdx.graphics.getFramesPerSecond(), mCurrentTetromino
				.getOrigin().toString());
		spriteBatch.end();
		
		shapeRenderer.translate(-DESKTOP_MARGIN, -DESKTOP_MARGIN, 0);
		spriteBatch.getProjectionMatrix().translate(-DESKTOP_MARGIN, -DESKTOP_MARGIN, 0);
		
	}
	
	void text(String... strings) {
		for (int i = 0; i < strings.length; i++) {
			font.draw(spriteBatch, strings[i], 0, Gdx.graphics.getHeight()
					- (font.getCapHeight() * (i + 1)));
		}
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
		spriteBatch.dispose();
		font.dispose();
		
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetGame() {
		mTetrominoStack = new TetrominoStack();
		mCurrentTetromino = mTetrominoStack.getNextPiece();
		mMatrix = new Matrix();
	}

}
