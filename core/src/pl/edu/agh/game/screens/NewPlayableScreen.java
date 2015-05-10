package pl.edu.agh.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pl.edu.agh.game.CleaverOfDoom;
import pl.edu.agh.game.input.Input;
import pl.edu.agh.game.input.InputState;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.entities.players.ComponentPlayer;
import pl.edu.agh.game.logic.entities.players.Player;
import pl.edu.agh.game.stolen_assets.EntityFactory;
import pl.edu.agh.game.stolen_assets.LevelFactory;
import pl.edu.agh.game.ui.UserInterface;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-30
 */
public class NewPlayableScreen implements Screen {
    private final CleaverOfDoom game;
    private final UserInterface userInterface;
    private final SpriteBatch batch;
    private final InputState inputState;

    private ComponentPlayer player;

    private Level level;

    private final OrthographicCamera camera;

    public NewPlayableScreen(CleaverOfDoom game) {
        this.game = game;
        this.userInterface = game.getUserInterface();

        batch = new SpriteBatch();

        initUI();
        Input input = userInterface.getInput();
        inputState = input.getInputState();
        if (inputState.getMusic() != null) inputState.getMusic().dispose();
        inputState.setMusic(Gdx.audio.newMusic(Gdx.files.internal("The_Losers_-_Menace.mp3")));
        inputState.getMusic().setLooping(true);
        inputState.getMusic().play();

//        inputState.setCurrentLevel(this);
        Gdx.input.setInputProcessor(input.getInputProcessor());

        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        level = LevelFactory.initializeLevel("testMap");
        player = EntityFactory.getNewPlayer(Player.Profession.ARCHER, level, inputState);
        level.addCharacter(player);
    }

    private void initUI() {
        OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Viewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        Stage stage = new Stage(viewport, batch);
        userInterface.setStage(stage);
        stage.addActor(userInterface);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        doOpenGLStuff();
        batch.setProjectionMatrix(camera.combined);
        level.drawCharactersAndLayers(batch);
    }

    private void doOpenGLStuff() {
        Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void update(float delta) {
        if (inputState.isMenuOn())  {
            inputState.setMenuOn(false);
            game.setScreen(new MenuScreen(game));
        }

        camera.update();
        camera.position.set(player.getX(), player.getY(), 0);
        level.getRenderer().setView(camera);
        level.update(delta);
        userInterface.update(delta);
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

    }
}
