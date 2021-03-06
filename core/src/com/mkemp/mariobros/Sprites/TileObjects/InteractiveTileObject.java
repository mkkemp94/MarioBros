package com.mkemp.mariobros.Sprites.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mkemp.mariobros.Screens.PlayScreen;
import com.mkemp.mariobros.Sprites.Mario;

import static com.mkemp.mariobros.MarioBros.PPM;

/**
 * Created by mkemp on 6/26/17.
 */

public abstract class InteractiveTileObject {

    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected PlayScreen screen;
    protected MapObject object;

    protected Fixture fixture;

    public InteractiveTileObject(PlayScreen screen, MapObject object) {
        this.object = object;
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();

        // For Coin and Brick.
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / PPM, (bounds.getY() + bounds.getHeight() / 2) / PPM);
        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / PPM, bounds.getHeight() / 2 / PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }

    public abstract void onHeadHit(Mario mario);

    /**
     * Set an object created here to have ta specific filter category.
     * @param filterBit : the category to give to this object.
     */
    public void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    /**
     * Get the cell at this specific spot on the map.
     */
    public TiledMapTileLayer.Cell getCell() {

        // Everything we want is on the graphics layer (1).
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);

        // Get the cell of the body's current position.
        // Scale the x and y coordinates back up, to interact with the real map size.
        // Divide by the tile size to get the coordinates.
        return layer.getCell((int)(body.getPosition().x * PPM / 16),
                (int)(body.getPosition().y * PPM / 16));
    }

}
