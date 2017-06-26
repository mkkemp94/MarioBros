package com.mkemp.mariobros.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mkemp.mariobros.Screens.PlayScreen;

import static com.mkemp.mariobros.MarioBros.PPM;

/**
 * Created by kempm on 6/24/2017.
 */

public class Mario extends Sprite {

    public World world;
    public Body b2body;
    private TextureRegion marioStand;

    /**
     * We're passing the region we want to Sprite's constructor using super().
     * Below, we're getting that texture from the Sprite class.
     */
    public Mario(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        defineMario();
        marioStand = new TextureRegion(getTexture(), 0, 0, 16, 16);
        setBounds(0, 0, 16 / PPM, 16 / PPM);
        setRegion(marioStand);
    }

    /**
     * Attach the sprite to the mario body.
     * @param dt
     */
    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
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
        shape.setRadius(6 / PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
