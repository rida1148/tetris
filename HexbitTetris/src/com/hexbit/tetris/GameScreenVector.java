package com.hexbit.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
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
	ShapeRenderer shapeRenderer;
	
	@Override
	public void render(float delta) {
		if(count > SPEED){
			
			if(true){//TODO check if fits
			}
			count = 0;
		}
		count += delta;
		Gdx.gl.glClearColor(0, 0, 0f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		currentTetromino.draw(shapeRenderer);
		checkInput();
	}
	
	private void checkInput() {
		if(Gdx.input.isKeyPressed(Keys.DOWN)){
			currentTetromino.move(0,-1);
		}
		if(Gdx.input.isKeyPressed(Keys.UP)){
			currentTetromino.move(0,1);
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			currentTetromino.move(-1,0);
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			currentTetromino.move(1,0);
		}
	}

	@Override
	public void show() {
		shapeRenderer = new ShapeRenderer();
		
		reset();
	}
	
	void reset(){
		tetrominoStack = new TetrominoStack();
		currentTetromino = tetrominoStack.getNextPiece();
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
	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

}
