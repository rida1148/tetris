package com.hexbit.tetris;

public class Timer {
	
	private float mFinishTime;
	
	private float mCurrentTime;
	
	private boolean enabled = true;
	
	Timer(float finishtime){
		mFinishTime = finishtime;
	}
	
	public void tick(float time){
		if(enabled){
			mCurrentTime+=time;
		}	
	}
	
	boolean isFinished(){
		if(mCurrentTime >= mFinishTime){
			return true;
		}
		return false;
	}
	
	void reset(){
		mCurrentTime = 0;
	}
	
	void pause(){
		enabled = false;
	}
	void start(){
		enabled = true;
	}
	
	float getCurrentTime(){
		return mCurrentTime;
	}
	
}
