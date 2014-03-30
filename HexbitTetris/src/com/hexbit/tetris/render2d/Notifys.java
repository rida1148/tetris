package com.hexbit.tetris.render2d;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.hexbit.tetris.Timer;

/***
 * 
 * @author brett class for rendering list of animated notifications like when
 *         you get a tetris
 */
public class Notifys implements Disposable {
	ArrayList<Notification> notifications = new ArrayList<Notification>();

	private BitmapFont notificationFont;

	class Notification {
		public static final float alphaIntensity = 0.8f;
		public static final float fallSpeed = 0.2f;

		Timer timer;
		String text;
		public float yOffset;

		public Notification(float length, String text, float y) {
			timer = new Timer(length);
			this.text = text;
			yOffset = y;
		}

		public Timer getTimer() {
			return timer;
		}

		public String getString() {
			return text;
		}

		public void draw(SpriteBatch spriteBatch) {
			GraphicUtils.enableAlpha();

			notificationFont.setColor(1, 1, 1, alphaIntensity
					- (alphaIntensity * timer.getProgressPercent()));

			spriteBatch.begin();
			notificationFont.draw(spriteBatch, text, (Gdx.graphics.getWidth()
					/ 2) - ((notificationFont.getSpaceWidth() * text.length()) / 2),
					Gdx.graphics.getHeight() / 2 + yOffset);
			spriteBatch.end();

			GraphicUtils.disableAlpha();
		}

	}

	public Notifys() {
		notificationFont = new BitmapFont(
				Gdx.files.internal("font/notification.fnt"));
	}

	// TODO make sure notifications don't overlap
	public void addNotification(String text, float length) {
		notifications.add(new Notification(length, text, notifications.size()
				* notificationFont.getCapHeight()));
	}

	public void draw(SpriteBatch spriteBatch) {
		if (notifications.size() > 0) {
			for (int i = notifications.size() - 1; i >= 0; i--) {
				notifications.get(i).draw(spriteBatch);
			}
		}

	}

	public void update(float delta) {

		for (int i = notifications.size() - 1; i >= 0; i--) {
			notifications.get(i).getTimer().tick(delta);
			notifications.get(i).yOffset -= Notification.fallSpeed;
			if (notifications.get(i).getTimer().isFinished()) {
				notifications.remove(i);
				System.out.println("notificaion removed");
			}
		}

	}

	@Override
	public void dispose() {
		notificationFont.dispose();

	}

}
