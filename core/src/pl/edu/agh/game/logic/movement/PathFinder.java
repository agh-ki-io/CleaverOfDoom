package pl.edu.agh.game.logic.movement;

import com.badlogic.gdx.ai.pfa.*;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Array;
import pl.edu.agh.game.logic.collisions.CollidableComponent;
import pl.edu.agh.game.logic.stats.StatsComponent;

public class PathFinder {

    BetterDefaultIndexedGraph indexedGraph;
    Heuristic<IndexedNodeImplementation> heuristic;

    private boolean canMove(IndexedNodeImplementation fromNode, CollidableComponent collidableComponent, float moveX, float moveY, int mapSize) {
        float newX = fromNode.getX() + moveX;
        float newY = fromNode.getY() + moveY;

        if (newX >= 0 && newX < mapSize && newY >= 0 && newY < mapSize) { // Should be checked in collidableComponent.collision I believe.

            if (collidableComponent.collision(newX, newY, "blocked") || collidableComponent.collision(newX, newY, "pit") || collidableComponent.collision(newX, newY, "slime"))
                return false;

            if (collidableComponent.collision(newX, newY, "water") && !collidableComponent.collision(fromNode.getX(), fromNode.getY(), "water") && !collidableComponent.collision(fromNode.getX(), fromNode.getY(), "stairs"))
                return false;

            if (collidableComponent.collision(fromNode.getX(), fromNode.getY(), "water") && !collidableComponent.collision(newX, newY, "water") && !collidableComponent.collision(newX, newY, "stairs"))
                return false;

            if (newX != fromNode.getX() && newY != fromNode.getY() && collidableComponent.collision(fromNode.getX(), fromNode.getY(), "stairs") && !(collidableComponent.collision(newX, fromNode.getY(), "water") && collidableComponent.collision(fromNode.getX(), newY, "water")))
                return false;

            return true;

            //if (collidableComponent.collision(newX, newY, "blocked") ||)


//            movementComponent.setPosition(fromNode.getX(), fromNode.getY());
//            movementComponent.setVelocity(1, 1);
//            movementComponent.move(moveX, moveY, 1);
//            if (fromNode.getX() + moveX == movementComponent.getX() && fromNode.getY() + moveY == movementComponent.getY()
//                    && movementComponent.getVelocity() != -1 && movementComponent.getDiagonalVelocity() != -1)
//                return true;
//        }
//        return false;

        }
        return false;
    }

    public PathFinder(TiledMap map) {

        StatsComponent statsComponent = new StatsComponent(0, 1, 0, 0);
//        float collisionRange = Float.valueOf(rangerAttributes.get("collision"));
        final CollidableComponent<Circle> collidableComponent = new CollidableComponent<>(null, map);
//        final MovementComponent movementComponent = new MovementComponent(1, 1, statsComponent, collidableComponent);


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

            boolean canMoveNorth = canMove(fromNode, collidableComponent, 0, tileSize, mapSize);
            boolean canMoveSouth = canMove(fromNode, collidableComponent, 0, -tileSize, mapSize);
            boolean canMoveEast = canMove(fromNode, collidableComponent, tileSize, 0, mapSize);
            boolean canMoveWest = canMove(fromNode, collidableComponent, -tileSize, 0, mapSize);

            if (canMoveNorth)
                fromNode.addConnection(new DefaultConnection(fromNode, array.get(index + 100)));

            if (canMoveSouth)
                fromNode.addConnection(new DefaultConnection(fromNode, array.get(index - 100)));

            if (canMoveWest)
                fromNode.addConnection(new DefaultConnection(fromNode, array.get(index - 1)));

            if (canMoveEast)
                fromNode.addConnection(new DefaultConnection(fromNode, array.get(index + 1)));

            if (canMoveNorth && canMoveWest && canMove(fromNode, collidableComponent, -tileSize, tileSize, mapSize))
                fromNode.addConnection(new DefaultConnection(fromNode, array.get(index + 100 - 1)));

            if (canMoveNorth && canMoveEast && canMove(fromNode, collidableComponent, tileSize, tileSize, mapSize))
                fromNode.addConnection(new DefaultConnection(fromNode, array.get(index + 100 + 1)));

            if (canMoveSouth && canMoveWest && canMove(fromNode, collidableComponent, -tileSize, -tileSize, mapSize))
                fromNode.addConnection(new DefaultConnection(fromNode, array.get(index - 100 - 1)));

            if (canMoveSouth && canMoveEast && canMove(fromNode, collidableComponent, tileSize, -tileSize, mapSize))
                fromNode.addConnection(new DefaultConnection(fromNode, array.get(index - 100 + 1)));

        }

        heuristic = new Heuristic<IndexedNodeImplementation>() {
            @Override
            public float estimate(IndexedNodeImplementation node, IndexedNodeImplementation endNode) {
                return Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY());
            }
        };


//        System.out.println(((TiledMapTileLayer) map.getLayers().get("background")).getCell(1, 1).getTile().getId());
//
//
//        System.out.println(((TiledMapTileLayer) map.getLayers().get("background")).getWidth());
//        System.out.println(((TiledMapTileLayer) map.getLayers().get("background")).getHeight());

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

        GraphPath<IndexedNodeImplementation> graphPath = new DefaultGraphPath<>();

//        System.out.println(indexedAStarPathFinder.search(new PathFinderRequest(indexedGraph.getNodes().get(fromIndex), indexedGraph.getNodes().get(toIndex), heuristic, graphPath), 2000));
        indexedAStarPathFinder.searchNodePath(indexedGraph.getNodes().get(fromIndex), indexedGraph.getNodes().get(toIndex), heuristic, graphPath);


        //System.out.println("From: " + indexedGraph.getNodes().get(fromIndex).getX() + " : " + indexedGraph.getNodes().get(fromIndex).getY() + " to " + indexedGraph.getNodes().get(toIndex).getX() + " : " + indexedGraph.getNodes().get(toIndex).getY());

//        for (IndexedNodeImplementation node : graphPath) {
//
//            System.out.println(node.getX() + " " + node.getY());
//
//        }

        return graphPath;
    }
}

