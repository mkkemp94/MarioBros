package com.mkemp.mariobros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mkemp.mariobros.MarioBros;
import com.mkemp.mariobros.Scenes.Hud;
import com.mkemp.mariobros.Sprites.Goomba;
import com.mkemp.mariobros.Sprites.Mario;
import com.mkemp.mariobros.Tools.B2WorldCreator;
import com.mkemp.mariobros.Tools.WorldContactListener;

import static com.mkemp.mariobros.MarioBros.PPM;
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
    private TextureAtlas atlas;

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr; // lets us see what's going on

    private Mario player;
    private Music music;

    private Goomba goomba;

    // We're sending the game to the screen, so we need a constructor.
    public PlayScreen(MarioBros game) {
        this.game = game;

        // This texture atlas allows us to read from .pack files.
        // Use an AssetManager if there are have resources to load.
        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        // Handle camera.
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(V_WIDTH / PPM, V_HEIGHT / PPM, gameCam);
        hud = new Hud(game.batch);

        // Handle rendering tiled map.
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("custom_level.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        // Give the world gravity and allow sleep on rest.
        world = new World(new Vector2(0, -10), true);

        // This shows green lines around box2d objects for debugging.
        b2dr = new Box2DDebugRenderer();

        // Create a new B2WorldCreator, which creates everything in the game world.
        new B2WorldCreator(this);

        player = new Mario(this);

        world.setContactListener(new WorldContactListener());

        music = MarioBros.manager.get("audio/music/mario_music.ogg", Music.class);
        music.setLooping(true);
        music.play();

        goomba = new Goomba(this, 5.64f, .16f);
    }

    /**
     * Gets the texture atlas to retrieve character/enemy sprites from.
     */
    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    /**
     * Handle any input to the screen.
     */
    public void handleInput(float dt) {
        if (Gdx.input.isTouched()) {
            gameCam.position.x += 100 * dt;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    }

    /**
     * This method does all the updating of the game world.
     * Get input, update the camera
     */
    public void update(float dt) {
        handleInput(dt);

        //Tell box2d to calculate 60 times per second.
        world.step(1/60f, 6, 2);

        // Give mario the dt so hit sprite can stay attached.
        player.update(dt);

        // updat goomba
        goomba.update(dt);

        // Pass dt to hud to update countdown timer.
        hud.update(dt);

        // Attach the gamecam to the player's x position
        gameCam.position.x = player.b2body.getPosition().x;

        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {

        // Calculate any changes.
        update(delta);

        // Clear the screen.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render the map with the updated changes.
        renderer.render();

        // Render Box2DDebugLines (green lines)
        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);

        // Open box to start drawing.
        game.batch.begin();

        // Give mario the sprite batch to draw itself (using the Sprite class)
        player.draw(game.batch);

        goomba.draw(game.batch);

        // Close box.
        game.batch.end();

        // Tell the sprite batch to only render what we can see.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        // Draw the hud.
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        // When we resize the screen, adjust the viewport.
        gamePort.update(width, height);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
