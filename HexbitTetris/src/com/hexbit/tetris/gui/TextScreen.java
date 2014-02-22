package com.hexbit.tetris.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.Game;

/***
 * 
 * @author brett
 * 
 *         A screen with a title label and a text area body for displaying text
 */

public class TextScreen implements Screen {
	private Stage stage;
	private TextureAtlas textureAtlas;
	private Table table;
	private BitmapFont font;
	private Skin skin;

	private String mTitle;
	private String[] mBody;

	private Label titleLabel;
	private TextField mBodyField;
	private Button okButton;

	private final Color fontColor = Color.WHITE;

	public TextScreen(String title, String... body) {
		mTitle = title;
		mBody = body;
	}

	@Override
	public void show() {
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		textureAtlas = new TextureAtlas("ui/button.pack");
		skin = new Skin(textureAtlas);
		font = new BitmapFont(Gdx.files.internal("font/gamefont.fnt"));

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		LabelStyle titleStyle = new LabelStyle();
		titleStyle.font = font;

		titleLabel = new Label(mTitle, titleStyle);

		String bodyText = "";
		
		for (int i = 0; i < mBody.length; i++) {
			bodyText += mBody[i] + "\n";
		}
		
		TextFieldStyle textFieldStyle = new TextFieldStyle();
		textFieldStyle.font = font;
		textFieldStyle.fontColor = fontColor;
		

		mBodyField = new TextField(bodyText, textFieldStyle);
		
		System.out.println(bodyText);

		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("button-up");
		buttonStyle.down = skin.getDrawable("button-down");
		buttonStyle.pressedOffsetX = 1;
		buttonStyle.pressedOffsetY = -1;
		buttonStyle.font = font;

		okButton = new TextButton("OK", buttonStyle);
		okButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});

		table.add(titleLabel);
		table.getCell(titleLabel).spaceBottom(20);
		table.row();
		table.add(mBodyField);
		table.row();
		table.add(okButton);

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
