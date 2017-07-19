package com.mkemp.mariobros.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mkemp.mariobros.MarioBros;

import static com.mkemp.mariobros.MarioBros.V_HEIGHT;
import static com.mkemp.mariobros.MarioBros.V_WIDTH;

/**
 * Created by mkemp on 7/19/17.
 */

public class Controller {
    Viewport viewport;
    Stage stage;
    boolean upPressed, downPressed, leftPressed, rightPressed;
    OrthographicCamera cam;
    MarioBros game;

    public Controller(MarioBros game) {
        this.game = game;
        cam = new OrthographicCamera();
        viewport = new FitViewport(V_WIDTH, V_HEIGHT, cam);
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.left().bottom();

        Image upImage = new Image(new Texture("Mario_GFX/up.png"));
        upImage.setSize(50, 50);
        upImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Image downImage = new Image(new Texture("Mario_GFX/down.png"));
        downImage.setSize(50, 50);
        downImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = false;
            }
        });

        Image leftImage = new Image(new Texture("Mario_GFX/left.png"));
        leftImage.setSize(50, 50);
        leftImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        Image rightImage = new Image(new Texture("Mario_GFX/right.png"));
        rightImage.setSize(50, 50);
        rightImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        table.add();
        table.add(upImage).size(upImage.getWidth(), upImage.getHeight());
        table.add();
        table.row().pad(5, 5, 5, 5);
        table.add(leftImage).size(leftImage.getWidth(), leftImage.getHeight());
        table.add();
        table.add(rightImage).size(rightImage.getWidth(), rightImage.getHeight());
        table.row().padBottom(5);
        table.add();
        table.add(downImage).size(downImage.getWidth(), downImage.getHeight());
        table.add();

        stage.addActor(table);
    }

    public void draw() {
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public void releaseUpPressed() {
        upPressed = false;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
