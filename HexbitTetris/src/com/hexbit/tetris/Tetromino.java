package com.hexbit.tetris;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Tetromino {
	// time it takes to be able to set keys as held
	final float KEY_HOLD_DELAY = 0.2f;

	final float SPEED_MIN = .45f;
	final float SPEED_MAX = .02f;

	private final Timer leftHeldTimer = new Timer(KEY_HOLD_DELAY);
	private final Timer rightHeldTimer = new Timer(KEY_HOLD_DELAY);

	// speed of the tetromino once a key is held down
	final float HOLD_SPEED = 0.09f;
	private final Timer keyHoldSpeedTimer = new Timer(HOLD_SPEED);

	public static final Point DOWN = new Point(0, -1);
	public static final Point LEFT = new Point(-1, 0);
	public static final Point RIGHT = new Point(1, 0);
	public static final Point UP = new Point(0, 1);

	protected static final boolean ghost = true;

	protected int mId;

	private int[][][] rotationStates;

	private int mCurrentRotationState;
	protected Point mPos = new Point(0, 0);
	private boolean done = false;

	public static Random random = new Random();

	private boolean downHeld;

	public static enum Type {
		I(0, Color.CYAN), O(1, Color.YELLOW), T(2, new Color(230, 230, 259, 0)), S(
				3, Color.GREEN), Z(4, Color.RED), J(5, Color.BLUE), L(6,
				Color.ORANGE);

		int id;
		public Color color;

		private Type(int i, Color c) {
			id = i;
			color = c;
		}
	};

	public Tetromino() {
		this(random.nextInt(7));
		// this(2);
	}

	public void reset() {
		mPos = new Point(Dimens.GRID_WIDTH / 2, Dimens.GRID_HEIGHT - 3);
		mCurrentRotationState = 0;
	}

	public Tetromino(int id) {
		Type t = Type.values()[id];
		mId = t.id;
		mCurrentRotationState = 0;
		rotationStates = RotationStateList.values()[id].getRotationStates();
		reset();
		leftHeldTimer.pause();
		rightHeldTimer.pause();
	}

	private Tetromino(Tetromino tetromino) {
		this(tetromino.getId());
		setPos(tetromino.getPos());
		setCurrentRotationState(tetromino.getCurrentRotationState());
	}

	private int getNextRotationState() {
		if (rotationStates.length > 0) {
			if (mCurrentRotationState < rotationStates.length - 1) {
				return mCurrentRotationState + 1;
			}
			return 0;
		}
		return 0;
	}

	public boolean rotateClockwise(Matrix matrix) {
		if (matrix.isValid(getNextShape(), mPos)) {
			mCurrentRotationState = getNextRotationState();
			return true;
		}
		Tetromino tmp = new Tetromino(this);
		tmp.setCurrentRotationState(getNextRotationState());
		//wall kicks
		if (matrix.isValid(tmp, LEFT)) {
			move(LEFT);
			mCurrentRotationState = getNextRotationState();
			return true;
		} else if (matrix.isValid(tmp, RIGHT)) {
			move(RIGHT);
			mCurrentRotationState = getNextRotationState();
			return true;
		}else if(matrix.isValid(tmp,UP)){
			move(UP);
			mCurrentRotationState = getNextRotationState();
			return true;
		}

		return false;
	}

	// void rotateClockwiseOld(Matrix matrix) {
	// int nextState = mCurrentRotationState;
	// if (rotationStates.length > 0) {
	// if (mCurrentRotationState < rotationStates.length - 1) {
	// nextState++;
	// } else {
	// nextState = 0;
	// }
	// }
	// Tetromino test = new TetrominoVector(mId);
	// test.setPos(mPos);
	// test.setCurrentRotationState(nextState);
	//
	// if (matrix.isValid(test)) {
	// mCurrentRotationState = nextState;
	// }
	//
	// }

	public void move(Point move) {
		mPos.x += move.x;
		mPos.y += move.y;
	}

	public void hardDrop(Matrix matrix) {
		mPos = getHardDropPos(matrix);
		addToMatrix(matrix);
	}

	public Point getHardDropPos(Matrix matrix) {
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

	public void addToMatrix(Matrix matrix) {
		int[][] shape = getShape();
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j] == 1) {
					int x = getPos().x + j;
					int y = getPos().y + i;
					matrix.setCell(new Point(x, y), mId + 1);
				}
			}
		}
		setDone(true);
	}

	public void update(Matrix matrix, float delta) {
		leftHeldTimer.tick(delta);
		rightHeldTimer.tick(delta);
		keyHoldSpeedTimer.tick(delta);

		if (leftHeldTimer.isFinished() && matrix.isValid(this, Tetromino.LEFT)
				&& keyHoldSpeedTimer.isFinished()) {
			move(Tetromino.LEFT);
		}
		if (rightHeldTimer.isFinished()
				&& matrix.isValid(this, Tetromino.RIGHT)
				&& keyHoldSpeedTimer.isFinished()) {
			move(Tetromino.RIGHT);
		}
		if (downHeld && matrix.isValid(this, DOWN)
				&& keyHoldSpeedTimer.isFinished()) {
			move(DOWN);
		}
		if (keyHoldSpeedTimer.isFinished()) {
			keyHoldSpeedTimer.reset();
		}

	}

	public int getCubeCount() {
		int total = 0;
		int[][] shape = getShape();
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[0].length; j++) {
				if (shape[i][j] == 1) {
					total++;
				}
			}
		}
		return total;
	}

	float getSpeed(int level) {
		float p = ((float) level) / 10;
		float r = SPEED_MIN - SPEED_MAX;
		float speed = r * p;
		return SPEED_MIN - speed; // invert cus smaller is faster
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

	public int getCurrentRotationState() {
		return mCurrentRotationState;
	}

	public void setCurrentRotationState(int currentRotationState) {
		this.mCurrentRotationState = currentRotationState;
	}

	public boolean isDownHeld() {
		return downHeld;
	}

	public void setDownHeld(boolean downHeld) {
		this.downHeld = downHeld;
	}

	public Timer getLeftHeldTimer() {
		return leftHeldTimer;
	}

	public Timer getRightHeldTimer() {
		return rightHeldTimer;
	}

	public void startLeftHeldTimer() {
		leftHeldTimer.start();
	}

	public void startRightHeldTimer() {
		rightHeldTimer.start();
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public int[][] getShape() {
		return rotationStates[mCurrentRotationState];
	}

	public int[][] getNextShape() {
		return rotationStates[getNextRotationState()];
	}
}
