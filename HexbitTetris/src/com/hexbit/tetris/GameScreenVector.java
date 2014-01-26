package com.hexbit.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

//TODO make tetris game class so that all you change is
//the rendering for different versions

public class GameScreenVector implements Screen{
	//constants
	
	final float PLAY_SPEED  = 0.3f;
	final float INPUT_SPEED = 0.05f;
	
	//game variables
	Matrix matrix;
	TetrominoStack tetrominoStack = new TetrominoStack();
	Tetromino currentTetromino;
	
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
	
	float count = 0;
	
	//TODO limit keys being held down
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		if(count > PLAY_SPEED){
			if(matrix.isValid(currentTetromino,Tetromino.DOWN)){
				currentTetromino.move(Tetromino.DOWN);
			}else{
				currentTetromino.addToMatrix(matrix);
			}

			count = 0;
		}
		count += delta;
		
		checkInput();
		
		matrix.checkClears(shapeRenderer);
		
		if(matrix.isGameOver()){
			reset();
		}
		
		if(currentTetromino.done){
			currentTetromino = tetrominoStack.getNextPiece();
		}
		
		//render -------------------------------
		
		//sr.translate(DESKTOP_MARGIN, DESKTOP_MARGIN,0);
		matrix.draw(shapeRenderer);
		currentTetromino.draw(shapeRenderer,matrix);
		//sr.translate(-DESKTOP_MARGIN,-DESKTOP_MARGIN, 0);
		
		spriteBatch.begin();
		font.draw(spriteBatch, "FPS: "+Gdx.graphics.getFramesPerSecond(),0 , Gdx.graphics.getHeight()-20);
		font.draw(spriteBatch, currentTetromino.getOrigin().toString(),0 , Gdx.graphics.getHeight()-40);
		spriteBatch.end();	
	}
	
	private void checkInput() {
		if(Gdx.input.isKeyPressed(Keys.R)){
			reset();
		}else if(Gdx.input.isKeyPressed(Keys.C)){
			Tetromino held = tetrominoStack.getHeld();
			if(held == null){
				tetrominoStack.setHeld(currentTetromino);
				currentTetromino = tetrominoStack.getNextPiece();
			}else{
				tetrominoStack.setHeld(currentTetromino);
				held.resetPos();
				currentTetromino = held;	
			}
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
