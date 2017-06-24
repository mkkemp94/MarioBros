package com.mkemp.mariobros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mkemp.mariobros.MarioBros;
import com.mkemp.mariobros.Scenes.Hud;

import static com.mkemp.mariobros.MarioBros.V_HEIGHT;
import static com.mkemp.mariobros.MarioBros.V_WIDTH;

/**
 * Created by kempm on 6/24/2017.
 *
 * The Screen executes logic and draws things to the screen.
 * It does all the dirty work for the game.
 * There can be many screens, all of which implement the Screen interface.
 */

public class PlayScreen implements Screen {

    private MarioBros game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    // We're sending the game to the screen, so we need a constructor.
    public PlayScreen(MarioBros game) {
        this.game = game;

        // Handle camera.
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(V_WIDTH, V_HEIGHT, gameCam);
        hud = new Hud(game.batch);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Clear the screen.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Tell the sprite batch to only render what we can see.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        // Open box to start drawing.
        game.batch.begin();

        // Close box.
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

        // When we resize the screen, adjust the viewport.
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
