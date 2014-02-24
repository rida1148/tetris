package com.hexbit.tetris.render2d;

import static com.hexbit.tetris.Dimens.COMPONENT_PAD;
import static com.hexbit.tetris.Dimens.GRIDHPX;
import static com.hexbit.tetris.Dimens.GRIDWPX;
import static com.hexbit.tetris.Dimens.MARGIN;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.hexbit.tetris.Utils;
import com.hexbit.tetris.gui.FinishScreen;

public abstract class SprintGame2D extends TetrisScreen2D {

	public SprintGame2D(String imageFolderName) {
		super(imageFolderName);
	}

	float time = 0;

	final int FINISH_LINE_COUNT = 40;

	@Override
	public void resetGame() {
		super.resetGame();
		time = 0;

	}

	@Override
	public void render(float delta) {
		super.render(delta);
		spriteBatch.begin();
		scoreFont.draw(spriteBatch, Utils.secondsToMins((int) time), MARGIN
				+ (GRIDWPX / 2), MARGIN + GRIDHPX + scoreFontHeight
				+ (COMPONENT_PAD * 2));
		scoreFont.draw(spriteBatch, 40-mMatrix.getLineCount()+"",((int) Gdx.graphics.getWidth()/2-(scoreFont.getSpaceWidth()/2)), MARGIN-scoreFontHeight);
		spriteBatch.end();
		time += delta;
		if (mMatrix.getLineCount() >= FINISH_LINE_COUNT) {
			System.out.println("time: " + time);
			Utils.setScreen(new FinishScreen(this, null, "Finished!"));
			
		}
	}

}
