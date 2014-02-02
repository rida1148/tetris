package com.hexbit.tetris.threed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hexbit.tetris.TetrisScreen;

public class Screen3D extends TetrisScreen{
	PerspectiveCamera camera;

	public Model model;
	public ModelInstance instance;

	private ModelBatch modelBatch;
	
	ShapeRenderer shapeRenderer;

	Environment environment;
	
	
	@Override
	public void load() {
		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		camera.position.set(0, 50f, 50f);
		camera.lookAt(0, 0, 0);
		camera.near = 0.1f;
		camera.far = 300f;
		camera.update();

		ModelBuilder modelBuilder = new ModelBuilder();
		model = modelBuilder.createBox(5f, 5f, 5f,
				new Material(ColorAttribute.createDiffuse(Color.GREEN)),
				Usage.Position | Usage.Normal);
		instance = new ModelInstance(model);

		modelBatch = new ModelBatch();

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f,
				0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f,
				-0.8f, -0.2f));
		
		shapeRenderer = new ShapeRenderer();
		
		Gdx.input.setInputProcessor(new InputMultiplexer(this,new CameraInputController(camera)));
        
	}
	

	@Override
	public void resetGame() {
		mMatrix = new Matrix3D();
		mTetrominoStack = new TetrominoStack3D();
		mCurrentTetromino = mTetrominoStack.getNextPiece();
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		gameLogic(delta);
		
		camera.update();
		
		shapeRenderer.setProjectionMatrix(camera.combined);
		//shapeRenderer.setTransformMatrix(camera.view);
		//shapeRenderer.setProjectionMatrix(camera.projection);
		
		
		((Matrix3D)mMatrix).drawGrid(shapeRenderer);
	
		
		modelBatch.begin(camera);
		((Matrix3D)mMatrix).draw(modelBatch,environment);
		((Tetromino3D) mCurrentTetromino).draw(modelBatch, environment);
		
		modelBatch.end();
		
		if(Gdx.input.isKeyPressed(Keys.X)){
			camera.direction.x+=.001f;
		}else if(Gdx.input.isKeyPressed(Keys.Y)){
			camera.direction.y+=.001f;
		}else if(Gdx.input.isKeyPressed(Keys.Z)){
			camera.direction.z+=.001f;
		}else if(Gdx.input.isKeyPressed(Keys.SPACE)){
			System.out.println(camera.position.toString());
			System.out.println(camera.direction.toString());
			System.out.println(camera.combined.toString());
			System.out.println("-----------------");
		}

	}

	@Override
	public void dispose() {
		model.dispose();
		shapeRenderer.dispose();
	}

}
