package com.mkemp.mariobros;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkemp.mariobros.Screens.PlayScreen;

/**
 * A Game is an application listener that delegates to a screen.
 * It allows an application to have multiple screens.
 */
public class MarioBros extends Game {

    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

	public static final short GROUND_BIT = 1;
	public static final short MARIO_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;

	public SpriteBatch batch;

	/* WARNING Using AssetManager in a static way can cause issues, especially on Android.
	You should pass it around. But we're just using static to save time. */
	public static AssetManager manager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/music/mario_music.ogg", Music.class);
		manager.load("audio/sounds/coin.wav", Sound.class);
		manager.load("audio/sounds/bump.wav", Sound.class);
		manager.load("audio/sounds/breakblock.wav", Sound.class);
		manager.finishLoading();

		// Create a PlayScreen and pass it the game.
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {

		// There is an Async part of asset manager.
		// Iff all are loaded...
//		if (manager.update()) {
//			// Do stuff
//		}

		// Make the screen take care of rendering.
		super.render();
	}
	
	@Override
	public void dispose () {
		manager.dispose();
		batch.dispose();
	}
}
