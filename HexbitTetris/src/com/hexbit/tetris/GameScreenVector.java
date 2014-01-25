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
	
	final float PLAY_SPEED  = 0.3f;
	final float INPUT_SPEED = 0.05f;
	
	//game variables
	Matrix matrix;
	TetrominoStack tetrominoStack = new TetrominoStack();
	Tetromino currentTetromino;
	
	float count = 0;
	float count2 = 0;
	
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
		if(count > PLAY_SPEED){
//			if(matrix.fits(new Point(0, -1),currentTetromino)){//TODO check if fits
//				currentTetromino.move(0, -1);
//				System.out.println("fits");
//			}else{
//				System.out.println("not fits");
//			}
			System.out.println(matrix.isValid(currentTetromino));
			count = 0;
		}
		count += delta;
		
		if(count2 > INPUT_SPEED){
			checkInput();
			count2 = 0;
		}
		count2 += delta;
		
		currentTetromino.draw(shapeRenderer,matrix);
		matrix.draw(shapeRenderer);
		
		spriteBatch.begin();
		font.draw(spriteBatch, "FPS: "+Gdx.graphics.getFramesPerSecond(),0 , Gdx.graphics.getHeight()-20);
		font.draw(spriteBatch, currentTetromino.getShapeOrigin().toString(),0 , Gdx.graphics.getHeight()-40);
		spriteBatch.end();
		
	}
	
	private void checkInput() {
		if(Gdx.input.isKeyPressed(Keys.R)){
			reset();
		}
		currentTetromino.handleInput(matrix);
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
}
