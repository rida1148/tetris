package com.hexbit.tetris;

public class Timer {
	
	private float mFinishTime;
	
	private float mCurrentTime;
	
	private boolean enabled = true;
	
	public Timer(float finishtime){
		mFinishTime = finishtime;
	}
	
	public void tick(float time){
		if(enabled){
			mCurrentTime+=time;
		}	
	}
	
	public boolean isFinished(){
		if(mCurrentTime > mFinishTime){
			return true;
		}
		return false;
	}
	
	public void reset(){
		mCurrentTime = 0;
	}
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	public float getCurrentTime(){
		return mCurrentTime;
	}
	public boolean isEnabled() {
		return enabled;
	}
	
	public float getProgressPercent(){
		return mCurrentTime/mFinishTime;
	}
	
	public float getFinishTime() {
		return mFinishTime;
	}
	
}
