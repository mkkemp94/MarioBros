package com.mkemp.mariobros.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mkemp.mariobros.Sprites.InteractiveTileObject;

/**
 * Created by mkemp on 6/26/17.
 *
 * A contact listener gets called when two fixtures in box2d collide.
 */

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        // Figure out which fixture is which.
        if (fixA.getUserData() == "head" || fixB.getUserData() == "head") {
            Fixture head = (fixA.getUserData() == "head") ? fixA : fixB;
            Fixture object = (fixA == head) ? fixB : fixA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("End", "contact");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Once something has collided, you can change the characteristics of the collision.
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Gives reult of what happened from that collision.
    }
}
