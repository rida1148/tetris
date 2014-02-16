package com.hexbit.tetris;

public class Dimens {
	//cell width in px
	public static final int CELL = 32;
	public static final int CELL3D = 2;
	//Spacing for components
	public static final int COMPONENT_PAD = 3;
	//borders between window and grid
	public static final int MARGIN = (CELL*4) + (COMPONENT_PAD * 4);
	
	//grid dimens in cells
	public static final int GRID_WIDTH = 10;
	public static final int GRID_HEIGHT = 22; //top 2 hidden
	//window dimens in px
	public static final int DESKTOP_HEIGHT = CELL*GRID_HEIGHT + MARGIN*2;
	public static final int DESKTOP_WIDTH = CELL*GRID_WIDTH + MARGIN*2;
	
	//grid dimens in px
	public static final int GRIDHPX = CELL*GRID_HEIGHT;
	public static final int GRIDWPX = CELL*GRID_WIDTH;
	
}
