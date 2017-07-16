package com.mkemp.mariobros.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.mkemp.mariobros.MarioBros;
import com.mkemp.mariobros.Scenes.Hud;
import com.mkemp.mariobros.Screens.PlayScreen;
import com.mkemp.mariobros.Sprites.Items.ItemDef;
import com.mkemp.mariobros.Sprites.Items.Mushroom;
import com.mkemp.mariobros.Sprites.Mario;

import static com.mkemp.mariobros.MarioBros.COIN_BIT;
import static com.mkemp.mariobros.MarioBros.PPM;

/**
 * Created by mkemp on 6/26/17.
 */

public class Coin extends InteractiveTileObject {

    private static TiledMapTileSet tileSet;

    // Tiled starts counting at 0, while LibGDX TileSet starts at 1.
    // Add one to the Tiled index.
    private final int BLANK_COIN = 28;

    public Coin(PlayScreen screen, MapObject object) {
        super(screen, object);

        // Get the tileset resource.
        tileSet = map.getTileSets().getTileSet("tileset_gutter");

        fixture.setUserData(this);

        // Set filter category to make this object read as a coin.
        setCategoryFilter(COIN_BIT);
    }

    @Override
    public void onHeadHit(Mario mario) {
        Gdx.app.log("Coin", "Collision");

        // Play bump sound if coin is blank or coin sound if not.
        if (getCell().getTile().getId() == BLANK_COIN)
            MarioBros.manager.get("audio/sounds/bump.wav", Sound.class).play();
        else {
            if (object.getProperties().containsKey("mushroom")) {
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x,
                        body.getPosition().y + 16 / PPM), Mushroom.class));
                MarioBros.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
            } else {
                MarioBros.manager.get("audio/sounds/coin.wav", Sound.class).play();
            }
        }

        // Set the image of this coin to blank.
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addScore(100);


    }
}
