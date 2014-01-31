package com.hexbit.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public abstract class TetrisScreen implements Screen, InputProcessor {
	protected final float PLAY_SPEED = 0.3f;

	protected Matrix mMatrix;
	protected TetrominoStack mTetrominoStack;
	protected Tetromino mCurrentTetromino;

	protected Timer gameTimer = new Timer(PLAY_SPEED);
	
	abstract public void load();
	
	abstract public void resetGame();
	
	@Override
	public void show() {
		load();
		resetGame();
		Gdx.input.setInputProcessor(this);
	}
	
	protected void gameLogic(float delta){
		if (gameTimer.isFinished()) {
			if (mMatrix.isValidOld(mCurrentTetromino, Tetromino.DOWN)) {
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
			mCurrentTetromino.startLeftHeldTimer();
			if (!madeLeftMove) {
				leftAllowed = true;
			}
			if (mMatrix.isValidOld(mCurrentTetromino, Tetromino.LEFT) && leftAllowed
					&& !madeLeftMove) {
				mCurrentTetromino.move(Tetromino.LEFT);
				leftAllowed = false;
				madeLeftMove = true;
			}
		} else if (keycode == Keys.RIGHT) {
			mCurrentTetromino.startRightHeldTimer();
			if (!madeRightMove) {
				rightAllowed = true;
			}
			if (mMatrix.isValidOld(mCurrentTetromino, Tetromino.RIGHT)
					&& rightAllowed && !madeRightMove) {
				mCurrentTetromino.move(Tetromino.RIGHT);
				rightAllowed = false;
				madeRightMove = true;
			}
		} else if (keycode == Keys.SPACE) {
			mCurrentTetromino.hardDrop(mMatrix);
		} else if (keycode == Keys.UP) {
			mCurrentTetromino.rotateClockwise(mMatrix);
		} else if (keycode == Keys.DOWN) {
			mCurrentTetromino.setDownHeld(true);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (Keys.DOWN == keycode) {
			mCurrentTetromino.setDownHeld(false);
		} else if (keycode == Keys.LEFT) {
			mCurrentTetromino.getLeftHeldTimer().reset();
			mCurrentTetromino.getLeftHeldTimer().pause();
			madeLeftMove = false;
		} else if (keycode == Keys.RIGHT) {
			mCurrentTetromino.getRightHeldTimer().reset();
			mCurrentTetromino.getRightHeldTimer().pause();
			madeRightMove = false;
		}
		// ---------
		else if (Keys.C == keycode) {
			Tetromino held = mTetrominoStack.getHeld();
			if (held == null) {
				mTetrominoStack.setHeld(mCurrentTetromino);
				mCurrentTetromino = mTetrominoStack.getNextPiece();
			} else {
				mTetrominoStack.setHeld(mCurrentTetromino);
				held.resetPos();
				mCurrentTetromino = held;
			}
		} else if (keycode == Keys.R) {
			resetGame();
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

}
