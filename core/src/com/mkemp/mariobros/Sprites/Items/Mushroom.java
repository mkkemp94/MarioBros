package com.mkemp.mariobros.Sprites.Items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mkemp.mariobros.Screens.PlayScreen;
import com.mkemp.mariobros.Sprites.Mario;

import static com.mkemp.mariobros.MarioBros.BRICK_BIT;
import static com.mkemp.mariobros.MarioBros.COIN_BIT;
import static com.mkemp.mariobros.MarioBros.GROUND_BIT;
import static com.mkemp.mariobros.MarioBros.ITEM_BIT;
import static com.mkemp.mariobros.MarioBros.MARIO_BIT;
import static com.mkemp.mariobros.MarioBros.OBJECT_BIT;
import static com.mkemp.mariobros.MarioBros.PPM;

/**
 * Created by mkemp on 7/14/17.
 */

public class Mushroom extends Item {

    public Mushroom(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("mushroom"), 0, 0, 16, 16);
        velocity = new Vector2(0.7f, 0);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PPM);
        fdef.filter.categoryBits = ITEM_BIT;
        fdef.filter.maskBits = MARIO_BIT |
                OBJECT_BIT |
                GROUND_BIT |
                COIN_BIT |
                BRICK_BIT
        ;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use(Mario mario) {
        destroy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        velocity.y = body.getLinearVelocity().y;
        body.setLinearVelocity(velocity);
    }
}
