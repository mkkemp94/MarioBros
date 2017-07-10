package com.mkemp.mariobros.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mkemp.mariobros.Scenes.Hud;

import static com.mkemp.mariobros.MarioBros.COIN_BIT;
import static com.mkemp.mariobros.MarioBros.DESTROYED_BIT;

/**
 * Created by mkemp on 6/26/17.
 */

public class Brick extends InteractiveTileObject {

    public Brick(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);

        // Set filter category to make this object read as a brick.
        setCategoryFilter(COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "Collision");

        // Destroy this brick by changing its category.
        setCategoryFilter(DESTROYED_BIT);

        // Get the cell at the current position being hit,
        // and set it to null - it's destroyed.
        getCell().setTile(null);

        // Update score.
        Hud.addScore(200);

    }
}
