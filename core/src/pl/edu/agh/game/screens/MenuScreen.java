package pl.edu.agh.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import pl.edu.agh.game.CleaverOfDoom;

public class MenuScreen implements Screen {
    private final CleaverOfDoom game;
    SpriteBatch batch;
    Texture img;
    private Stage stage;

    public MenuScreen(CleaverOfDoom game) {
        this.game = game;
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

//        img = new Texture("badlogic.jpg");
//        Gdx.input.setInputProcessor(stage);
        TextButton startButton = new TextButton("Start", new TextButton.TextButtonStyle(null, null, null, new BitmapFont()));
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("clicked");
                resume();
            }
        });

        stage.addActor(startButton);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        stage.draw();

        //batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        game.setScreen(new PlayableScreen());
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
