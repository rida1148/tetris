package com.hexbit.tetris.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu extends GuiScreen {

	public MainMenu() {
		super("Hexbit tetris", new String[] { "Start" },
				new ClickListener[] { new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						((Game) Gdx.app.getApplicationListener())
								.setScreen(new ModeSelector());
					}
				}

				});
	}

}
