package com.hexbit.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.ACubemap;
import com.hexbit.tetris.gui.MainMenu;
import com.hexbit.tetris.gui.ModeSelector;

public abstract class TetrisScreen implements Screen, InputProcessor {
	public static final int LEVEL_COUNT = 10;
	final int START_LEVEL = 0;
	final float LOCK_DELAY = 0.5f;

	protected Matrix mMatrix;
	protected TetrominoStack mTetrominoStack;
	protected Tetromino mCurrentTetromino;
	protected Timer gameSpeedTimer;
	private Timer lockDelayTimer = new Timer(LOCK_DELAY);

	protected int mLevel;
	boolean alreadySwapped;
	
	int biggestBackToBacks = 0;
	int currentBackToBacks = 0;

	abstract public void load();

	public void resetGame(){
		alreadySwapped = false;
		setLevel(START_LEVEL);
		lockDelayTimer.pause();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		load();
		resetGame();
		// test code, getspeed works
		// for (int i = 0; i <= 10; i++) {
		// System.out.println(mCurrentTetromino.getSpeed(i));
		// }

	}
	
	boolean tetrominoJustAppended = false;

	protected void gameLogic(float delta) {
		if (gameSpeedTimer.isFinished()) {
			if (mMatrix.isValid(mCurrentTetromino, Tetromino.DOWN)) {
				mCurrentTetromino.move(Tetromino.DOWN);
				lockDelayTimer.reset();
				lockDelayTimer.pause();
			} else {
				lockDelayTimer.start();
				if(lockDelayTimer.isFinished()){
					mCurrentTetromino.addToMatrix(mMatrix);
					tetrominoJustAppended = true;
					lockDelayTimer.reset();
				}
			}

			gameSpeedTimer.reset();
		}
		gameSpeedTimer.tick(delta);

		int clears = mMatrix.checkClears(mLevel); 
		
		if(tetrominoJustAppended){
			if(clears > 0){
				currentBackToBacks += clears;
			}else{
				if(currentBackToBacks > biggestBackToBacks){
					biggestBackToBacks = currentBackToBacks;
					System.out.println(biggestBackToBacks);
					//TODO show user 
				}
				if(currentBackToBacks >= 2){
					//TODO award points and show user
				}
				currentBackToBacks = 0;
			}
			tetrominoJustAppended = false;
		}

		if (mMatrix.isGameOver()) {
			resetGame();
		}

		if (mCurrentTetromino.isDone()) {
			mCurrentTetromino = mTetrominoStack.getNextPiece();
			alreadySwapped = false;
		}

		mCurrentTetromino.update(mMatrix, delta);
		
		lockDelayTimer.tick(delta);
	}

	void setLevel(int level) {
		mLevel = level;
		gameSpeedTimer = new Timer(mCurrentTetromino.getSpeed(mLevel));
	}

	boolean leftAllowed = false;
	boolean madeLeftMove = false;

	boolean rightAllowed = false;
	boolean madeRightMove = false;

	@Override
	public boolean keyDown(int keycode) {
		// freaky boolean logic!
		if (keycode == Keys.LEFT) {
			lockDelayTimer.reset();
			mCurrentTetromino.startLeftHeldTimer();
			if (!madeLeftMove) {
				leftAllowed = true;
			}
			// single move
			if (mMatrix.isValid(mCurrentTetromino, Tetromino.LEFT)
					&& leftAllowed && !madeLeftMove) {
				mCurrentTetromino.move(Tetromino.LEFT);
				leftAllowed = false;
				madeLeftMove = true;

			}
		} else if (keycode == Keys.RIGHT) {
			lockDelayTimer.reset();
			mCurrentTetromino.startRightHeldTimer();
			if (!madeRightMove) {
				rightAllowed = true;
			}
			// single move
			if (mMatrix.isValid(mCurrentTetromino, Tetromino.RIGHT)
					&& rightAllowed && !madeRightMove) {
				mCurrentTetromino.move(Tetromino.RIGHT);
				rightAllowed = false;
				madeRightMove = true;
			}
		} //HARD DROP
		else if (keycode == Keys.SPACE) {
			mCurrentTetromino.hardDrop(mMatrix);
			tetrominoJustAppended = true;
		}//ROTATION 
		else if (keycode == Keys.UP) {
			if(mCurrentTetromino.rotateClockwise(mMatrix)){
				lockDelayTimer.reset();
			}
		} else if (keycode == Keys.DOWN) {
			mCurrentTetromino.setDownHeld(true);
		} else if (keycode == Keys.ESCAPE) {
			((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
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
		} // hold
		else if (Keys.C == keycode) {
			if (!alreadySwapped) {
				Tetromino held = mTetrominoStack.getHeld();
				if (held == null) {
					mTetrominoStack.setHeld(mCurrentTetromino);
					mCurrentTetromino = mTetrominoStack.getNextPiece();
				} else {
					mCurrentTetromino = mTetrominoStack.swap(mCurrentTetromino);
					alreadySwapped = true;
				}
			}
		} else if (keycode == Keys.R) {
			resetGame();
		}
		return false;
	}

}
