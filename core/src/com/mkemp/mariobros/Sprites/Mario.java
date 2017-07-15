package com.mkemp.mariobros.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mkemp.mariobros.Screens.PlayScreen;

import static com.mkemp.mariobros.MarioBros.BRICK_BIT;
import static com.mkemp.mariobros.MarioBros.COIN_BIT;
import static com.mkemp.mariobros.MarioBros.ENEMY_BIT;
import static com.mkemp.mariobros.MarioBros.ENEMY_HEAD_BIT;
import static com.mkemp.mariobros.MarioBros.GROUND_BIT;
import static com.mkemp.mariobros.MarioBros.ITEM_BIT;
import static com.mkemp.mariobros.MarioBros.MARIO_BIT;
import static com.mkemp.mariobros.MarioBros.OBJECT_BIT;
import static com.mkemp.mariobros.MarioBros.PPM;

/**
 * Created by kempm on 6/24/2017.
 */

public class Mario extends Sprite {

    public enum State { FALLING, JUMPING, STANDING, RUNNING };
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    private Animation<TextureRegion> marioRun;
    private Animation<TextureRegion> marioJump;
    private float stateTimer;
    private boolean runningRight;

    /**
     * We're passing the region we want to Sprite's constructor using super().
     * Below, we're getting that texture from the Sprite class.
     */
    public Mario(PlayScreen screen) {
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 4; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        marioRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i = 4; i < 6; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        marioJump = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        marioStand = new TextureRegion(getTexture(), 0, 0, 16, 16);

        defineMario();
        setBounds(0, 0, 16 / PPM, 16 / PPM);
        setRegion(marioStand);
    }

    /**
     * Attach the sprite to the mario body.
     */
    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    /**
     * Returns the frame to display at this time.
     */
    private TextureRegion getFrame(float dt) {
        currentState = getState();

        // Get the region based on what mario is doing.
        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }

        // Flip mario if he needs to be flipped.
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        // Reset state timer if this new state is different from the previous.
        stateTimer = (currentState == previousState) ? stateTimer + dt : 0;
        previousState = currentState;

        return region;
    }

    /**
     * Based on what our body is currently doing, return its state.
     */
    private State getState() {
        if (b2body.getLinearVelocity().y > 0 | (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
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
        fdef.filter.categoryBits = MARIO_BIT;
        fdef.filter.maskBits = GROUND_BIT |
                COIN_BIT |
                BRICK_BIT |
                ENEMY_BIT |
                OBJECT_BIT |
                ENEMY_HEAD_BIT |
                ITEM_BIT
        ;

        fdef.shape = shape;
        b2body.createFixture(fdef);

        // Create sensor on his head.
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PPM, 6 / PPM), new Vector2(2 / PPM, 6 / PPM));
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("head");
    }
}
