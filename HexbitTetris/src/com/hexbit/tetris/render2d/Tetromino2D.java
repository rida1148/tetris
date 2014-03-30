package com.hexbit.tetris.render2d;

import static com.hexbit.tetris.Dimens.CELL;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.hexbit.tetris.Matrix;
import com.hexbit.tetris.Point;
import com.hexbit.tetris.Tetromino;
import com.hexbit.tetris.Timer;

public class Tetromino2D extends Tetromino {

	final float GHOST_ALPHA = 0.3f;

	private Texture mCellTexture;

	Timer hardDropAnimation = new Timer(4);

	// alpha start from 0 to 1
	final float hardDropAnimationIntensity = 1;


	private Tetromino2D(int id, String imageFolderName) {
		super(id);
		mCellTexture = new Texture(Gdx.files.internal(imageFolderName + "/"
				+ id + ".png"));
		hardDropAnimation.pause();
	}

	public Tetromino2D(String imageFolderName) {
		this(random.nextInt(7), imageFolderName);
	}

	// the offset is because the grid has a border
	public void draw(SpriteBatch sb, ShapeRenderer shapeRenderer,
			Matrix matrix, int xOffset, int yOffset) {
		int[][] shape = getShape();
		for (int i = shape.length - 1; i >= 0; i--) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j] != 0) {
					int x = mPos.x + j;
					int y = mPos.y + i;
					sb.draw(mCellTexture, x * CELL + xOffset, y * CELL
							+ yOffset, CELL, CELL);
				}
			}
		}
		if (ghost) {
			GraphicUtils.enableAlpha();
			com.badlogic.gdx.graphics.Color c = sb.getColor();
			sb.setColor(c.r, c.g, c.b, GHOST_ALPHA);
			Point pos = getHardDropPos(matrix);
			for (int i = 0; i < shape.length; i++) {
				for (int j = 0; j < shape[i].length; j++) {
					if (shape[i][j] != 0) {
						int x = pos.x + j;
						int y = pos.y + i;

						sb.draw(mCellTexture, x * CELL + xOffset, y * CELL
								+ yOffset, CELL, CELL);
					}
				}
			}

			sb.setColor(c.r, c.g, c.b, 1f);
		}
		//System.out.println(hardDropAnimation.isEnabled());
		if (hardDropAnimation.isEnabled()) {
			System.out.println("HELP!!!");
			shapeRenderer.setColor(new Color(1, 1, 1,
					hardDropAnimationIntensity
							- (hardDropAnimationIntensity * hardDropAnimation
									.getProgressPercent())));
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.rect((float) mPos.x, (float) mPos.y, (float) mPos.x
					* CELL + yOffset, CELL * 20);
			shapeRenderer.end();
		}

		GraphicUtils.disableAlpha();
	}

	public void draw(SpriteBatch sb, Point pos) {
		int[][] shape = getShape();
		for (int i = shape.length - 1; i >= 0; i--) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j] != 0) {
					int x = pos.x + j;
					int y = pos.y + i;
					sb.draw(mCellTexture, x + (CELL * j), y + (CELL * i), CELL,
							CELL);
				}
			}
		}
	}

	@Override
	public void update(Matrix matrix, float delta) {
		super.update(matrix, delta);
		hardDropAnimation.tick(delta);

		// if(hardDropAnimation.isFinished()){
		// hardDropAnimation.reset();
		// hardDropAnimation.pause();
		// }
	}
	
	@Override
	public void hardDrop(Matrix matrix) {
		hardDropAnimation.reset();
		hardDropAnimation.start();
		System.out.println(hardDropAnimation.isEnabled() + " WTF ");
		super.hardDrop(matrix);
	}

	public void dispose() {
		mCellTexture.dispose();
	}
}
