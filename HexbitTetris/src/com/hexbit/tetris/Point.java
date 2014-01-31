package com.hexbit.tetris;

public class Point {
	public int x;
	public int y;
	
	public Point(Point p){
		x = p.x;
		y = p.y;
	}
	
	public Point(int X, int Y) {
		x = X;
		y = Y;
	}
	public String toString(){
		return "x: "+x+"\ny: "+y;
	}
	public void sub(Point toSub){
		x-=toSub.x;
		y-=toSub.y;
	}
	public void add(Point a){
		x+=a.x;
		y+=a.y;
	}

}
