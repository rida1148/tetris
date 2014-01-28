package com.hexbit.tetris;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import static com.hexbit.tetris.Dimens.*;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "HexbitTetris";
		cfg.useGL20 = false;
		cfg.width = DESKTOP_WIDTH;
		cfg.height = DESKTOP_HEIGHT;
		
		new LwjglApplication(new TetrisGame(), cfg);
	}
}
