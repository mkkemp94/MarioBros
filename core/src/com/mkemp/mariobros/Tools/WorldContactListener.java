package com.mkemp.mariobros.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mkemp.mariobros.Sprites.Enemies.Enemy;
import com.mkemp.mariobros.Sprites.Items.Item;
import com.mkemp.mariobros.Sprites.Mario;
import com.mkemp.mariobros.Sprites.TileObjects.InteractiveTileObject;

import static com.mkemp.mariobros.MarioBros.ENEMY_BIT;
import static com.mkemp.mariobros.MarioBros.ENEMY_HEAD_BIT;
import static com.mkemp.mariobros.MarioBros.ITEM_BIT;
import static com.mkemp.mariobros.MarioBros.MARIO_BIT;
import static com.mkemp.mariobros.MarioBros.OBJECT_BIT;

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

        // Collision definition
        int cdef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        // Figure out which fixture is which.
        if (fixA.getUserData() == "head" || fixB.getUserData() == "head") {
            Fixture head = (fixA.getUserData() == "head") ? fixA : fixB;
            Fixture object = (fixA == head) ? fixB : fixA;

            // Call onHeadHit() for interactive tile objects.
            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }

        // We or'd the two fixtures up above and stored it in cdef.
        switch (cdef) {

            // Enemy Head and Mario collide.
            // If fixA is the enemy head, get its object type and run
            // hitOnHead(). Otherwise if fixB is the head, do the same for that.
            case ENEMY_HEAD_BIT | MARIO_BIT:
                if (fixA.getFilterData().categoryBits == ENEMY_HEAD_BIT)
                    ((Enemy) fixA.getUserData()).hitOnHead();
                else
                    ((Enemy) fixB.getUserData()).hitOnHead();
                break;

            case ENEMY_BIT | OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case MARIO_BIT | ENEMY_BIT:
                Gdx.app.log("Mario", "Died");
                break;

            case ENEMY_BIT | ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case ITEM_BIT | OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == ITEM_BIT)
                    ((Item) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Item) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case ITEM_BIT | MARIO_BIT:
                if (fixA.getFilterData().categoryBits == ITEM_BIT)
                    ((Item) fixA.getUserData()).use((Mario) fixB.getUserData());
                else
                    ((Item) fixB.getUserData()).use((Mario) fixA.getUserData());
                break;
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
