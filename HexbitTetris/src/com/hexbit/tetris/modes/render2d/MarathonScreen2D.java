package com.hexbit.tetris.modes.render2d;

import com.hexbit.tetris.Utils;
import com.hexbit.tetris.gui.FinishScreen;
import com.hexbit.tetris.modes.gem.MarathonModeGem;


public abstract class MarathonScreen2D extends TetrisScreen2D{
	@Override
	public void resetGame() {
		if(!firstReset){
			Utils.setScreen(new FinishScreen(this,null, "Game over"));
		}else{
			super.resetGame();
		}
	}

}
