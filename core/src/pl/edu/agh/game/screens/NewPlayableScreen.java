package pl.edu.agh.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.Graph;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.DefaultIndexedGraph;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pl.edu.agh.game.CleaverOfDoom;
import pl.edu.agh.game.input.Input;
import pl.edu.agh.game.input.InputState;
import pl.edu.agh.game.logic.Level;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.entities.players.Player;
import pl.edu.agh.game.logic.movement.IndexedNodeImplementation;
import pl.edu.agh.game.logic.movement.MovementComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;
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

    private Player player;

    private Level level;

    private final OrthographicCamera camera;

    public NewPlayableScreen(CleaverOfDoom game) {
        this.game = game;
        this.userInterface = game.getUserInterface();

        batch = new SpriteBatch();

        initUI();
        Input input = userInterface.getInput();
        inputState = input.getInputState();
        EntityFactory.player1InputState = inputState;
        Gdx.input.setInputProcessor(input.getInputProcessor());

        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        level = LevelFactory.initializeLevel("testMap");
//        player = EntityFactory.getNewPlayer(Player.Profession.ARCHER, level);
//        level.addCharacter(player);
        player = level.getPlayers()[1];
        camera.position.set(player.getX(), player.getY(), 0);


        StatsComponent statsComponent = new StatsComponent(0, 1, 0);
//        float collisionRange = Float.valueOf(rangerAttributes.get("collision"));
        CollidableComponent<Circle> collidableComponent = new CollidableComponent<>(new Circle(0, 0, 1), level.getMap());
        final MovementComponent movementComponent = new MovementComponent(1, 1, statsComponent, collidableComponent);

        Heuristic<IndexedNodeImplementation> heuristic = new Heuristic<IndexedNodeImplementation>() {
            @Override
            public float estimate(IndexedNodeImplementation node, IndexedNodeImplementation endNode) {
                return Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY()- node.getY());
            }
        };



        Graph<Integer[]> graph = new Graph<Integer[]>() {
            @Override
            public Array<Connection<Integer[]>> getConnections(Integer[] fromNode) {

                Array<Connection<Integer[]>> array = new Array<>();
                movementComponent.move(fromNode[0] - 50, fromNode[1], 0);
                array.add(new DefaultConnection(new Integer[]{fromNode[0] - 50, fromNode[1]}, new Integer[]{(int) movementComponent.getX(), (int) movementComponent.getY()}));

                movementComponent.move(fromNode[0], fromNode[1] + 50, 0);
                array.add(new DefaultConnection(new Integer[]{fromNode[0], fromNode[1] + 50}, new Integer[]{(int) movementComponent.getX(), (int) movementComponent.getY()}));

                movementComponent.move(fromNode[0] + 50, fromNode[1], 0);
                array.add(new DefaultConnection(new Integer[]{fromNode[0] + 50, fromNode[1]}, new Integer[]{(int) movementComponent.getX(), (int) movementComponent.getY()}));

                movementComponent.move(fromNode[0], fromNode[1] - 50, 0);
                array.add(new DefaultConnection(new Integer[]{fromNode[0], fromNode[1] + 50}, new Integer[]{(int) movementComponent.getX(), (int) movementComponent.getY()}));

                return array;
            }
        };



        Array<IndexedNodeImplementation> array = new Array<>();

        for (int y = 0; y < ((TiledMapTileLayer)level.getMap().getLayers().get("background")).getHeight(); y++)
            for (int x = 0; x < ((TiledMapTileLayer)level.getMap().getLayers().get("background")).getWidth(); x++) {
                array.add(new IndexedNodeImplementation(x * 50, y * 50));
        }

        int index = 0;
        for (IndexedNodeImplementation fromNode : array) {

            movementComponent.setPosition(fromNode.getX(), fromNode.getY());
            movementComponent.move(fromNode.getX() - 50, fromNode.getY(), 0);
            if (fromNode.getX() != (int)movementComponent.getX() || fromNode.getY() != (int)movementComponent.getY())
                fromNode.addConnection(new DefaultConnection(fromNode, array.get(index - 1)));

            movementComponent.setPosition(fromNode.getX(), fromNode.getY());
            movementComponent.move(fromNode.getX(), fromNode.getY() + 50, 0);
            if (fromNode.getX() != (int)movementComponent.getX() || fromNode.getY() != (int)movementComponent.getY())
                fromNode.addConnection(new DefaultConnection(fromNode, array.get(index - 100)));

            movementComponent.setPosition(fromNode.getX(), fromNode.getY());
            movementComponent.move(fromNode.getX() + 50, fromNode.getY(), 0);
            if (fromNode.getX() != (int)movementComponent.getX() || fromNode.getY() != (int)movementComponent.getY())
                fromNode.addConnection(new DefaultConnection(fromNode, array.get(index + 1)));

            movementComponent.setPosition(fromNode.getX(), fromNode.getY());
            movementComponent.move(fromNode.getX(), fromNode.getY() - 50, 0);
            if (fromNode.getX() != (int)movementComponent.getX() || fromNode.getY() != (int)movementComponent.getY())
                fromNode.addConnection(new DefaultConnection(fromNode, array.get(index + 100)));

        }


//        for (int x = 0; x < ((TiledMapTileLayer)level.getMap().getLayers().get("background")).getWidth(); x++)
//            for (int y = 0; y < ((TiledMapTileLayer)level.getMap().getLayers().get("background")).getHeight(); y++) {
//
//                //IndexedNodeImplementation indexedNodeImplementation = new IndexedNodeImplementation(x, y);
//
//
//                IndexedNodeImplementation fromNode = new IndexedNodeImplementation(x * 50, y * 50);
//
//
//                movementComponent.setPosition(fromNode.getX(), fromNode.getY());
//                movementComponent.move(fromNode.getX()- 50, fromNode.getY(), 0);
//                if (fromNode.getX() != (int)movementComponent.getX() || fromNode.getY() != (int)movementComponent.getY())
//                    fromNode.addConnection(new DefaultConnection(fromNode, new IndexedNodeImplementation((int)movementComponent.getX(), (int)movementComponent.getY())));
//
//                movementComponent.setPosition(fromNode.getX(), fromNode.getY());
//                movementComponent.move(fromNode.getX(), fromNode.getY() + 50, 0);
//                if (fromNode.getX() != (int)movementComponent.getX() || fromNode.getY() != (int)movementComponent.getY())
//                    fromNode.addConnection(new DefaultConnection(fromNode, new IndexedNodeImplementation((int)movementComponent.getX(), (int)movementComponent.getY())));
//
//                movementComponent.setPosition(fromNode.getX(), fromNode.getY());
//                movementComponent.move(fromNode.getX() + 50, fromNode.getY(), 0);
//                if (fromNode.getX() != (int)movementComponent.getX() || fromNode.getY() != (int)movementComponent.getY())
//                    fromNode.addConnection(new DefaultConnection(fromNode, new IndexedNodeImplementation((int)movementComponent.getX(), (int)movementComponent.getY())));
//
//                movementComponent.setPosition(fromNode.getX(), fromNode.getY());
//                movementComponent.move(fromNode.getX(), fromNode.getY() - 50, 0);
//                if (fromNode.getX() != (int)movementComponent.getX() || fromNode.getY() != (int)movementComponent.getY())
//                    fromNode.addConnection(new DefaultConnection(fromNode, new IndexedNodeImplementation((int)movementComponent.getX(), (int)movementComponent.getY())));
//
//
//                array.add(fromNode);
//
//            }

        System.out.println(((TiledMapTileLayer) level.getMap().getLayers().get("background")).getCell(1, 1).getTile().getId());


        System.out.println(((TiledMapTileLayer)level.getMap().getLayers().get("background")).getWidth());
        System.out.println(((TiledMapTileLayer)level.getMap().getLayers().get("background")).getHeight());

        DefaultIndexedGraph defaultIndexedGraph = new DefaultIndexedGraph(array);

        IndexedAStarPathFinder indexedAStarPathFinder = new IndexedAStarPathFinder(defaultIndexedGraph);

        System.out.println(indexedAStarPathFinder.searchConnectionPath(new IndexedNodeImplementation(100, 100), new IndexedNodeImplementation(150, 150), heuristic, null));

//        HierarchicalPathFinder<Integer[]> = new HierarchicalPathFinder<>(graph,x null);



//        for (int x = 0; x < ((TiledMapTileLayer)level.getMap().getLayers().get("background")).getWidth(); x++)
//            for (int y = 0; y < ((TiledMapTileLayer)level.getMap().getLayers().get("background")).getHeight(); y++)
//

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

        //for (OnePointEnemy componentEnemy : (Collection<OnePointEnemy>)level.getEnemies())
            //componentEnemy.setNewDestination(player.getX(), player.getY());

        float mapScale = Float.parseFloat(level.getMap().getProperties().get("scale", "1.0", String.class));
        if (player.getY() + camera.viewportHeight/2 < mapScale*5000
                && player.getY() - camera.viewportHeight/2 > 0)
            camera.position.set(camera.position.x, player.getY(), 0);
        if (player.getX() + camera.viewportWidth/2 < mapScale*5000
                && player.getX() - camera.viewportWidth/2 > 0)
            camera.position.set(player.getX(), camera.position.y, 0);

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
