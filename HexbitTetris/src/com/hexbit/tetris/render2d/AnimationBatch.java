package com.hexbit.tetris.render2d;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class AnimationBatch {
	private ArrayList<Animation> animations = new ArrayList<Animation>();
	
	public void add(Animation animation) {
		animations.add(animation);
	}

	public void animateAll(ShapeRenderer shapeRenderer, int xOffset, int yOffset) {
		for (int i = 0; i < animations.size(); i++) {
			animations.get(i).animate(shapeRenderer, xOffset, yOffset);
		}
	}

	public void update(float delta) {
		for (int i = 0; i < animations.size(); i++) {
			animations.get(i).update(delta);
		}
		// remove done animations
		for (int i = animations.size() - 1; i >= 0; i--) {
			if (animations.get(i).doneAnimating()) {
				animations.remove(i);
			}
		}
	}

}
