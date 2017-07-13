package com.mkemp.mariobros.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mkemp.mariobros.Screens.PlayScreen;

import static com.mkemp.mariobros.MarioBros.BRICK_BIT;
import static com.mkemp.mariobros.MarioBros.COIN_BIT;
import static com.mkemp.mariobros.MarioBros.ENEMY_BIT;
import static com.mkemp.mariobros.MarioBros.ENEMY_HEAD_BIT;
import static com.mkemp.mariobros.MarioBros.GROUND_BIT;
import static com.mkemp.mariobros.MarioBros.MARIO_BIT;
import static com.mkemp.mariobros.MarioBros.OBJECT_BIT;
import static com.mkemp.mariobros.MarioBros.PPM;

/**
 * Created by mkemp on 7/12/17.
 */

public class Goomba extends Enemy {

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy = false;
    private boolean destroyed = false;

    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i * 16, 0, 16, 16));
        walkAnimation = new Animation<TextureRegion>(0.4f, frames);
        stateTime = 0;

        setBounds(getX(), getY(), 16 / PPM, 16 / PPM);
    }

    public void update(float dt) {
        stateTime += dt;

        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"), 32, 0, 16, 16));
        }
        else if (!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PPM);
        fdef.filter.categoryBits = ENEMY_BIT;
        fdef.filter.maskBits = GROUND_BIT |
                COIN_BIT |
                BRICK_BIT |
                ENEMY_BIT |
                OBJECT_BIT |
                MARIO_BIT
        ;

        fdef.shape = shape;
        b2body.createFixture(fdef);

        // Goomba also needs a head, or else we can't stomp on it.
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0]= new Vector2(-5, 8).scl(1 / PPM);
        vertice[1]= new Vector2(5, 8).scl(1 / PPM);
        vertice[2]= new Vector2(-3, 3).scl(1 / PPM);
        vertice[3]= new Vector2(3, 3).scl(1 / PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void hitOnHead() {

        // We want to get rid of the body so there's no more collision, but can't do it here
        // because hitOnHead() is called from the contact listener,
        // which gets called from the world.step() function in
        // PlayScreen, in the update() method.

        // Bodies can't be deleted at this time because what if the body is
        // colliding with two or more objects?
        setToDestroy = true;



    }
}
