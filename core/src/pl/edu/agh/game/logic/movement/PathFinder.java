package pl.edu.agh.game.logic.movement;

import com.badlogic.gdx.ai.pfa.*;
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
public class PathFinder {

    BetterDefaultIndexedGraph indexedGraph;
    Heuristic<IndexedNodeImplementation> heuristic;

    private boolean canMove(IndexedNodeImplementation fromNode, MovementComponent movementComponent, float moveX, float moveY, int mapSize) {
        if (fromNode.getX() + moveX >= 0 && fromNode.getX() + moveX < mapSize && fromNode.getY() + moveY >= 0 && fromNode.getY() + moveY < mapSize) {
            movementComponent.setPosition(fromNode.getX(), fromNode.getY());
            movementComponent.setVelocity(1, 1);
            movementComponent.move(moveX, moveY, 1);
            if (fromNode.getX() + moveX == movementComponent.getX() && fromNode.getY() + moveY == movementComponent.getY()
                    && movementComponent.getVelocity() != 0 && movementComponent.getDiagonalVelocity() != 0)
                return true;
        }
        return false;
    }

    public PathFinder(TiledMap map) {

        StatsComponent statsComponent = new StatsComponent(0, 1, 0);
//        float collisionRange = Float.valueOf(rangerAttributes.get("collision"));
        final CollidableComponent<Circle> collidableComponent = new CollidableComponent<>(new Circle(0, 0, 1), map);
        final MovementComponent movementComponent = new MovementComponent(1, 1, statsComponent, collidableComponent);


        int height = ((TiledMapTileLayer) map.getLayers().get("background")).getHeight();
        int mapSize = 7500;

        Array<IndexedNodeImplementation> array = new Array<>();
        float tileSize = Float.parseFloat(map.getProperties().get("scale", "1.0", String.class)) * 50;
        for (int y = 0; y < ((TiledMapTileLayer) map.getLayers().get("background")).getHeight(); y++)
            for (int x = 0; x < ((TiledMapTileLayer) map.getLayers().get("background")).getWidth(); x++) {
                array.add(new IndexedNodeImplementation(y * height + x, (x * tileSize + tileSize / 2), (y * tileSize + tileSize / 2)));
            }

        for (IndexedNodeImplementation fromNode : array) {
            int index = fromNode.getIndex();

            if (canMove(fromNode, movementComponent, -tileSize, 0, mapSize))
                fromNode.addConnection(new DefaultConnection(fromNode, array.get(index - 1)));

            if (canMove(fromNode, movementComponent, 0, tileSize, mapSize))
                fromNode.addConnection(new DefaultConnection(fromNode, array.get(index + 100)));

            if (canMove(fromNode, movementComponent, tileSize, 0, mapSize))
                fromNode.addConnection(new DefaultConnection(fromNode, array.get(index + 1)));

            if (canMove(fromNode, movementComponent, 0, -tileSize, mapSize))
                fromNode.addConnection(new DefaultConnection(fromNode, array.get(index - 100)));
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

        int fromIndex = (int) (fromX / 75 + ((fromY / 75) * 100));
        int toIndex = (int) (toX / 75 + ((toY / 75) * 100));

        IndexedAStarPathFinder indexedAStarPathFinder = new IndexedAStarPathFinder(indexedGraph);
//

//        System.out.println("5202 getX " + indexedGraph.getNodes().get(9999).getX());
//        System.out.println("5202 getY " + indexedGraph.getNodes().get(9999).getY());

        GraphPath<IndexedNodeImplementation> graphPath = new DefaultGraphPath<>();

//        System.out.println(indexedAStarPathFinder.search(new PathFinderRequest(indexedGraph.getNodes().get(fromIndex), indexedGraph.getNodes().get(toIndex), heuristic, graphPath), 2000));
        System.out.println(indexedAStarPathFinder.searchNodePath(indexedGraph.getNodes().get(fromIndex), indexedGraph.getNodes().get(toIndex), heuristic, graphPath));


        System.out.println("From: " + indexedGraph.getNodes().get(fromIndex).getX() + " : " + indexedGraph.getNodes().get(fromIndex).getY() + " to " + indexedGraph.getNodes().get(toIndex).getX() + " : " + indexedGraph.getNodes().get(toIndex).getY());

//        for (IndexedNodeImplementation node : graphPath) {
//
//            System.out.println(node.getX() + " " + node.getY());
//
//        }

        return graphPath;
    }
}

