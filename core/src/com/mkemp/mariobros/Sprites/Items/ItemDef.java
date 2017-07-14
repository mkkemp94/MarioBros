package com.mkemp.mariobros.Sprites.Items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by mkemp on 7/14/17.
 */

public class ItemDef {
    public Vector2 position;
    public Class<?> type;

    public ItemDef(Vector2 position, Class<?> type) {
        this.position = position;
        this.type = type;
    }
}
