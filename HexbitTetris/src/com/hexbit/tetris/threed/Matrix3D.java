package com.hexbit.tetris.threed;

import static com.hexbit.tetris.Dimens.CELL3D;
import static com.hexbit.tetris.Dimens.GRID_HEIGHT;
import static com.hexbit.tetris.Dimens.GRID_WIDTH;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.hexbit.tetris.Dimens;
import com.hexbit.tetris.Matrix;
import com.hexbit.tetris.Tetromino.Type;

public class Matrix3D extends Matrix{
	private ModelBuilder modelBuilder = new ModelBuilder();
	
	public void drawGrid(ShapeRenderer shapeRenderer){
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.begin(ShapeType.Line);
		//horizontal
		for (int y = 0; y <= Dimens.GRID_HEIGHT; y++) {
			//shapeRenderer.line(0, CELL*y ,CELL*GRID_WIDTH, CELL*y);
			shapeRenderer.line(0, CELL3D*y,0 ,CELL3D*GRID_WIDTH, CELL3D*y,0);
		}
		//Vertical
		for (int x = 0; x <= Dimens.GRID_WIDTH; x++) {
			//shapeRenderer.line(CELL*x, 0 , CELL*x, CELL*GRID_HEIGHT);
			shapeRenderer.line(CELL3D*x, 0 ,0, CELL3D*x, CELL3D*GRID_HEIGHT,0);
		}
		
		shapeRenderer.end();
	}

	public void draw(ModelBatch modelBatch, Environment environment) {
		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix[y].length; x++) {
				if(matrix[y][x] != 0){				
					Model model = modelBuilder.createBox(CELL3D, CELL3D, CELL3D,new Material(ColorAttribute.createDiffuse(Type.values()[matrix[y][x]-1].color)),Usage.Position | Usage.Normal);
					ModelInstance modelInstance = new ModelInstance(model,CELL3D*x,CELL3D*y,0);
					modelBatch.render(modelInstance,environment);
					//model.dispose();
				}
			}
		}

	}
}
