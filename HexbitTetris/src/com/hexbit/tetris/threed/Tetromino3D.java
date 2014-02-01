package com.hexbit.tetris.threed;

import static com.hexbit.tetris.Dimens.CELL3D;

import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.hexbit.tetris.Tetromino;

public class Tetromino3D extends Tetromino{
	
	static ModelBuilder modelBuilder  = new ModelBuilder();
	
	public Tetromino3D(int id) {
		super(id);
	}
	public Tetromino3D() {
		this(random.nextInt(7));
	}
	
	void draw(ModelBatch modelBatch,Environment environment){
		int[][] shape = getShape();
		
		for(int i = 0; i < shape.length; i++){
			for (int j = 0; j < shape[0].length; j++) {
				if(shape[i][j] == 1){
					int x = mPos.x+j;
					int y = mPos.y+i;
					
					Model model = modelBuilder.createBox(CELL3D, CELL3D, CELL3D,new Material(ColorAttribute.createDiffuse(Type.values()[mId].color)),Usage.Position | Usage.Normal);
					ModelInstance modelInstance = new ModelInstance(model,CELL3D*x,CELL3D*y, 0);
					modelBatch.render(modelInstance,environment);
					
					//model.dispose();		
				}
			}
		}
	}
}
