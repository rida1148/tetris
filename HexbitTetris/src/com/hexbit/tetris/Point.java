package com.hexbit.tetris;

public class Point {
	int x,y;
	public Point(int X, int Y) {
		x = X;
		y = Y;
	}
	public String toString(){
		return "x: "+x+"\ny: "+y;
	}
	public Point sub(Point toSub){
		return new Point(x-toSub.x,y-toSub.y);
	}
}
