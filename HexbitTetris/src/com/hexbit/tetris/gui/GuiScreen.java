package com.hexbit.tetris.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public abstract class GuiScreen implements Screen {
	private static final int SPACE = 10;

	private Stage stage;
	private TextureAtlas textureAtlas;
	private Table table;
	private Label titleLabel;
	private BitmapFont font;
	private Skin skin;

	public GuiScreen(String title, String[] buttonsLabels,
			ClickListener[] listeners) {
		setup(title, buttonsLabels, listeners);
	}

	@Override
	public void show() {

	}

	void setup(String title, String[] buttonStr, ClickListener[] listeners) {
		Button[] buttons = new Button[buttonStr.length];

		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		textureAtlas = new TextureAtlas("ui/button.pack");
		skin = new Skin(textureAtlas);
		font = new BitmapFont(Gdx.files.internal("font/gamefont.fnt"));

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// title setup
		LabelStyle titleStyle = new LabelStyle();
		titleStyle.font = font;

		titleLabel = new Label(title, titleStyle);

		// button styles
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("button-up");
		buttonStyle.down = skin.getDrawable("button-down");
		buttonStyle.pressedOffsetX = 1;
		buttonStyle.pressedOffsetY = -1;
		buttonStyle.font = font;

		// table.debug();

		table.add(titleLabel);
		table.getCell(titleLabel).spaceBottom(20);
		table.row();

		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new TextButton(buttonStr[i], buttonStyle);
			buttons[i].pad(10);
			buttons[i].addListener(listeners[i]);
			table.add(buttons[i]);
			table.getCell(buttons[i]).spaceBottom(SPACE);
			if (i < buttons.length) { 
				table.row();
			}

		}

		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Table.drawDebug(stage);
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
		textureAtlas.dispose();
		font.dispose();
		skin.dispose();
	}

}
