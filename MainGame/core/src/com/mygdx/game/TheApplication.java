package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.badlogic.gdx.physics.box2d.Body;

public class TheApplication extends ApplicationAdapter{

	public static final float PPM = 100;
	SpriteBatch batch;
	Texture img;

	Game game;


	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		game = new Game();

		game.create();
		System.out.print("ASD");
		System.out.println("Create_SUCCESS");

	}

	@Override
	public void render () {
		System.out.println("CAM_POS");
		System.out.print(game.camera.position.x);
		System.out.print(game.camera.position.y);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		game.camera.update();
		int [] nLayer = {0};
		//game.renderer.render(nLayer);
		//game.DrawLoot(batch, game.lootList);
		game.UpdateGame(batch);
		System.out.println("STATE " + game.state);
		System.out.println(game.viewPort.getScreenX());
		System.out.println(game.npcList.get(0).sprite.getX());
		game.Draw(batch);
		//game.DrawMiniMap(game.window, game.miniMap);

		//.draw(batch)(img, 0, 0);

		batch.end();
		System.out.println("LOOP_END");
	}

	public void resize(int width, int height){
		super.resize(width, height);
		game.viewPort.update(width, height);
	}
}
