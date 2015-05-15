package pl.edu.agh.game.logic.movement;

import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Array;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;

/**
 * Created by kcpr on 15.05.15.
 */
public class Pathfinder {

    BetterDefaultIndexedGraph indexedGraph;
    Heuristic<IndexedNodeImplementation> heuristic;

    public Pathfinder(TiledMap map) {

        StatsComponent statsComponent = new StatsComponent(0, 1, 0);
//        float collisionRange = Float.valueOf(rangerAttributes.get("collision"));
        CollidableComponent<Circle> collidableComponent = new CollidableComponent<>(new Circle(0, 0, 1), map);
        final MovementComponent movementComponent = new MovementComponent(1, 1, statsComponent, collidableComponent);


        int height = ((TiledMapTileLayer) map.getLayers().get("background")).getHeight();
        int mapSize = 7500;

        Array<IndexedNodeImplementation> array = new Array<>();
        float tileSize = Float.parseFloat(map.getProperties().get("scale", "1.0", String.class)) * 50;
        System.out.println("tileSize " + tileSize);
        for (int y = 0; y < ((TiledMapTileLayer) map.getLayers().get("background")).getHeight(); y++)
            for (int x = 0; x < ((TiledMapTileLayer) map.getLayers().get("background")).getWidth(); x++) {
                array.add(new IndexedNodeImplementation(y * height + x, (int) (x * tileSize), (int) (y * tileSize)));
            }

        int index = 0;
        for (IndexedNodeImplementation fromNode : array) {

//            System.out.println("index " + index);

            if (fromNode.getX() - tileSize >= 0) {
                movementComponent.setPosition(fromNode.getX(), fromNode.getY());
                movementComponent.move(-tileSize, 0, 1);
                if (fromNode.getX() != (int) movementComponent.getX() || fromNode.getY() != (int) movementComponent.getY())
                    if (index - 1 >= 0)
                        fromNode.addConnection(new DefaultConnection(fromNode, array.get(index - 1)));
            }

            if (fromNode.getY() + tileSize < mapSize) {
                movementComponent.setPosition(fromNode.getX(), fromNode.getY());
//                System.out.println(fromNode.getX() + " " + fromNode.getY());
                movementComponent.move(0, tileSize, 1);
                if (fromNode.getX() != (int) movementComponent.getX() || fromNode.getY() != (int) movementComponent.getY())
                    if (index + 100 < array.size)
                        fromNode.addConnection(new DefaultConnection(fromNode, array.get(index + 100)));
            }

            if (fromNode.getX() + tileSize < mapSize) {
                movementComponent.setPosition(fromNode.getX(), fromNode.getY());
                movementComponent.move(tileSize, 0, 1);
                if (fromNode.getX() != (int) movementComponent.getX() || fromNode.getY() != (int) movementComponent.getY())
                    if (index + 1 < array.size)
                        fromNode.addConnection(new DefaultConnection(fromNode, array.get(index + 1)));
            }

            if (fromNode.getY() - tileSize >= 0) {
                movementComponent.setPosition(fromNode.getX(), fromNode.getY());
                movementComponent.move(0, -tileSize, 1);
                if (fromNode.getX() != (int) movementComponent.getX() || fromNode.getY() != (int) movementComponent.getY())
                    if (index - 100 >= 0)
                        fromNode.addConnection(new DefaultConnection(fromNode, array.get(index - 100)));
            }

            //System.out.println("index " + index);
            index++;
        }

        heuristic = new Heuristic<IndexedNodeImplementation>() {
            @Override
            public float estimate(IndexedNodeImplementation node, IndexedNodeImplementation endNode) {
                return Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY());
            }
        };


//        movementComponent.setPosition(3750, 0);
//        movementComponent.move(3750, 0 + 50, 1);


        System.out.println(((TiledMapTileLayer) map.getLayers().get("background")).getCell(1, 1).getTile().getId());


        System.out.println(((TiledMapTileLayer) map.getLayers().get("background")).getWidth());
        System.out.println(((TiledMapTileLayer) map.getLayers().get("background")).getHeight());

        indexedGraph = new BetterDefaultIndexedGraph(array);


    }

//        HierarchicalPathFinder<Integer[]> = new HierarchicalPathFinder<>(graph,x null);


//        for (int x = 0; x < ((TiledMapTileLayer)map.getLayers().get("background")).getWidth(); x++)
//            for (int y = 0; y < ((TiledMapTileLayer)map.getLayers().get("background")).getHeight(); y++)
//


    public GraphPath<IndexedNodeImplementation> findPath(int fromX, int fromY, int toX, int toY) {

        int fromIndex = fromX / 75 + ((fromY / 75) * 100);
        int toIndex = toX / 75 + ((toY / 75) * 100);

        IndexedAStarPathFinder indexedAStarPathFinder = new IndexedAStarPathFinder(indexedGraph);
//
//        System.out.println("5202 getX " + indexedGraph.getNodes().get(9999).getX());
//        System.out.println("5202 getY " + indexedGraph.getNodes().get(9999).getY());

        GraphPath<IndexedNodeImplementation> graphPath = new DefaultGraphPath<>();

        System.out.println(indexedAStarPathFinder.searchNodePath(indexedGraph.getNodes().get(fromIndex), indexedGraph.getNodes().get(toIndex), heuristic, graphPath));

//        System.out.println("From: " + indexedGraph.getNodes().get(1145 / 75 + ((3519 / 75) * 100)).getX() + " : " + indexedGraph.getNodes().get(1145 / 75 + ((3519 / 75) * 100)).getY() + " to " + indexedGraph.getNodes().get((2250 / 75 + ((2775 / 75) * 100))).getX() + " : " + indexedGraph.getNodes().get((2250 / 75 + ((2775 / 75) * 100))).getY());

        for (IndexedNodeImplementation node : graphPath) {

            System.out.println(node.getX() + " " + node.getY());

        }

        return graphPath;
    }
}

