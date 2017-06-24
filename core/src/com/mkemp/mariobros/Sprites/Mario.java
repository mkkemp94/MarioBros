package com.mkemp.mariobros.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import static com.mkemp.mariobros.MarioBros.PPM;

/**
 * Created by kempm on 6/24/2017.
 */

public class Mario extends Sprite {

    public World world;
    public Body b2body;

    public Mario(World world) {
        this.world = world;
        defineMario();
    }

    /**
     * Define mario's body.
     * Create the body out of a definition,
     * and then create a fixture for it.
     */
    private void defineMario() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / PPM, 32 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
