package com.mkemp.mariobros.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mkemp.mariobros.MarioBros;
import com.mkemp.mariobros.Scenes.Hud;

import static com.mkemp.mariobros.MarioBros.COIN_BIT;

/**
 * Created by mkemp on 6/26/17.
 */

public class Coin extends InteractiveTileObject {

    private static TiledMapTileSet tileSet;

    // Tiled starts counting at 0, while LibGDX TileSet starts at 1.
    // Add one to the Tiled index.
    private final int BLANK_COIN = 28;

    public Coin(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);

        // Get the tileset resource.
        tileSet = map.getTileSets().getTileSet("tileset_gutter");

        fixture.setUserData(this);

        // Set filter category to make this object read as a coin.
        setCategoryFilter(COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin", "Collision");

        // Set the image of this coin to blank.
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addScore(100);

        if (getCell().getTile().getId() == BLANK_COIN)
            MarioBros.manager.get("audio/sounds/bump.wav", Sound.class).play();
        else
            MarioBros.manager.get("audio/sounds/coin.wav", Sound.class).play();
    }
}
