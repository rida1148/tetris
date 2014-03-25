package com.hexbit.tetris.gui;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.hexbit.tetris.Utils;
import com.hexbit.tetris.modes.gem.MarathonModeGem;
import com.hexbit.tetris.modes.gem.SprintModeGem;
import com.hexbit.tetris.render2d.TetrisScreen2D;


public class ModeSelector extends GuiScreen {
	
	final static String[] MODE_NAMES = {"Marathon Mode","Sprint Mode"};

	final static Class[] MODES = new Class[] {MarathonModeGem.class,SprintModeGem.class};
	
	//to generate the names from the names of the classes (easy for testing)
	static String[] getStringList(){
		ArrayList<String> names = new ArrayList<String>();
		for (Class c: MODES){
			//#do you even lift
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
						try {
							Utils.setScreen((TetrisScreen2D) c.getDeclaredConstructor(String.class).newInstance("gem"));
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						}
						//((Game) Gdx.app.getApplicationListener()).setScreen((TetrisScreen2D) c.newInstance());
						
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
