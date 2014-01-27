package com.hexbit.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

//TODO make tetris game class so that all you change is
//the rendering for different versions

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
		if (gameTimer.isFinished()) {
			if (matrix.isValid(currentTetromino, Tetromino.DOWN)) {
				currentTetromino.move(Tetromino.DOWN);
			} else {
				currentTetromino.addToMatrix(matrix);
			}

			gameTimer.reset();
		}
		gameTimer.tick(delta);

		matrix.checkClears(shapeRenderer);

		if (matrix.isGameOver()) {
			reset();
		}

		if (currentTetromino.done) {
			currentTetromino = tetrominoStack.getNextPiece();
		}

		currentTetromino.update(matrix,delta);

		// render -------------------------------

		// sr.translate(DESKTOP_MARGIN, DESKTOP_MARGIN,0);
		matrix.draw(shapeRenderer);
		currentTetromino.draw(shapeRenderer, matrix);
		// sr.translate(-DESKTOP_MARGIN,-DESKTOP_MARGIN, 0);

		spriteBatch.begin();
		text("FPS: " + Gdx.graphics.getFramesPerSecond(),
				currentTetromino.getOrigin().toString(),
				currentTetromino.getRightHeldTimer().getCurrentTime()+"",
				currentTetromino.getLeftHeldTimer().getCurrentTime()+"");
		spriteBatch.end();
	}
	
	void text(String...strings){
		for(int i = 0 ; i < strings.length; i++){
			font.draw(spriteBatch, strings[i], 0,
					Gdx.graphics.getHeight() - (font.getCapHeight()*(i+1)));
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
	
	@Override
	public boolean keyDown(int keycode) {	
		
		if (keycode == Keys.LEFT) {
			currentTetromino.setLeftHeld(true);
			currentTetromino.startLeftHeldTimer();
		} else if (keycode == Keys.RIGHT) {
			currentTetromino.setRightHeld(true);
			currentTetromino.startRightHeldTimer();
		}
		if (keycode == Keys.SPACE) {
			currentTetromino.hardDrop(matrix);
		}
		if (keycode == Keys.UP) {
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
			currentTetromino.setLeftHeld(false);
			currentTetromino.getLeftHeldTimer().reset();
			currentTetromino.getLeftHeldTimer().pause();
		} else if (keycode == Keys.RIGHT) {
			currentTetromino.setRightHeld(false);
			currentTetromino.getRightHeldTimer().reset();
			currentTetromino.getRightHeldTimer().pause();
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
