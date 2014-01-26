package com.hexbit.tetris;

import static com.hexbit.tetris.Dimens.CELL;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/***
 * 
 * @author brett Performance class - renders using primitive shapes (apart from
 *         font)
 */

// TODO (optimise) make tetromino raw class for only data then
// have this extend it

public class Tetromino {

	private int mId;
	private Color mColor;
	private Color mGhostColor;
	private int[][][] rotationStates;
	private int mCurrentRotationState;
	private Point mPos = new Point(0, 0);
	boolean done = false;

	public static Random random = new Random();
	static final boolean ghost = true;
	final float GHOST_ALPHA = 0.2f;
	
	private boolean leftHeld;
	private boolean rightHeld;
	private boolean downHeld;

	public static final Point DOWN = new Point(0, -1);
	public static final Point LEFT = new Point(-1, 0);
	public static final Point RIGHT = new Point(1, 0);

	enum Type {
		I(0, Color.CYAN), O(1, Color.YELLOW), T(2, new Color(230, 230, 259, 0)), S(
				3, Color.GREEN), Z(4, Color.RED), J(5, Color.BLUE), L(6,
				Color.ORANGE);

		int id;
		Color color;

		private Type(int i, Color c) {
			id = i;
			color = c;
		}
	};

	public Tetromino() {
		this(random.nextInt(7));
		// this(2);
	}

	void resetPos() {
		mPos = new Point(Dimens.GRID_WIDTH / 2, Dimens.GRID_HEIGHT-1 );
	}

	public Tetromino(int id) {
		Type t = Type.values()[id];
		mColor = t.color;
		mGhostColor = new Color(mColor.r, mColor.g, mColor.b, GHOST_ALPHA);
		mId = t.id;
		mCurrentRotationState = 0;
		// mShape = SHAPES[mId];
		rotationStates = RotationStateList.values()[id].getRotationStates();
		resetPos();
		// print();
	}

	void rotateClockwise(Matrix matrix) {
		int nextState = mCurrentRotationState;
		if (rotationStates.length > 0) {
			if (mCurrentRotationState < rotationStates.length - 1) {
				nextState++;
			} else {
				nextState = 0;
			}
		}
		Tetromino test = new Tetromino(mId);
		test.setPos(mPos);
		test.setCurrentRotationState(nextState);

		if (matrix.isValid(test)) {
			mCurrentRotationState = nextState;
		}

	}

	public void move(Point move) {
		mPos.x += move.x;
		mPos.y += move.y;
	}

	void hardDrop(Matrix matrix) {
		mPos = getHardDropPos(matrix);
		addToMatrix(matrix);
	}

	Point getHardDropPos(Matrix matrix) {
		Point movement = new Point(0, 0);
		while (matrix.isValid(this, movement)) {
			movement.y--;
		}
		Point pos = new Point(mPos);
		pos.add(movement);
		pos.y++;
		return pos;
	}

	public void print() {
		int[][] shape = getShape();
		for (int y = 0; y < shape.length; y++) {
			for (int x = 0; x < shape[y].length; x++) {
				if (shape[y][x] != 0) {
					System.out.print("[]");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}

	public void draw(ShapeRenderer sr, Matrix matrix) {
		int[][] shape = getShape();
		sr.setColor(mColor);
		sr.begin(ShapeType.Filled);
		for (int i = shape.length - 1; i >= 0; i--) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j] != 0) {
					int x = mPos.x - getOrigin().x + j;
					int y = mPos.y - getOrigin().y + i;
					sr.rect(x * Dimens.CELL, y * Dimens.CELL, Dimens.CELL,
							Dimens.CELL);
				}
			}
		}
		sr.end();
		if (ghost) {
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			sr.begin(ShapeType.Filled);
			sr.setColor(mGhostColor);
			Point pos = getHardDropPos(matrix);

			for (int i = 0; i < shape.length; i++) {
				for (int j = 0; j < shape[i].length; j++) {
					if (shape[i][j] != 0) {
						int x = pos.x - getOrigin().x + j;
						int y = pos.y - getOrigin().y + i;
						sr.rect(x * Dimens.CELL, y * Dimens.CELL, Dimens.CELL,
								Dimens.CELL);
					}
				}
			}
			sr.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);
		}

		// debugDraw(sr);
	}

	// TODO fix debug draw and check with origin finder to see if it works
	void debugDraw(ShapeRenderer sr) {
		int[][] shape = getShape();
		sr.begin(ShapeType.Line);
		sr.setColor(Color.RED);
		sr.rect((mPos.x - getOrigin().x) * CELL, (mPos.y - getOrigin().y)
				* CELL, shape[0].length * CELL, shape.length * CELL);
		sr.end();
	}

	int[][] getShape() {
		// TODO flip shape here for SRS
		int[][] original = rotationStates[mCurrentRotationState];
		int[][] flipped = new int[original.length][original[0].length];
		for (int i = 0; i < original.length; i++) {
			for (int j = 0; j < flipped.length; j++) {
				flipped[flipped.length-i-1][j] = original[i][j];
			}
		}
		return rotationStates[mCurrentRotationState];
	}

	// TODO fix origin finding code
	Point getOrigin() {
		Point origin = new Point(-1, -1);
		int[][] shape = getShape();

		for (int y = 0; y < shape.length; y++) {
			boolean hasCell = false;
			for (int x = 0; x < shape[y].length; x++) {
				if (shape[y][x] == 1) {
					hasCell = true;
					if (x < origin.x || origin.x == -1) {
						origin.x = x;
					}
				}
			}
			if (hasCell && y > origin.y) {
				origin.y = y;
			}
		}
		origin.y = shape.length - origin.y - 1; // -1 because .length counts
												// starting at 1

		return origin;
	}

	// TODO fix tetrominos diapering when on far right
	void addToMatrix(Matrix matrix) {
		int[][] shape = getShape();
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j] == 1) {
					int x = getPos().x + j - getOrigin().x;
					int y = getPos().y + i - getOrigin().y;
					matrix.setCell(new Point(x, y), mId + 1);
				}
			}
		}
		done = true;
	}
	
	void update(Matrix matrix){
		if (isRightHeld()
				&& matrix.isValid(this, Tetromino.RIGHT)) {
			move(Tetromino.RIGHT);
		} else if (isLeftHeld()
				&& matrix.isValid(this, Tetromino.LEFT)) {
			move(Tetromino.LEFT);
		}
		if(downHeld && matrix.isValid(this, DOWN)){
			move(DOWN);
		}
	}

	// ------------------------------------------------------------------------

	Point getPos() {
		return mPos;
	}

	void setPos(Point pos) {
		mPos = pos;
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		this.mId = id;
	}

	public Color getColor() {
		return mColor;
	}

	public void setColor(Color color) {
		this.mColor = color;
	}

	public Color getGhostColor() {
		return mGhostColor;
	}

	public void setGhostColor(Color mGhostColor) {
		this.mGhostColor = mGhostColor;
	}

	public int getCurrentRotationState() {
		return mCurrentRotationState;
	}

	public void setCurrentRotationState(int currentRotationState) {
		this.mCurrentRotationState = currentRotationState;
	}

	// ------------------
	public InputProcessor INPUT_PROCESSOR = new InputProcessor() {
		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			/*
			
			*/
			return false;
		}

		@Override
		public boolean keyTyped(char character) {

			return false;
		}

		@Override
		public boolean keyDown(int keycode) {
			
			
			
			return false;
		}
	};
	public InputProcessor getINPUT_PROCESSOR() {
		return INPUT_PROCESSOR;
	}

	public boolean isRightHeld() {
		return rightHeld;
	}

	public void setRightHeld(boolean rightHeld) {
		this.rightHeld = rightHeld;
	}

	public boolean isLeftHeld() {
		return leftHeld;
	}

	public void setLeftHeld(boolean leftHeld) {
		this.leftHeld = leftHeld;
	}
	
	public boolean isDownHeld() {
		return downHeld;
	}

	public void setDownHeld(boolean downHeld){
		this.downHeld = downHeld;
	}

}
