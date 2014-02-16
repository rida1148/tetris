package com.hexbit.tetris.modes.render2d;

import static com.hexbit.tetris.Dimens.*;

import java.awt.Font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.hexbit.tetris.Matrix;
import com.hexbit.tetris.Point;
import com.hexbit.tetris.TetrisScreen;
import com.hexbit.tetris.Tetromino;
import com.hexbit.tetris.TetrominoStack;

public abstract class TetrisScreen2D extends TetrisScreen {

	public abstract Tetromino getNewTetromino();

	public abstract Matrix getNewMatrix();

	public abstract TetrominoStack getNewTetrominoStack();

	protected OrthographicCamera camera;
	protected SpriteBatch spriteBatch;
	protected ShapeRenderer shapeRenderer;

	protected BitmapFont gameFont;
	protected BitmapFont scoreFont;

	int gameFontHeight;
	int scoreFontHeight;

	@Override
	public void load() {
		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		gameFont = new BitmapFont(Gdx.files.internal("font/gamefont.fnt"));
		scoreFont = new BitmapFont(Gdx.files.internal("font/score.fnt"));
		
		gameFontHeight = (int) gameFont.getCapHeight();
		scoreFontHeight = (int) scoreFont.getCapHeight();
	}

	@Override
	public void resetGame() {
		mMatrix = getNewMatrix();
		mTetrominoStack = getNewTetrominoStack();
		mCurrentTetromino = mTetrominoStack.getNextPiece();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		gameLogic(delta);

		camera.translate(0, 0);

		camera.update();

		spriteBatch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);

		drawBackground();

		((Matrix2D) mMatrix).draw(spriteBatch, shapeRenderer, MARGIN, MARGIN);

		//score
		GraphicUtils.drawBox(shapeRenderer, new Rectangle(MARGIN, GRIDHPX+MARGIN +COMPONENT_PAD, GRIDWPX, scoreFontHeight+COMPONENT_PAD));
		
		//held bg
		GraphicUtils.drawBox(shapeRenderer, new Rectangle(COMPONENT_PAD, GRIDHPX, MARGIN - (COMPONENT_PAD*2), MARGIN));

		//next bg
		GraphicUtils.drawBox(shapeRenderer, new Rectangle(MARGIN + GRIDWPX + COMPONENT_PAD, GRIDHPX, MARGIN - (COMPONENT_PAD*2), MARGIN));
		

		
		
		// image only rendering so can call begin from here
		// (there's no clashes with shape renderer)
		spriteBatch.begin();
		((Tetromino2D) mCurrentTetromino).draw(spriteBatch, mMatrix, MARGIN,
				MARGIN);

		((Tetromino2D) mTetrominoStack.peekNextPiece()).draw(spriteBatch,
				new Point(GRID_WIDTH * CELL + MARGIN + COMPONENT_PAD,
						(int) (GRID_HEIGHT * CELL - gameFont.getCapHeight())));

		if (mTetrominoStack.getHeld() != null) {
			((Tetromino2D) mTetrominoStack.getHeld()).draw(
					spriteBatch,
					new Point(COMPONENT_PAD, (int) (GRID_HEIGHT * CELL - gameFont
							.getCapHeight())));
		}

		gameFont.draw(spriteBatch, "HELD", COMPONENT_PAD, MARGIN + GRIDHPX);
		gameFont.draw(spriteBatch, "LEVEL", COMPONENT_PAD, MARGIN + (GRIDHPX / 2));
		gameFont.draw(spriteBatch, "NEXT", MARGIN + GRIDWPX + COMPONENT_PAD, MARGIN
				+ GRIDHPX);

		scoreFont.draw(spriteBatch, "" + mMatrix.getScore(), MARGIN, MARGIN
				+ GRIDHPX + scoreFontHeight + (COMPONENT_PAD*2));

		spriteBatch.end();
	}

	protected void drawBackground() {
	}

	protected void drawOnTop() {
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		shapeRenderer.dispose();
		((Matrix2D) mMatrix).dispose();
		((Tetromino2D) mCurrentTetromino).dispose();
		((TetrominoStack2D) mTetrominoStack).dispose();
		gameFont.dispose();
		scoreFont.dispose();

	}
}
