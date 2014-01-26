package com.hexbit.tetris;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import static com.hexbit.tetris.Dimens.*;

public class Tetromino {

	private int mId;
	private Color mColor;
	private Color mGhostColor;
	private int[][][] rotationStates;
	private int mCurrentRotationState;
	private Point mPos = new Point(0, 0);
	boolean done = false;

	public static Random random = new Random();
	static final boolean ghost = false;

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
		//this(2);
	}

	public Tetromino(int id) {
		Type t = Type.values()[id];
		mColor = t.color;
		mGhostColor = new Color(mColor.r, mColor.g, mColor.b, 10);
		mId = t.id;
		mCurrentRotationState = 0;
		// mShape = SHAPES[mId];
		rotationStates = RotationStateList.values()[id].getRotationStates();

		mPos = new Point(Dimens.GRID_WIDTH / 2, Dimens.GRID_HEIGHT
				- getShape().length);
		// print();
	}

	void handleInput(Matrix matrix) {
		if (Gdx.input.isKeyPressed(Keys.DOWN) && matrix.isValid(this, DOWN)) {
			move(DOWN);
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT) && matrix.isValid(this, LEFT)) {
			move(LEFT);
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT) && matrix.isValid(this, RIGHT)) {
			move(RIGHT);
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			rotateClockwise();
		}
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			hardDrop(matrix);
		}
	}

	void rotateClockwise() {
		if (rotationStates.length > 0) {
			if (mCurrentRotationState < rotationStates.length - 1) {
				mCurrentRotationState++;
			} else {
				mCurrentRotationState = 0;
			}
		}
	}

	public void move(Point move) {
		mPos.x += move.x;
		mPos.y += move.y;
	}

	void hardDrop(Matrix matrix) {
		Point movement = new Point(0, 0);
		while (matrix.isValid(this, movement)) {
			movement.y--;
		}
		move(movement);
		addToMatrix(matrix);
	}

	Point getHardDropPos(Matrix matrix) {
		Point movement = new Point(0, 0);
		while (matrix.isValid(this, movement)) {
			movement.y--;
		}
		Point pos = mPos;
		pos.sub(movement);
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

	// TODO fix upside down shit

	public void draw(ShapeRenderer sr, Matrix matrix) {
		int[][] shape = getShape();
		sr.setColor(mColor);
		sr.begin(ShapeType.Filled);
		for (int i = shape.length - 1; i >= 0; i--) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j] != 0) {
					int x = mPos.x - getOrigin().x + j;
					int y =  mPos.y - getOrigin().y +i;
					sr.rect(x * Dimens.CELL, y * Dimens.CELL, Dimens.CELL,
							Dimens.CELL);
				}
			}
		}
		if (ghost) {
			sr.setColor(mGhostColor);
			Point pos = getHardDropPos(matrix);

			for (int i = 0; i < shape.length; i++) {
				for (int j = 0; j < shape[i].length; j++) {
					if (shape[i][j] != 0) {
						sr.rect(pos.x * Dimens.CELL + Dimens.CELL * j, pos.y
								* Dimens.CELL + Dimens.CELL * i, Dimens.CELL,
								Dimens.CELL);
					}
				}
			}
		}
		sr.end();
		debugDraw(sr);
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
		origin.y = shape.length - origin.y - 1; // -1 because of .length counts
												// starting at 1

		return origin;
	}

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
}
