package com.hexbit.tetris.gui;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.hexbit.tetris.modes.gem.MarathonModeGem;
import com.hexbit.tetris.modes.gem.SprintModeGem;
import com.hexbit.tetris.modes.glow.GlowScreen;
import com.hexbit.tetris.modes.threed.Screen3D;
import com.hexbit.tetris.modes.vector.VectorScreen;

public class ModeSelector extends GuiScreen {
	
	final static String[] MODE_NAMES = {"Marathon Mode","Sprint Mode"};

	final static Class[] MODES = new Class[] {MarathonModeGem.class,SprintModeGem.class};
	
	//to generate the names from the names of the classes (easy for testing)
	static String[] getStringList(){
		ArrayList<String> names = new ArrayList<String>();
		for (Class c: MODES){
			names.add(c.getName().substring(c.getName().lastIndexOf(".")+1));
		}
		return names.toArray(new String[0]);
	}
	
	static ClickListener[] getClickListenerList(){
		ArrayList<ClickListener> listeners = new ArrayList<ClickListener>();
		for (final Class c: MODES){	
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
		//super("Modes:", getStringList(), getClickListenerList());
		super("Modes:", MODE_NAMES, getClickListenerList());
	}
}
