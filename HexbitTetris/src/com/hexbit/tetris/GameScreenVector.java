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
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(currentTetromino.getINPUT_PROCESSOR());
		inputMultiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(inputMultiplexer);
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

	float count = 0;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		if (count > PLAY_SPEED) {
			if (matrix.isValid(currentTetromino, Tetromino.DOWN)) {
				currentTetromino.move(Tetromino.DOWN);
			} else {
				currentTetromino.addToMatrix(matrix);
			}

			count = 0;
		}
		count += delta;

		matrix.checkClears(shapeRenderer);

		if (matrix.isGameOver()) {
			reset();
		}

		if (currentTetromino.done) {
			currentTetromino = tetrominoStack.getNextPiece();
		}

		currentTetromino.update(matrix);

		// render -------------------------------

		// sr.translate(DESKTOP_MARGIN, DESKTOP_MARGIN,0);
		matrix.draw(shapeRenderer);
		currentTetromino.draw(shapeRenderer, matrix);
		// sr.translate(-DESKTOP_MARGIN,-DESKTOP_MARGIN, 0);

		spriteBatch.begin();
		font.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0,
				Gdx.graphics.getHeight() - 20);
		font.draw(spriteBatch, currentTetromino.getOrigin().toString(), 0,
				Gdx.graphics.getHeight() - 40);
		spriteBatch.end();
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (Keys.DOWN == keycode) {
			currentTetromino.setDownHeld(false);
		} else if (keycode == Keys.LEFT) {
			currentTetromino.setLeftHeld(false);
		} else if (keycode == Keys.RIGHT) {
			currentTetromino.setRightHeld(false);
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

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT) {
			currentTetromino.setLeftHeld(true);
		} else if (keycode == Keys.RIGHT) {
			currentTetromino.setRightHeld(true);
		} 
		 if (keycode == Keys.SPACE) {
			currentTetromino.hardDrop(matrix);
		} if (keycode == Keys.UP) {
			currentTetromino.rotateClockwise(matrix);
		}else if (keycode == Keys.DOWN) {
			currentTetromino.setDownHeld(true);
		}
		return false;
	}

}
