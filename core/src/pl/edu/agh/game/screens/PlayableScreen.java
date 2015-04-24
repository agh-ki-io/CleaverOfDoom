package pl.edu.agh.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pl.edu.agh.game.CleaverOfDoom;
import pl.edu.agh.game.graphics.Animation;
import pl.edu.agh.game.input.Input;
import pl.edu.agh.game.input.InputState;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.damage.Damage;
import pl.edu.agh.game.logic.damage.DamageComponent;
import pl.edu.agh.game.logic.damage.ReductionStrategy;
import pl.edu.agh.game.logic.drawable.DrawableComponent;
import pl.edu.agh.game.logic.entities.creatures.ComponentEnemy;
import pl.edu.agh.game.logic.entities.players.ComponentPlayer;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;
import pl.edu.agh.game.stolen_assets.Util;
import pl.edu.agh.game.ui.UserInterface;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by lgmyrek on 4/16/15.
 */
public class PlayableScreen implements Screen {
    private final CleaverOfDoom game;
    private final UserInterface userInterface;
    private final SpriteBatch batch;
    private final InputState inputState;

    private final ComponentPlayer player;
    private final List<ComponentEnemy> enemies;

    private Level level;


    public PlayableScreen(CleaverOfDoom game) {
        this.game = game;
        this.userInterface = game.getUserInterface();

        batch = new SpriteBatch();
        OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Viewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        Stage stage = new Stage(viewport, batch);
        userInterface.setStage(stage);
        stage.addActor(userInterface);
        Input input = userInterface.getInput();
        inputState = input.getInputState();
        Gdx.input.setInputProcessor(input.getInputProcessor());

        level = new Level(null, null);
        player = getPlayer();
        enemies = getEnemies();

        level.addCharacter(player);
        level.addCharacters(enemies);
    }

    private ComponentPlayer getPlayer() {
        StatsComponent statsComponent = new StatsComponent(500, 1f, 1f);
        int velocity = 300;
        MovementComponent movementComponent = new MovementComponent(velocity, (float) (Math.sqrt(2) / 2 * velocity), statsComponent);
        Map<String, Animation> animationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/actors/player/ranger_c.xml"));
        return new ComponentPlayer(
                500, 500,
                statsComponent,
                movementComponent,
                new DamageComponent(statsComponent),
                new CollidableComponent<>(new Circle(0, 0, 3.5f * 4)),
                new DrawableComponent(animationMap),
                inputState,
                level
        );
    }


    private List<ComponentEnemy> getEnemies() {
        List<ComponentEnemy> list = new LinkedList<>();
        //jeden przeciwnik
        Queue<Point>points = new LinkedList<>();
        points.add(new Point(500,500));
        points.add(new Point(700,500));
        points.add(new Point(700,200));
        points.add(new Point(600,300));
        points.add(new Point(250,300));
        points.add(new Point(250,200));
        points.add(new Point(200,200));
        StatsComponent statsComponent = new StatsComponent(500, 1f, 1);
        int velocity = 200;
        MovementComponent movementComponent = new MovementComponent(velocity, (float) (Math.sqrt(2) / 2 * velocity), statsComponent);
        Map<String, Animation> animationMap = Util.playerAnimationFromXml(Gdx.files.internal("stolen_assets/actors/player/enemy_1.xml"));
        DamageComponent damageComponent = new DamageComponent(statsComponent);
        damageComponent.setReductionStrategy(new ReductionStrategy() {
            @Override
            public int reduce(Damage damage) {
                return damage.getValue();
            }
        });
        list.add(new ComponentEnemy(
                200, 200,
                statsComponent,
                movementComponent,
                damageComponent,
                new CollidableComponent<>(new Circle(0, 0, 12)),
                new DrawableComponent(animationMap),
                inputState,
                points,
                level
        ));



        return list;
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
//        for(ComponentEnemy enemy:this.enemies)
//            enemy.draw(batch);
//        player.draw(batch);
        level.draw(batch);
        batch.end();
        userInterface.draw();
    }

    private void update(float delta) {
//        for(ComponentEnemy enemy:this.enemies)
//            enemy.update(delta);
//        player.update(delta);
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
