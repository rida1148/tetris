package com.hexbit.tetris;

import static com.hexbit.tetris.Dimens.CELL;
import static com.hexbit.tetris.Dimens.DESKTOP_MARGIN;
import static com.hexbit.tetris.Dimens.GRID_HEIGHT;
import static com.hexbit.tetris.Dimens.GRID_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

//old (obsolete) original class before TetrisScreen was made 

public class GameScreenVector implements Screen, InputProcessor {
	// constants

	final float PLAY_SPEED = 0.3f;

	// game variables
	Matrix matrix;
	TetrominoStack tetrominoStack = new TetrominoStack();
	Tetromino currentTetromino;

	Timer gameTimer = new Timer(PLAY_SPEED);

	// graphics

	SpriteBatch spriteBatch;

	ShapeRenderer shapeRenderer;

	BitmapFont font;

	@Override
	public void show() {
		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont(Gdx.files.internal("ubuntu.fnt"));
		reset();
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
		spriteBatch.dispose();
		font.dispose();
	}

	// reset of game variables
	void reset() {
		tetrominoStack = new TetrominoStack();
		currentTetromino = tetrominoStack.getNextPiece();
		matrix = new Matrix();

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		shapeRenderer.translate(DESKTOP_MARGIN, DESKTOP_MARGIN, 0);
		spriteBatch.getProjectionMatrix().translate(DESKTOP_MARGIN, DESKTOP_MARGIN, 0);

		if (gameTimer.isFinished()) {
			if (matrix.isValid(currentTetromino, Tetromino.DOWN)) {
				currentTetromino.move(Tetromino.DOWN);
			} else {
				currentTetromino.addToMatrix(matrix);
			}

			gameTimer.reset();
		}
		gameTimer.tick(delta);

		matrix.checkClears();

		if (matrix.isGameOver()) {
			reset();
		}

		if (currentTetromino.isDone()) {
			currentTetromino = tetrominoStack.getNextPiece();
		}

		currentTetromino.update(matrix, delta);

		// render -------------------------------

		matrix.draw(shapeRenderer);
		currentTetromino.draw(shapeRenderer, matrix);
		tetrominoStack.peekNextPiece().draw(shapeRenderer,
				new Point(GRID_WIDTH, GRID_HEIGHT - 2));

		
		spriteBatch.begin();
		font.draw(spriteBatch, "NEXT", GRID_WIDTH * CELL, GRID_HEIGHT * CELL);
		text("FPS: " + Gdx.graphics.getFramesPerSecond(), currentTetromino
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
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	boolean leftAllowed = false;
	boolean madeLeftMove = false;

	boolean rightAllowed = false;
	boolean madeRightMove = false;

	@Override
	public boolean keyDown(int keycode) {
		// freaky boolean logic!
		if (keycode == Keys.LEFT) {
			currentTetromino.startLeftHeldTimer();
			if (!madeLeftMove) {
				leftAllowed = true;
			}
			if (matrix.isValid(currentTetromino, Tetromino.LEFT) && leftAllowed
					&& !madeLeftMove) {
				currentTetromino.move(Tetromino.LEFT);
				leftAllowed = false;
				madeLeftMove = true;
			}
		} else if (keycode == Keys.RIGHT) {
			currentTetromino.startRightHeldTimer();
			if (!madeRightMove) {
				rightAllowed = true;
			}
			if (matrix.isValid(currentTetromino, Tetromino.RIGHT)
					&& rightAllowed && !madeRightMove) {
				currentTetromino.move(Tetromino.RIGHT);
				rightAllowed = false;
				madeRightMove = true;
			}
		} else if (keycode == Keys.SPACE) {
			currentTetromino.hardDrop(matrix);
		} else if (keycode == Keys.UP) {
			currentTetromino.rotateClockwise(matrix);
		} else if (keycode == Keys.DOWN) {
			currentTetromino.setDownHeld(true);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (Keys.DOWN == keycode) {
			currentTetromino.setDownHeld(false);
		} else if (keycode == Keys.LEFT) {
			currentTetromino.getLeftHeldTimer().reset();
			currentTetromino.getLeftHeldTimer().pause();
			madeLeftMove = false;
		} else if (keycode == Keys.RIGHT) {
			currentTetromino.getRightHeldTimer().reset();
			currentTetromino.getRightHeldTimer().pause();
			madeRightMove = false;
		}
		// ---------
		else if (Keys.C == keycode) {
			Tetromino held = tetrominoStack.getHeld();
			if (held == null) {
				tetrominoStack.setHeld(currentTetromino);
				currentTetromino = tetrominoStack.getNextPiece();
			} else {
				tetrominoStack.setHeld(currentTetromino);
				held.resetPos();
				currentTetromino = held;
			}
		} else if (keycode == Keys.R) {
			reset();
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

}
