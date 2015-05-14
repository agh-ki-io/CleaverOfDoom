package pl.edu.agh.game.logic.movement;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedNode;
import com.badlogic.gdx.utils.Array;

/**
 * Created by kcpr on 14.05.15.
 */
public class IndexedNodeImplementation implements IndexedNode {

    int x;
    int y;

    Array<Connection> connections = new Array<>();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public IndexedNodeImplementation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getIndex() {
        return x/50 + y/50*100;
    }

    @Override
    public Array<Connection> getConnections() {
        return null;
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
    }

}
