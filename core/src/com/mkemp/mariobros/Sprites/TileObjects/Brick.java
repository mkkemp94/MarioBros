package com.mkemp.mariobros.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.mkemp.mariobros.MarioBros;
import com.mkemp.mariobros.Scenes.Hud;
import com.mkemp.mariobros.Screens.PlayScreen;
import com.mkemp.mariobros.Sprites.Mario;

import static com.mkemp.mariobros.MarioBros.COIN_BIT;
import static com.mkemp.mariobros.MarioBros.DESTROYED_BIT;

/**
 * Created by mkemp on 6/26/17.
 */

public class Brick extends InteractiveTileObject {

    public Brick(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);

        // Set filter category to make this object read as a brick.
        setCategoryFilter(COIN_BIT);
    }

    @Override
    public void onHeadHit(Mario mario) {

        if (mario.isBig()) {
            Gdx.app.log("Brick", "Collision");

            // Destroy this brick by changing its category.
            setCategoryFilter(DESTROYED_BIT);

            // Get the cell at the current position being hit,
            // and set it to null - it's destroyed.
            getCell().setTile(null);

            // Update score.
            Hud.addScore(200);

            // Play sound
            MarioBros.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
        } else {
            MarioBros.manager.get("audio/sounds/bump.wav", Sound.class).play();
        }
    }
}
