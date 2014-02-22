package com.hexbit.tetris.modes.render2d;

import static com.hexbit.tetris.Dimens.COMPONENT_PAD;
import static com.hexbit.tetris.Dimens.GRIDHPX;
import static com.hexbit.tetris.Dimens.GRIDWPX;
import static com.hexbit.tetris.Dimens.MARGIN;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.hexbit.tetris.gui.MainMenu;

public abstract class SprintGame2D extends TetrisScreen2D{
	
	float time = 0;
	
	@Override
	public void resetGame() {
		super.resetGame();
		time = 0;
		
	}
	@Override
	public void render(float delta) {
		super.render(delta);
		spriteBatch.begin();
		scoreFont.draw(spriteBatch, secondsToMins((int) time), MARGIN+(GRIDWPX/2), MARGIN
				+ GRIDHPX + scoreFontHeight + (COMPONENT_PAD*2));
		spriteBatch.end();
		time+=delta;
		if(mMatrix.getLineCount() >= 40){
			System.out.println("time: "+time);
			((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());//TODO set this to screen showing yo time
		}
	}
	String secondsToMins(int secs){
		return ((int)secs/60) + ":" +((int)secs%60); 
	}
}
