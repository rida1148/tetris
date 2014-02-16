package com.hexbit.tetris.gui;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.hexbit.tetris.modes.gem.GemScreen;
import com.hexbit.tetris.modes.glow.GlowScreen;
import com.hexbit.tetris.modes.threed.Screen3D;
import com.hexbit.tetris.modes.vector.VectorScreen;

public class ModeSelector extends GuiScreen {

	final static Class[] modes = new Class[] {	GemScreen.class,Screen3D.class };
	
	static String[] getStringList(){
		ArrayList<String> names = new ArrayList<String>();
		for (Class c: modes){
			names.add(c.getName().substring(c.getName().lastIndexOf(".")+1));
		}
		return names.toArray(new String[0]);
	}
	
	static ClickListener[] getClickListenerList(){
		ArrayList<ClickListener> listeners = new ArrayList<ClickListener>();
		for (final Class c: modes){	
			listeners.add(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					try {
						((Game) Gdx.app.getApplicationListener()).setScreen((Screen) c.newInstance());
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			});
		}
		return listeners.toArray(new ClickListener[0]);
	}

	public ModeSelector() {
		super("Modes:", getStringList(), getClickListenerList());
	}
}
