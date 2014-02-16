package com.hexbit.tetris.modes.render2d;

import static com.hexbit.tetris.Dimens.CELL;
import static com.hexbit.tetris.Dimens.GRID_HEIGHT;
import static com.hexbit.tetris.Dimens.GRID_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class GraphicUtils {
	public static void drawBox(ShapeRenderer shapeRenderer,Rectangle rect){
		//transparent bg
		shapeRenderer.setColor(new Color(.1f, .1f, .1f, .3f));
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.box(rect.x, rect.y, 0, rect.width, rect.height, 0);
		shapeRenderer.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);
		//outline
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.box(rect.x, rect.y, 0, rect.width, rect.height, 0);
		shapeRenderer.end();
	}
	
	
}
