package com.hexbit.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreenVector implements Screen{
	//constants
	
	final float SPEED  = 0.3f;
	
	//game variables
	Matrix matrix;
	TetrominoStack tetrominoStack = new TetrominoStack();
	Tetromino currentTetromino;
	
	float count = 0;
	
	//graphics
	
	SpriteBatch spriteBatch;
	
	ShapeRenderer shapeRenderer;
	
	BitmapFont font;
	
	@Override
	public void show() {
		spriteBatch  = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont(Gdx.files.internal("ubuntu.fnt"));
		reset();
	}
	
	@Override
	public void dispose() {
		shapeRenderer.dispose();
		spriteBatch.dispose();
		font.dispose();
	}
	//reset of game variables
	void reset(){
		tetrominoStack = new TetrominoStack();
		currentTetromino = tetrominoStack.getNextPiece();
		matrix = new Matrix();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		if(count > SPEED){
			if(matrix.fits(new Point(0, -1), currentTetromino)){//TODO check if fits
				currentTetromino.move(0, -1);
			}
			count = 0;
		}
		count += delta;
		//TODO possible optimise by doing all renders in one call 
		//e.g. shapeRenderer.begin()... do all rendering
		currentTetromino.draw(shapeRenderer);
		matrix.draw(shapeRenderer);
		
		spriteBatch.begin();
		font.draw(spriteBatch, "FPS: "+Gdx.graphics.getFramesPerSecond(),0 , Gdx.graphics.getHeight()-20);
		spriteBatch.end();
		checkInput();
	}
	
	private void checkInput() {
		if(Gdx.input.isKeyPressed(Keys.R)){
			reset();
		}
		currentTetromino.handleInput(matrix);
	}

	

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

}
