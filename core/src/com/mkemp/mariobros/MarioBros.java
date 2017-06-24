package com.mkemp.mariobros;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkemp.mariobros.Screens.PlayScreen;

/**
 * A Game is an application listener that delegates to a screen.
 * It allows an application to have multiple screens.
 */
public class MarioBros extends Game {

    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;

	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		// Create a PlayScreen and pass it the game.
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {

		// Make the screen take care of rendering.
		super.render();
	}
	
//	@Override
//	public void dispose () {
//		batch.dispose();
//		img.dispose();
//	}
}
