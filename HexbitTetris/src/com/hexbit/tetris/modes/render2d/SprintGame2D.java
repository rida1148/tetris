package com.hexbit.tetris.modes.render2d;

import static com.hexbit.tetris.Dimens.COMPONENT_PAD;
import static com.hexbit.tetris.Dimens.GRIDHPX;
import static com.hexbit.tetris.Dimens.GRIDWPX;
import static com.hexbit.tetris.Dimens.MARGIN;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.hexbit.tetris.Utils;
import com.hexbit.tetris.gui.FinishScreen;

public abstract class SprintGame2D extends TetrisScreen2D {

	float time = 0;

	final int FINISH_LINE_COUNT = 1;

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
		spriteBatch.end();
		time += delta;
		if (mMatrix.getLineCount() >= FINISH_LINE_COUNT) {
			System.out.println("time: " + time);
			((Game) Gdx.app.getApplicationListener())
					.setScreen(new FinishScreen(mMatrix.getScore(), mMatrix
							.getLineCount(), (int) time));
		}
	}

}
