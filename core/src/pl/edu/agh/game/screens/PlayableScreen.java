package pl.edu.agh.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pl.edu.agh.game.CleaverOfDoom;
import pl.edu.agh.game.graphics.AnimatedMap;
import pl.edu.agh.game.graphics.Animation;
import pl.edu.agh.game.input.Input;
import pl.edu.agh.game.input.InputState;
import pl.edu.agh.game.logic.ComponentPlayer;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.damage.DamageComponent;
import pl.edu.agh.game.logic.drawable.DrawableComponent;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;
import pl.edu.agh.game.stolen_assets.Util;
import pl.edu.agh.game.ui.UserInterface;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by lgmyrek on 4/16/15.
 */
public class PlayableScreen implements Screen {
    private final CleaverOfDoom game;
    private final UserInterface userInterface;
    private final SpriteBatch batch;
    private final InputState inputState;
    OrthographicCamera camera;
    private TiledMap map;
    private TiledMapRenderer renderer;


    private final ComponentPlayer player;


    public PlayableScreen(CleaverOfDoom game) {
        this.game = game;
        this.userInterface = game.getUserInterface();

        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Viewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        Stage stage = new Stage(viewport, batch);
        userInterface.setStage(stage);
        stage.addActor(userInterface);
        Input input = userInterface.getInput();
        inputState = input.getInputState();
        Gdx.input.setInputProcessor(input.getInputProcessor());

        map = new TmxMapLoader().load("untitled.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1.5f);

        AnimatedMap animatedMap = new AnimatedMap(map);

        player = getPlayer();
    }

    private ComponentPlayer getPlayer() {
        StatsComponent statsComponent = new StatsComponent(500, 2f, 3f);
        int velocity = 300;
        CollidableComponent collidableComponent = new CollidableComponent(map);
        MovementComponent movementComponent = new MovementComponent(300, (float) (Math.sqrt(2) / 2 * velocity), statsComponent, collidableComponent);
        Map<String, Animation> animationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/actors/player/ranger_c.xml"));
        DrawableComponent drawableComponent = new DrawableComponent(statsComponent, movementComponent, animationMap);
        collidableComponent.setSize(drawableComponent.getSize());
        return new ComponentPlayer(
                statsComponent,
                movementComponent,
                new DamageComponent(statsComponent),
                collidableComponent,
                drawableComponent,
                inputState
        );
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        AnimatedTiledMapTile.updateAnimationBaseTime();
        Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
        batch.begin();
        player.draw(batch);
        batch.end();
        userInterface.draw();
    }

    private void update(float delta) {
        player.update(delta);
        userInterface.update(delta);
        camera.position.set(player.getX(), player.getY(), 1);
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
