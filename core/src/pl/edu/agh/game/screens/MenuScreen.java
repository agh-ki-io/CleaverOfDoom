package pl.edu.agh.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pl.edu.agh.game.CleaverOfDoom;
import pl.edu.agh.game.input.Input;
import pl.edu.agh.game.input.InputState;
import pl.edu.agh.game.settings.GameSettings;
import pl.edu.agh.game.ui.UserInterface;

public class MenuScreen implements Screen {
    private final CleaverOfDoom game;
    private final InputState inputState;
    private final UserInterface userInterface;

    SpriteBatch batch;
    Texture img;
    private Stage stage;

    public MenuScreen(final CleaverOfDoom game) {
        this.game = game;
        userInterface = game.getUserInterface();
        Input input = userInterface.getInput();
        inputState = input.getInputState();
        if (inputState.getMusic() != null) inputState.getMusic().dispose();
        inputState.setMusic(Gdx.audio.newMusic(Gdx.files.internal("The_Losers_-_You_Bastard.mp3")));
        inputState.getMusic().setLooping(true);
        inputState.getMusic().setVolume(GameSettings.getInstance().getMusicVolume());
        inputState.getMusic().play();
        batch = new SpriteBatch();

        Table table = new Table();


//        Gdx.input.setInputProcessor(stage);
//        Skin skin = new Skin();
        TextButton startButton = new TextButton("Begin Your Adventure!", new TextButton.TextButtonStyle(null, null, null, new BitmapFont()));
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                System.out.println("Begin Your Adventure! " + x + " " + y);
                inputState.startGame();
            }
        });

        TextButton exitButton = new TextButton("Exit", new TextButton.TextButtonStyle(null, null, null, new BitmapFont()));
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                System.out.println("Exit " + x + " " + y);
                dispose();
            }
        });

        table.add(startButton).row();
        table.add(exitButton);

//        stage.addActor(new Image());
//        table.setFillParent(true);
        table.setPosition(stage.getWidth() / 2, stage.getHeight() * 2 / 3);
//        table.debug();
//        table.setPosition(stage.getWidth() / 2, stage.getHeight() / 2, 0);

//        table.setBackground();
        stage.addActor(table);

        img = new Texture("MenuBackground.png");

        inputState.setMenu(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
//        if (inputState.) game.setScreen(new NewPlayableScreen(game));
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(img, 105, -stage.getHeight() / 3 + 50);
        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        System.exit(0);
    }

    public void setGame(NewPlayableScreen screen) {
        game.setScreen(screen);
    }

    public CleaverOfDoom getGame() {
        return game;
    }

//    private void initUI() {
//        OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        Viewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
//        Stage stage = new Stage(viewport, batch);
//
////        labelXY.setPosition(player.getX(), player.getY());
//        userInterface.setStage(stage);
//        stage.addActor(userInterface);
//
//    }

}
